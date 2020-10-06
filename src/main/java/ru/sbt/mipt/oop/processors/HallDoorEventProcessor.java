package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.CommandType;
import ru.sbt.mipt.oop.SensorCommand;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.home.components.*;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class HallDoorEventProcessor implements Processor {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public HallDoorEventProcessor(SmartHome smartHome, CommandSender commandSender) {
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
                if (event.getType() == DOOR_CLOSED && object instanceof Room) {
                    Room room = (Room) object;
                    if (room.getName().equals("hall")) {
                        room.execute(doorInstance -> {
                            if (doorInstance instanceof Door) {
                                Door door = (Door) doorInstance;
                                if (door.getId().equals((event.getObjectId()))) {
                                    smartHome.execute(homeInstance -> {
                                        if (homeInstance instanceof Light) {
                                            Light light = (Light) homeInstance;
                                            light.setOn(false);
                                            System.out.println("Light " + light.getId() + " was turned off.");
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}