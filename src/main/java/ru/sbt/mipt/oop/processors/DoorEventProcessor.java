package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.home.components.*;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventProcessor implements Processor {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public DoorEventProcessor(SmartHome smartHome, CommandSender commandSender) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    private boolean isDoor(SensorEvent event) {
        return event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED;
    }

    @Override
    public void processing(SensorEvent event) {
        if (isDoor(event)) {
            smartHome.execute(object -> {
                if (object instanceof Door) {
                    Door door = (Door) object;
                    if (door.getId().equals(event.getObjectId())) {
                        if (event.getType() == DOOR_OPEN) {
                            door.setOpen(true);
                            System.out.println("Door " + door.getId() + " was opened.");
                        } else {
                            door.setOpen(false);
                            System.out.println("Door " + door.getId() + " was closed.");
                        }
                    }
                }
            });
        }
    }

    @Override
    public SmartHome getHome() {
        return smartHome;
    }
}