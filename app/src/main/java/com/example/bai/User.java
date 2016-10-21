package com.example.bai;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class User  {
    private String person_name;
    private String person_tel;
    private String person_pass;

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_tel() {
        return person_tel;
    }

    public void setPerson_tel(String person_tel) {
        this.person_tel = person_tel;
    }

    public String getPerson_pass() {
        return person_pass;
    }

    public void setPerson_pass(String person_pass) {
        this.person_pass = person_pass;
    }

    public User(String person_name, String person_tel, String person_pass) {
        super();
        this.person_name = person_name;
        this.person_tel = person_tel;
        this.person_pass = person_pass;
    }
}
