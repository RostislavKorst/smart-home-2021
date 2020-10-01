package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ru.sbt.mipt.oop.home_components.*;
import ru.sbt.mipt.oop.data_readers.*;
import ru.sbt.mipt.oop.processors.*;

public class Application {

    public static void main(String... args) throws IOException {
        // считываем состояние дома из файла
        JSONDataReader jsonData = new JSONDataReader("smart-home-1.js");
        Gson gson = new Gson();
        SmartHome smartHome = gson.fromJson(jsonData.getData(), SmartHome.class);
        // начинаем цикл обработки событий
        List<Processor> eventProcessors = Arrays.asList(new LightEventProcessor(smartHome),
                new DoorEventProcessor(smartHome));
        SmartHomeHandler smartHomeHandler = new SmartHomeHandler(smartHome, eventProcessors);
        smartHomeHandler.runCycleForEvent();
    }
}