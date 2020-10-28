package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapter.CCEventHandlerAdapter;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.command.senders.CommandSender;
import ru.sbt.mipt.oop.command.senders.PretendingCommandSender;
import ru.sbt.mipt.oop.data.loaders.HomeLoader;
import ru.sbt.mipt.oop.data.loaders.JSONHomeLoader;
import ru.sbt.mipt.oop.event.processors.*;
import ru.sbt.mipt.oop.home.components.SmartHome;
import ru.sbt.mipt.oop.message.senders.MessageSender;
import ru.sbt.mipt.oop.message.senders.SMSMessageSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringConfiguration {
    @Bean
    public Map<String, SensorEventType> ccEventTypeToEventType() {
        return new HashMap<String, SensorEventType>() {{
            put("LightIsOn", SensorEventType.LIGHT_ON);
            put("LightIsOff", SensorEventType.LIGHT_OFF);
            put("DoorIsOpen", SensorEventType.DOOR_OPEN);
            put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        }};
    }

    @Bean
    public HomeLoader homeLoader() {
        return new JSONHomeLoader("smart-home-1.js");
    }

    @Bean
    public SmartHome smartHome() {
        SmartHome smartHome = homeLoader().loadHome();
        Alarm alarm = new Alarm(12345);
        smartHome.setAlarm(alarm);
        return smartHome;
    }

    @Bean
    public MessageSender messageSender() {
        return new SMSMessageSender();
    }

    @Bean
    CommandSender commandSender() {
        return new PretendingCommandSender();
    }

    @Bean
    SensorEventProcessor alarmEventProcessor() {
        return new AlarmEventProcessor(smartHome(), commandSender());
    }

    @Bean
    SensorEventProcessor doorEventProcessor() {
        return new DoorEventProcessor(smartHome(), commandSender());
    }

    @Bean
    SensorEventProcessor hallDoorEventProcessor() {
        return new HallDoorEventProcessor(smartHome(), commandSender());
    }

    @Bean
    SensorEventProcessor lightEventProcessor() {
        return new LightEventProcessor(smartHome(), commandSender());
    }

    @Bean
    @Autowired
    public SensorEventsManager sensorEventsManager(List<SensorEventProcessor> eventProcessors, SmartHome smartHome) {
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        for (SensorEventProcessor processor : eventProcessors) {
            sensorEventsManager.registerEventHandler(
                    new CCEventHandlerAdapter(
                            new SecurityProcessorDecorator(processor, messageSender(), smartHome),
                            smartHome,
                            ccEventTypeToEventType()
                    )
            );
        }
        return sensorEventsManager;
    }
}