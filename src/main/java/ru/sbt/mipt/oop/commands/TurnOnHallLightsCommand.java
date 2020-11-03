package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.CommandType;
import ru.sbt.mipt.oop.SensorCommand;
import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.home.components.Light;
import ru.sbt.mipt.oop.home.components.Room;
import ru.sbt.mipt.oop.home.components.SmartHome;

public class TurnOnHallLightsCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public TurnOnHallLightsCommand(SmartHome smartHome, CommandSender commandSender) {
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
            if (!room.getName().equals("hall")) {
                return;
            }
            room.execute(lightInstance -> {
                if (!(lightInstance instanceof Light)) {
                    return;
                }
                Light light = (Light) lightInstance;
                light.setOn(true);
                System.out.println("Light " + light.getId() + " was turned on.");
                SensorCommand sensorCommand = new SensorCommand(CommandType.LIGHT_ON, light.getId());
                commandSender.sendCommand(sensorCommand);
            });
        });
    }
}

