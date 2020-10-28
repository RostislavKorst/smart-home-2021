package ru.sbt.mipt.oop.alarm;

public interface AlarmState {
    void activate(int codeInput);
    void deactivate(int codeInput);
    void trigger();
}