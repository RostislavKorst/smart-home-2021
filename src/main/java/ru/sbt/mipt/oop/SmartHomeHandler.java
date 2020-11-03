package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.data.generators.DataGenerator;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.event.processors.SensorEventProcessor;

import java.util.List;

public class SmartHomeHandler {
    private final SmartHome smartHome;
    private final List<SensorEventProcessor> sensorEventProcessors;
    private final DataGenerator dataGenerator;

    public SmartHomeHandler(SmartHome smartHome, List<SensorEventProcessor> sensorEventProcessors, DataGenerator dataGenerator) {
        this.smartHome = smartHome;
        this.sensorEventProcessors = sensorEventProcessors;
        this.dataGenerator = dataGenerator;
    }

    public void runCycleForEvent() {
        SensorEvent event = dataGenerator.getNextSensorEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            for (SensorEventProcessor sensorEventProcessor : sensorEventProcessors) {
                sensorEventProcessor.process(event);
            }
            event = dataGenerator.getNextSensorEvent();
        }
    }
}