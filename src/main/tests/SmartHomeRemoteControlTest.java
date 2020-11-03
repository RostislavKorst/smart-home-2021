package tests;

import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.command.senders.*;
import ru.sbt.mipt.oop.commands.*;
import ru.sbt.mipt.oop.data.loaders.*;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.remote.control.SmartHomeRemoteControl;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SmartHomeRemoteControlTest {
    private String resources;
    private SmartHome homeWithAllLightsOff;
    private SmartHome homeWithAllLightsOn;
    private SmartHome homeWithHallLightsOff;
    private SmartHome homeWithHallLightsOn;
    private SmartHome homeWithHallDoorOpened;
    private SmartHome homeWithHallDoorClosed;
    private SmartHomeRemoteControl smartHomeRemoteControl;
    private CommandSender commandSender;

    @Before
    public void setUpHomeState() {
        resources = "src/main/tests/resources/remote/control/";

        HomeLoader homeLoader1 = new JSONHomeLoader(resources + "home_with_all_lights_off.json");
        HomeLoader homeLoader2 = new JSONHomeLoader(resources + "home_with_all_lights_on.json");
        HomeLoader homeLoader3 = new JSONHomeLoader(resources + "home_with_hall_light_off.json");
        HomeLoader homeLoader4 = new JSONHomeLoader(resources + "home_with_hall_light_on.json");
        HomeLoader homeLoader5 = new JSONHomeLoader(resources + "home_with_hall_door_opened.json");
        HomeLoader homeLoader6 = new JSONHomeLoader(resources + "home_with_hall_door_closed.json");

        homeWithAllLightsOff = homeLoader1.loadHome();
        homeWithAllLightsOn = homeLoader2.loadHome();
        homeWithHallLightsOff = homeLoader3.loadHome();
        homeWithHallLightsOn = homeLoader4.loadHome();
        homeWithHallDoorOpened = homeLoader5.loadHome();
        homeWithHallDoorClosed = homeLoader6.loadHome();

        commandSender = new PretendingCommandSender();

        smartHomeRemoteControl = new SmartHomeRemoteControl(new HashMap<>(), "1");
    }

    @Test
    public void onButtonPressed_TurnOffAllLightsCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("A",
                new TurnOffAllLightsCommand(homeWithAllLightsOn, commandSender));
        //when
        smartHomeRemoteControl.onButtonPressed("A", "1");
        //then
        assertEquals(homeWithAllLightsOff, homeWithAllLightsOn);
    }

    @Test
    public void onButtonPressed_TurnOnAllLightsCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("B",
                new TurnOnAllLightsCommand(homeWithAllLightsOff, commandSender));
        //when
        smartHomeRemoteControl.onButtonPressed("B", "1");
        //then
        assertEquals(homeWithAllLightsOn, homeWithAllLightsOff);
    }

    @Test
    public void onButtonPressed_TurnOnHallLightsCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("C",
                new TurnOnHallLightsCommand(homeWithHallLightsOff, commandSender));
        //when
        smartHomeRemoteControl.onButtonPressed("C", "1");
        //then
        assertEquals(homeWithHallLightsOff, homeWithHallLightsOn);
    }

    @Test
    public void onButtonPressed_CloseHallDoorCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("D",
                new CloseHallDoorCommand(homeWithHallDoorOpened, commandSender));
        //when
        smartHomeRemoteControl.onButtonPressed("D", "1");
        //then
        assertEquals(homeWithHallDoorOpened, homeWithHallDoorClosed);
    }

    @Test
    public void onButtonPressed_ActivateAlarmCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("1",
                new ActivateAlarmCommand(homeWithHallDoorOpened, commandSender, 12345));
        homeWithHallDoorOpened.setAlarm(new Alarm(12345));
        //when
        smartHomeRemoteControl.onButtonPressed("1", "1");
        //then
        assertTrue(homeWithHallDoorOpened.getAlarm().isActivated());
    }

    @Test
    public void onButtonPressed_TriggerAlarmCommand_ChangeHomeState() {
        //given
        smartHomeRemoteControl.setCommandToButton("2",
                new TriggerAlarmCommand(homeWithHallDoorOpened, commandSender));
        homeWithHallDoorOpened.setAlarm(new Alarm(12345));
        //when
        smartHomeRemoteControl.onButtonPressed("2", "1");
        //then
        assertTrue(homeWithHallDoorOpened.getAlarm().isTriggered());
    }
}