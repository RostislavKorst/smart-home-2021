package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.home.components.SmartHome;

public class AlarmEventProcessor implements Processor {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public AlarmEventProcessor(SmartHome smartHome, CommandSender commandSender) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    @Override
    public void processing(SensorEvent event) {
        if (event.getType() == SensorEventType.ALARM_ACTIVATE) {
            smartHome.getAlarm().activate(event.getCode());
        }

        if (event.getType() == SensorEventType.ALARM_DEACTIVATE) {
            smartHome.getAlarm().deactivate(event.getCode());
        }
    }

    @Override
    public SmartHome getHome() {
        return smartHome;
    }
}