package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.data.generators.DataGenerator;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.processors.Processor;

import java.util.List;

public class SmartHomeHandler {
    private final SmartHome smartHome;
    private final List<Processor> processors;
    private final DataGenerator dataGenerator;

    public SmartHomeHandler(SmartHome smartHome, List<Processor> processors, DataGenerator dataGenerator) {
        this.smartHome = smartHome;
        this.processors = processors;
        this.dataGenerator = dataGenerator;
    }

    public void runCycleForEvent() {
        SensorEvent event = dataGenerator.getNextSensorEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            for (Processor processor : processors) {
                processor.processing(event);
            }
            event = dataGenerator.getNextSensorEvent();
        }
    }
}