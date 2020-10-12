package ru.sbt.mipt.oop.data.generators;

import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;

public class DummyRandomDataGenerator implements DataGenerator {
    public SensorEvent getNextSensorEvent() {
        // pretend like we're getting the events from physical world, but here we're going to just generate some random events
        if (Math.random() < 0.05) return null; // null means end of event stream
        SensorEventType sensorEventType = SensorEventType.values()[(int) (6 * Math.random())];
        String objectId = "" + ((int) (10 * Math.random()));
        if (sensorEventType == SensorEventType.ALARM_ACTIVATE || sensorEventType == SensorEventType.ALARM_DEACTIVATE) {
            // Введение правильного либо неправильного кода (верный код - 12345)
            int inputCode = 12345 - (int) (3 * Math.random());
            return new SensorEvent(sensorEventType, inputCode);
        }
        return new SensorEvent(sensorEventType, objectId);
    }
}