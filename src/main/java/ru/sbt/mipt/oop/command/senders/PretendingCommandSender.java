package ru.sbt.mipt.oop.command.senders;

import ru.sbt.mipt.oop.SensorCommand;

public class PretendingCommandSender implements CommandSender {
    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}