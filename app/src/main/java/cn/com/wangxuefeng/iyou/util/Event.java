package cn.com.wangxuefeng.iyou.util;

public class Event {
    private String eventName;
    private IEventHandler eventHandler;

    public Event(String eventName, IEventHandler eventHandler) {
        super();
        this.eventName = eventName;
        this.eventHandler = eventHandler;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public IEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

}
