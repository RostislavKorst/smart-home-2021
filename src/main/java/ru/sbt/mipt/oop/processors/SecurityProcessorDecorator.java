package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.message.senders.MessageSender;

public class SecurityProcessorDecorator implements Processor {
    private final Processor wrappeeProcessor;
    private final MessageSender messageSender;

    public SecurityProcessorDecorator(Processor wrappeeProcessor, MessageSender messageSender) {
        this.wrappeeProcessor = wrappeeProcessor;
        this.messageSender = messageSender;
    }

    private boolean isAlarmEvent(SensorEvent event) {
        return event.getType() == SensorEventType.ALARM_DEACTIVATE || event.getType() == SensorEventType.ALARM_ACTIVATE;
    }

    @Override
    public void processing(SensorEvent event) {
        if (isAlarmEvent(event)) return;
        if (getHome().getAlarm() != null && getHome().getAlarm().isTriggered()) {
            System.out.println("Sensor detection while alarm is triggered!");
            messageSender.send("Sending sms");
            return;
        }
        wrappeeProcessor.processing(event);
        if (getHome().getAlarm() != null && getHome().getAlarm().isActivated()) {
            getHome().getAlarm().trigger();
            System.out.println("Sensor detection while alarm is on. Alarm was triggered!");
            messageSender.send("Sending sms");
        }
    }

    @Override
    public SmartHome getHome() {
        return wrappeeProcessor.getHome();
    }
}
