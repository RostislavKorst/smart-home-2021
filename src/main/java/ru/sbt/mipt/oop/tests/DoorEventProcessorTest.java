package ru.sbt.mipt.oop.tests;

import com.google.gson.Gson;
import org.junit.Test;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.command.senders.PretendingCommandSender;
import ru.sbt.mipt.oop.data.loaders.HomeLoader;
import ru.sbt.mipt.oop.data.loaders.JSONHomeLoader;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.processors.DoorEventProcessor;
import ru.sbt.mipt.oop.processors.LightEventProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DoorEventProcessorTest {
    @Test
    public void processing_NonChangingHome() throws IOException {
        //given
        HomeLoader homeLoader = new JSONHomeLoader("src/main/java/ru/sbt/mipt/oop/tests/" +
                "resources/test_home.json");
        SmartHome smartHome = homeLoader.loadHome();
        SmartHome sameSmartHome = homeLoader.loadHome();
        Gson gson = new Gson();
        String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/ru/sbt/mipt/oop/tests/" +
                "resources/test_sensor_events.json")));
        SensorEvent[] sensorEvents =  gson.fromJson(jsonStr, SensorEvent[].class);
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome, null);
        //when
        for (SensorEvent event : sensorEvents) {
            doorEventProcessor.processing(event);
        }
        //then
        assertEquals(smartHome, sameSmartHome);
    }

    @Test
    public void processing_ChangingHome_ClosesDoor() throws IOException {
        //given
        HomeLoader homeLoader = new JSONHomeLoader("src/main/java/ru/sbt/mipt/oop/tests/" +
                "resources/test_home_door_opened.json");
        HomeLoader homeLoader2 = new JSONHomeLoader("src/main/java/ru/sbt/mipt/oop/tests/" +
                "resources/test_home_door_closed.json");
        SmartHome smartHome = homeLoader.loadHome();
        SmartHome smartHomeWithClosedDoor = homeLoader2.loadHome();
        Gson gson = new Gson();
        String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/ru/sbt/mipt/oop/tests/" +
                "resources/test_sensor_event_closing_door.json")));
        SensorEvent[] sensorEvents =  gson.fromJson(jsonStr, SensorEvent[].class);
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome, new PretendingCommandSender());
        //when
        for (SensorEvent event : sensorEvents) {
            doorEventProcessor.processing(event);
        }
        //then
        assertEquals(smartHome, smartHomeWithClosedDoor);
    }
}