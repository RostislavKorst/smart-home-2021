package ru.sbt.mipt.oop.processors;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.home.components.SmartHome;

public interface Processor {
    void processing(SensorEvent event);
    SmartHome getHome();
}