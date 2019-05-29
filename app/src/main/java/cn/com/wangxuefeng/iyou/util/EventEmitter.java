package cn.com.wangxuefeng.iyou.util;

import java.util.ArrayList;
import java.util.List;

public class EventEmitter {
    private List<Event> eventList = new ArrayList<Event>();

    public EventEmitter on(String eventName, IEventHandler eventHandler) {
        eventList.add(new Event(eventName, eventHandler));
        return this;
    }

    public void emit(String eventName, List<Object> params) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) {
                event.getEventHandler().handleEvent(params);
            }
        }
    }
}
