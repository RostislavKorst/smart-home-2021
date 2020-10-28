package ru.sbt.mipt.oop.data.loaders;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.home.components.SmartHome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONHomeLoader implements HomeLoader {
    private final String fileName;

    public JSONHomeLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SmartHome loadHome() {
        Gson gson = new Gson();
        String jsonStr;
        try {
            jsonStr = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Could not read home from file " + fileName, e);
        }
        return gson.fromJson(jsonStr, SmartHome.class);
    }
}