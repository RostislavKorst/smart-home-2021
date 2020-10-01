package ru.sbt.mipt.oop.data_readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONDataReader {
    private String jsonStr;

    public JSONDataReader() {
        jsonStr = "";
    }

    public JSONDataReader(String addr) throws IOException {
        jsonStr = new String(Files.readAllBytes(Paths.get(addr)));
    }

    public String getData() {
        return jsonStr;
    }

    public void writeData(String addr) throws IOException {
        jsonStr = new String(Files.readAllBytes(Paths.get(addr)));
    }
}