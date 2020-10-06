package ru.sbt.mipt.oop.home.components;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SmartHome implements Actionable {
    Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    @Override
    public void execute(Action action) {
        rooms.forEach(room->room.execute(action));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartHome smartHome = (SmartHome) o;
        return rooms.equals(smartHome.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rooms);
    }
}