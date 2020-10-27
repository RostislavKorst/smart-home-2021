package ru.sbt.mipt.oop;

public class SensorEvent {
    private final SensorEventType type;
    private String objectId;

    public int getCode() {
        return code;
    }

    private int code;

    public SensorEvent(SensorEventType type, String objectId) {
        this.type = type;
        this.objectId = objectId;
    }

    public SensorEvent(SensorEventType type, int code) {
        this.type = type;
        this.code = code;
    }

    public SensorEventType getType() {
        return type;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        if (type == SensorEventType.ALARM_ACTIVATE || type == SensorEventType.ALARM_DEACTIVATE) {
            return "SensorEvent{" +
                    "type=" + type +
                    ", passCode=" + code +
                    '}';
        }
        return "SensorEvent{" +
                "type=" + type +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}