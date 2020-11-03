package ru.sbt.mipt.oop.remote.control;

import rc.RemoteControl;
import ru.sbt.mipt.oop.commands.Command;

import java.util.Map;

public class SmartHomeRemoteControl implements RemoteControl {
    private final Map<String, Command> buttonToCommandMap;
    private final String rcId;

    public SmartHomeRemoteControl(Map<String, Command> buttonToCommandMap, String rcId) {
        this.buttonToCommandMap = buttonToCommandMap;
        this.rcId = rcId;
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (!rcId.equals(this.rcId)) {
            return;
        }
        if (buttonToCommandMap.containsKey(buttonCode)) {
            buttonToCommandMap.get(buttonCode).execute();
        }
    }

    public void setCommandToButton(String buttonCode, Command command) {
        buttonToCommandMap.put(buttonCode, command);
    }

    public String getRcId() {
        return rcId;
    }
}