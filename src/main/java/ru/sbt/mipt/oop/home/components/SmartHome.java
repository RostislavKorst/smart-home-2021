package ru.sbt.mipt.oop.home.components;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;
import ru.sbt.mipt.oop.alarm.Alarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SmartHome implements Actionable {
    Collection<Room> rooms;
    private Alarm alarm;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Alarm getAlarm() {
        if (alarm != null) {
            return alarm;
        }
        System.out.println("There is no alarm mounted");
        return null;
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
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