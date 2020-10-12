package ru.sbt.mipt.oop;

import java.util.Arrays;
import java.util.List;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.command.senders.PretendingCommandSender;
import ru.sbt.mipt.oop.data.generators.DataGenerator;
import ru.sbt.mipt.oop.data.generators.DummyRandomDataGenerator;
import ru.sbt.mipt.oop.data.loaders.HomeLoader;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.data.loaders.*;
import ru.sbt.mipt.oop.message.senders.MessageSender;
import ru.sbt.mipt.oop.message.senders.SMSMessageSender;
import ru.sbt.mipt.oop.processors.*;

public class Application {
    private final HomeLoader homeLoader;
    private final DataGenerator dataGenerator;
    private final MessageSender messageSender;

    public Application(HomeLoader homeLoader, DataGenerator dataGenerator, MessageSender messageSender) {
        this.homeLoader = homeLoader;
        this.dataGenerator = dataGenerator;
        this.messageSender = messageSender;
    }

    public static void main(String... args) {
        HomeLoader homeLoader = new JSONHomeLoader("smart-home-1.js");
        DataGenerator dataGenerator = new DummyRandomDataGenerator();
        MessageSender messageSender = new SMSMessageSender();
        Application application = new Application(homeLoader, dataGenerator, messageSender);
        application.run();
    }

    public void run() {
        SmartHome smartHome = homeLoader.loadHome();
        Alarm alarm = new Alarm(12345);
        smartHome.setAlarm(alarm);
        List<Processor> eventProcessors = Arrays.asList(
                new SecurityProcessorDecorator(new LightEventProcessor(smartHome, new PretendingCommandSender()),
                        messageSender),
                new SecurityProcessorDecorator(new DoorEventProcessor(smartHome, new PretendingCommandSender()),
                        messageSender),
                new SecurityProcessorDecorator(new HallDoorEventProcessor(smartHome, new PretendingCommandSender()),
                        messageSender),
                new AlarmEventProcessor(smartHome, new PretendingCommandSender()));
        SmartHomeHandler smartHomeHandler = new SmartHomeHandler(smartHome, eventProcessors, dataGenerator);
        smartHomeHandler.runCycleForEvent();
    }
}