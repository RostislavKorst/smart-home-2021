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

    private boolean isHallDoor(SensorEvent event) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(event.getObjectId())) {
                    if (room.getName().equals("hall")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void processing(SensorEvent event) {
        if (isDoor(event) && isHallDoor(event)) {
            if (event.getType() == DOOR_CLOSED) {
                for (Room room : smartHome.getRooms()) {
                    if (room.getName().equals("hall")) {
                        for (Room homeRoom : smartHome.getRooms()) {
                            for (Light light : homeRoom.getLights()) {
                                light.setOn(false);
                                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                                commandSender.sendCommand(command);
                            }
                        }
                    }
                }
            }

        }
    }
}