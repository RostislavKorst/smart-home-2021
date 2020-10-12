package ru.sbt.mipt.oop.alarm;

public class Alarm {
    private final int code;
    private AlarmState alarmState;

    public Alarm(int code, AlarmState alarmState) {
        this.code = code;
        this.alarmState = alarmState;
    }

    public Alarm(int code) {
        this.code = code;
        this.alarmState = new InactiveState();
    }

    public boolean isCorrectCode(int code) {
        return this.code == code;
    }

    public void activate(int codeInput) {
        alarmState.activate(codeInput);
    }

    public void deactivate(int codeInput) {
        alarmState.deactivate(codeInput);
    }

    public void trigger() {
        alarmState.trigger();
    }

    public boolean isActivated() {
        return this.alarmState instanceof ActiveState;
    }

    public boolean isDeactivated() {
        return this.alarmState instanceof InactiveState;
    }

    public boolean isTriggered() {
        return this.alarmState instanceof TriggerState;
    }

    private void setAlarmState(AlarmState alarmState) {
        this.alarmState = alarmState;
    }

    private class ActiveState implements AlarmState {
        @Override
        public void activate(int codeInput) {
            System.out.println("Alarm is already active");
        }

        @Override
        public void deactivate(int codeInput) {
            if (isCorrectCode(codeInput)) {
                setAlarmState(new InactiveState());
                System.out.println("Alarm was deactivated");
            } else {
                setAlarmState(new TriggerState());
                System.out.println("Wrong input code. Alarm was triggered!");
            }
        }

        @Override
        public void trigger() {
            setAlarmState(new TriggerState());
            System.out.println("Alarm was triggered!");
        }
    }

    private class InactiveState implements AlarmState {
        @Override
        public void activate(int codeInput) {
            if (isCorrectCode(codeInput)) {
                setAlarmState(new ActiveState());
                System.out.println("Alarm was activated");
            } else {
                setAlarmState(new TriggerState());
                System.out.println("Wrong input code. Alarm was triggered!");
            }
        }

        @Override
        public void deactivate(int codeInput) {
            System.out.println("Alarm is already inactive");
        }

        @Override
        public void trigger() {
            setAlarmState(new TriggerState());
            System.out.println("Alarm was triggered!");
        }
    }

    private class TriggerState implements AlarmState {
        @Override
        public void activate(int codeInput) {
            System.out.println("Alarm was triggered, you can not activate it");
        }

        @Override
        public void deactivate(int codeInput) {
            if (isCorrectCode(codeInput)) {
                setAlarmState(new InactiveState());
                System.out.println("Alarm was deactivated");
            } else {
                System.out.println("Wrong code!");
            }
        }

        @Override
        public void trigger() {
            System.out.println("Alarm is already triggered");
        }
    }
}