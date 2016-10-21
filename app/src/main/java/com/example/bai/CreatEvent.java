package com.example.bai;

import cn.bmob.v3.BmobObject;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class CreatEvent extends BmobObject {
    private String name;
    private String event;
    private String startTime;
    private String endTime;
    private String location;
    private String beizhu;



    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public CreatEvent() {
        super();
    }

    public CreatEvent(String name, String startTime, String endTime, String location, String beizhu,String event) {
        super();
        this.name = name;
        this.event=event;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.beizhu = beizhu;
    }


}
