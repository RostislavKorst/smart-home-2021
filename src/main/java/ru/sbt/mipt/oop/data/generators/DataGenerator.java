package ru.sbt.mipt.oop.data.generators;

import ru.sbt.mipt.oop.SensorEvent;

public interface DataGenerator {
    SensorEvent getNextSensorEvent();
}
