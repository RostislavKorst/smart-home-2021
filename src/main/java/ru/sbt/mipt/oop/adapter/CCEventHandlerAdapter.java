package ru.sbt.mipt.oop.adapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.SensorEvent;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.event.processors.SensorEventProcessor;
import ru.sbt.mipt.oop.home.components.SmartHome;

import java.util.Map;

public class CCEventHandlerAdapter implements EventHandler {
    private final SensorEventProcessor sensorEventProcessor;
    private final SmartHome smartHome;
    private final Map<String, SensorEventType> ccEventTypeToEventType;

    public CCEventHandlerAdapter(SensorEventProcessor sensorEventProcessor, SmartHome smartHome,
                                 Map<String, SensorEventType> ccEventTypeToEventTypeMap) {
        this.sensorEventProcessor = sensorEventProcessor;
        this.smartHome = smartHome;
        this.ccEventTypeToEventType = ccEventTypeToEventTypeMap;
    }

    private SensorEvent convertCCSensorEventToSensorEvent(CCSensorEvent event){
        if(ccEventTypeToEventType.containsKey(event.getEventType()))
            return new SensorEvent(ccEventTypeToEventType.get(event.getEventType()), event.getObjectId());
        else
            return null;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        SensorEvent sensorEvent = convertCCSensorEventToSensorEvent(event);
        if (sensorEvent != null) {
            sensorEventProcessor.process(sensorEvent);
        }
    }
}
