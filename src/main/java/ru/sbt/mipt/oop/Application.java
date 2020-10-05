package ru.sbt.mipt.oop;

import java.util.Arrays;
import java.util.List;

import ru.sbt.mipt.oop.command_senders.PretendingCommandSender;
import ru.sbt.mipt.oop.data.loaders.HomeLoader;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.data.loaders.*;
import ru.sbt.mipt.oop.processors.*;

public class Application {
    private final HomeLoader homeLoader;

    public Application(HomeLoader homeLoader) {
        this.homeLoader = homeLoader;
    }

    public static void main(String... args) {
        HomeLoader homeLoader = new JSONHomeLoader("smart-home-1.js");
        Application application = new Application(homeLoader);
        application.run();
    }

    public void run() {
        SmartHome smartHome = homeLoader.loadHome();
        // начинаем цикл обработки событий
        List<Processor> eventProcessors = Arrays.asList(new LightEventProcessor(smartHome, new PretendingCommandSender()),
                new DoorEventProcessor(smartHome, new PretendingCommandSender()));
        SmartHomeHandler smartHomeHandler = new SmartHomeHandler(smartHome, eventProcessors);
        smartHomeHandler.runCycleForEvent();
    }
}