package com.example.bai;

import cn.bmob.v3.BmobObject;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class Person extends BmobObject {
    private String name;
    private String number;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person() {
        super();
    }

    public Person(String name, String number, String password) {
        super();
        this.name = name;
        this.number = number;
        this.password = password;
    }
}
