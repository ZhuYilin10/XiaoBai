package com.example.bai;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 逸林 on 2016/3/3.
 */
public class ShareHelper {

    private Context mContext;

    public ShareHelper() {
    }

    public ShareHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void save(String number, String password,String name)
    {
        User user=MyApplication.getLoginUser();
        number=user.getPerson_tel();
        name=user.getPerson_name();
        password=user.getPerson_pass();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", number);
        editor.putString("passwd", password);
        editor.putString("name",name);
        editor.commit();
    }

    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("passwd", sp.getString("passwd", ""));
        data.put("name",sp.getString("name",""));
        return data;
    }
}