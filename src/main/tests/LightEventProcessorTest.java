package tests;

import com.google.gson.Gson;
import org.junit.Test;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.data.loaders.HomeLoader;
import ru.sbt.mipt.oop.data.loaders.JSONHomeLoader;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.event.processors.LightEventProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LightEventProcessorTest {
    private final String resources = "src/main/tests/resources/";

    @Test
    public void process_NonChangingHome() throws IOException {
        //given
        HomeLoader homeLoader = new JSONHomeLoader(resources + "test_home.json");
        SmartHome smartHome = homeLoader.loadHome();
        SmartHome sameSmartHome = homeLoader.loadHome();
        Gson gson = new Gson();
        String jsonStr = new String(Files.readAllBytes(Paths.get(resources + "test_sensor_events.json")));
        SensorEvent[] sensorEvents =  gson.fromJson(jsonStr, SensorEvent[].class);
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome, null);
        //when
        for (SensorEvent event : sensorEvents) {
            lightEventProcessor.process(event);
        }
        //then
        assertEquals(smartHome, sameSmartHome);
    }

    @Test
    public void process_ChangingHome() throws IOException {
        //given
        HomeLoader homeLoader = new JSONHomeLoader(resources + "test_home_light_turned_on.json");
        HomeLoader homeLoader2 = new JSONHomeLoader(resources + "test_home.json");
        SmartHome smartHome = homeLoader.loadHome();
        SmartHome smartHomeWithTurnedOffLight = homeLoader2.loadHome();
        Gson gson = new Gson();
        String jsonStr = new String(Files.readAllBytes(Paths.get(resources + "test_sensor_events.json")));
        SensorEvent[] sensorEvents =  gson.fromJson(jsonStr, SensorEvent[].class);
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome, null);
        //when
        for (SensorEvent event : sensorEvents) {
            lightEventProcessor.process(event);
        }
        //then
        assertEquals(smartHome, smartHomeWithTurnedOffLight);
    }
}