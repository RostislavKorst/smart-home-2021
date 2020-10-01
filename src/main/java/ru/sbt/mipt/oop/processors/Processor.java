package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.SensorEvent;

public interface Processor {
    void processing(SensorEvent event);
}