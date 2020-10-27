package ru.sbt.mipt.oop.event.processors;

import ru.sbt.mipt.oop.SensorEvent;

public interface SensorEventProcessor {
    void process(SensorEvent event);
}