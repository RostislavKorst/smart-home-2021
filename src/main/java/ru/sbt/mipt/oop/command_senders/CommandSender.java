package ru.sbt.mipt.oop.command_senders;

import ru.sbt.mipt.oop.SensorCommand;

public class CommandSender {
    public static void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
