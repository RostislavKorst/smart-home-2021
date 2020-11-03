package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.CommandType;
import ru.sbt.mipt.oop.SensorCommand;
import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.home.components.*;

public class CloseHallDoorCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public CloseHallDoorCommand(SmartHome smartHome, CommandSender commandSender) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    @Override
    public void execute() {
        smartHome.execute(roomInstance -> {
            if (!(roomInstance instanceof Room)) {
                return;
            }
            Room room = (Room) roomInstance;
            if (!(room.getName().equals("hall"))) {
                return;
            }
            smartHome.execute(doorInstance -> {
                if (!(doorInstance instanceof Door)) {
                    return;
                }
                Door door = (Door) doorInstance;
                door.setOpen(false);
                System.out.println("Door " + door.getId() + " was closed.");
                SensorCommand sensorCommand = new SensorCommand(CommandType.DOOR_CLOSE, door.getId());
                commandSender.sendCommand(sensorCommand);
            });
        });
    }
}
