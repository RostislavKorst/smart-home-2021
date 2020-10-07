package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.home.components.*;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;

public class LightEventProcessor implements Processor {
    private final SmartHome smartHome;
    private final CommandSender commandSender;

    public LightEventProcessor(SmartHome smartHome, CommandSender commandSender) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
    }

    private boolean isLight(SensorEvent event) {
        return event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF;
    }

    @Override
    public void processing(SensorEvent event) {
        if (isLight(event)) {
            smartHome.execute(object -> {
                if (object instanceof Light) {
                    Light light = (Light) object;
                    if (event.getObjectId().equals(light.getId())) {
                        if ( event.getType() == LIGHT_ON) {
                            light.setOn(true);
                            System.out.println("Light " + light.getId() + " was turned on.");
                        }
                        else {
                            light.setOn(false);
                            System.out.println("Light " + light.getId() + " was turned off.");
                        }
                    }
                }
            });
        }
    }
}