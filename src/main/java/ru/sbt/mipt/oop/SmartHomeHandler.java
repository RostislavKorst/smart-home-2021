package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.processors.Processor;

import java.util.List;

public class SmartHomeHandler {
    private final SmartHome smartHome;
    private final List<Processor> processors;
    private SensorEvent event;

    {
        event = RandomDataGenerator.getNextSensorEvent();
    }

    public SmartHomeHandler(SmartHome smartHome, List<Processor> processors) {
        this.smartHome = smartHome;
        this.processors = processors;
    }

    public void runCycleForEvent() {
        while (event != null) {
            System.out.println("Got event: " + event);
            for (Processor processor : processors) {
                processor.processing(event);
            }
            event = RandomDataGenerator.getNextSensorEvent();
        }
    }
}