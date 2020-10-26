package tests;

import org.junit.Test;
import ru.sbt.mipt.oop.alarm.Alarm;

import static org.junit.Assert.*;

public class AlarmTest {
    @Test
    public void activate_InactiveState_InputCodeEqualsToActual_AlarmActivated() {
        //given
        Alarm alarm = new Alarm(12345);
        //when
        alarm.activate(12345);
        //then
        assertTrue(alarm.isActivated());
    }

    @Test
    public void activate_InactiveState_InputCodeNotEqualsToActual_AlarmTriggered() {
        //given
        Alarm alarm = new Alarm(12345);
        //when
        alarm.activate(11111);
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void activate_ActiveState_InputCodeEqualsToActual_AlarmActivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.activate(12345);
        //when
        alarm.activate(12345);
        //then
        assertTrue(alarm.isActivated());
    }

    @Test
    public void activate_ActiveState_InputCodeNotEqualsToActual_AlarmActivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.activate(12345);
        //when
        alarm.activate(11111);
        //then
        assertTrue(alarm.isActivated());
    }

    @Test
    public void activate_TriggerState_InputCodeEqualsToActual_AlarmTriggered() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.trigger();
        //when
        alarm.activate(12345);
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void activate_TriggerState_InputCodeNotEqualsToActual_AlarmTriggered() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.trigger();
        //when
        alarm.activate(11111);
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void deactivate_ActiveState_InputCodeEqualsToActual_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.activate(12345);
        //when
        alarm.deactivate(12345);
        //then
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void deactivate_ActiveState_InputCodeNotEqualsToActual_AlarmTriggered() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.activate(12345);
        //when
        alarm.deactivate(11111);
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void deactivate_InactiveState_InputCodeEqualsToActual_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        //when
        alarm.deactivate(12345);
        //then
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void deactivate_InactiveState_InputCodeNotEqualsToActual_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        //when
        alarm.deactivate(11111);
        //then
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void deactivate_TriggerState_InputCodeEqualsToActual_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.trigger();
        //when
        alarm.deactivate(12345);
        //then
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void deactivate_TriggerState_InputCodeNotEqualsToActual_AlarmTriggered() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.trigger();
        //when
        alarm.deactivate(11111);
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void trigger_ActiveState_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.activate(12345);
        //when
        alarm.trigger();
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void trigger_InactiveState_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        //when
        alarm.trigger();
        //then
        assertTrue(alarm.isTriggered());
    }

    @Test
    public void trigger_TriggerState_AlarmDeactivated() {
        //given
        Alarm alarm = new Alarm(12345);
        alarm.trigger();
        //when
        alarm.trigger();
        //then
        assertTrue(alarm.isTriggered());
    }
}