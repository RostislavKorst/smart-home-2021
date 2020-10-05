package ru.sbt.mipt.oop.command_senders;

import ru.sbt.mipt.oop.SensorCommand;

public interface CommandSender {
    void sendCommand(SensorCommand command);
}
