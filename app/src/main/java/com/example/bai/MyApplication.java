package com.example.bai;

import android.app.Application;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class MyApplication extends Application {
    private static User loginUser;

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        MyApplication.loginUser = loginUser;
    }
}
