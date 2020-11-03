package ru.sbt.mipt.oop;

import java.util.Arrays;

import static ru.sbt.mipt.oop.CommandType.*;

public class SensorCommand {
    private final CommandType type;
    private String objectId;
    private int alarmCode;

    public SensorCommand(CommandType type, String objectId) {
        if (Arrays.asList(ALARM_ACTIVATE, ALARM_DEACTIVATE, ALARM_TRIGGER).contains(type)) {
            throw new RuntimeException(new ClassCastException("Wrong type of alarmCode, should be int"));
        }
        this.type = type;
        this.objectId = objectId;
    }

    public SensorCommand(CommandType type, int alarmCode) {
        if (!Arrays.asList(ALARM_ACTIVATE, ALARM_DEACTIVATE, ALARM_TRIGGER).contains(type)) {
            throw new RuntimeException(new ClassCastException("Wrong type of objectId, should be String"));
        }
        this.type = type;
        this.alarmCode = alarmCode;
    }

    @Override
    public String toString() {
        return "SensorCommand{" +
                "type=" + type +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}