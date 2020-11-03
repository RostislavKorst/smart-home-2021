package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.CommandType;
import ru.sbt.mipt.oop.SensorCommand;
import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.home.components.SmartHome;

public class TriggerAlarmCommand implements Command {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public TriggerAlarmCommand(SmartHome smartHome, CommandSender commandSender) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    @Override
    public void execute() {
        smartHome.getAlarm().trigger();
        SensorCommand sensorCommand = new SensorCommand(CommandType.ALARM_TRIGGER, 12345);
        commandSender.sendCommand(sensorCommand);
    }
}