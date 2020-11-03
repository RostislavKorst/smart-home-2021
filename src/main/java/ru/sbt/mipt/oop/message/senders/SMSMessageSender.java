package ru.sbt.mipt.oop.message.senders;

public class SMSMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println(message);
    }
}
