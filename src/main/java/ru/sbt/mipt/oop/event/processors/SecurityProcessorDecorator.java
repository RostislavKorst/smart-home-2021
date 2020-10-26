package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.message.senders.MessageSender;

public class SecurityProcessorDecorator implements SensorEventProcessor {
    private final SensorEventProcessor wrappeeProcessor;
    private final MessageSender messageSender;
    private final SmartHome smartHome;

    public SecurityProcessorDecorator(SensorEventProcessor wrappeeProcessor, MessageSender messageSender,
                                      SmartHome smartHome) {
        this.wrappeeProcessor = wrappeeProcessor;
        this.messageSender = messageSender;
        this.smartHome = smartHome;
    }

    private boolean isAlarmEvent(SensorEvent event) {
        return event.getType() == SensorEventType.ALARM_DEACTIVATE || event.getType() == SensorEventType.ALARM_ACTIVATE;
    }

    @Override
    public void process(SensorEvent event) {
        if (isAlarmEvent(event)) return;
        Alarm alarm = smartHome.getAlarm();
        wrappeeProcessor.process(event);
        if (alarm != null && alarm.isActivated()) {
            alarm.trigger();
            System.out.println("Sensor detection while alarm is on. Alarm was triggered!");
            messageSender.send("Sending sms");
        }
    }
}
