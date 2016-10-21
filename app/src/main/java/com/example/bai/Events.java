package com.example.bai;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class Events {

    private String time;
    private String events;
    private String eventBeizhu;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getEventBeizhu() {
        return eventBeizhu;
    }

    public void setEventBeizhu(String eventBeizhu) {
        this.eventBeizhu = eventBeizhu;
    }

    public Events(String time, String events, String eventBeizhu) {
        super();
        this.time = time;
        this.events = events;
        this.eventBeizhu = eventBeizhu;
    }
}
