package com.example.bai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;


public class ResActivity extends AppCompatActivity {

    String id="ef9596c4652fba4a22c982a128cd7875";
    private EditText name,number,yanzhengma,mima;
    private Button send,regest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);

        Bmob.initialize(this, id);
        name= (EditText) findViewById(R.id.nicheng);
        number= (EditText) findViewById(R.id.shoujihao);
        yanzhengma= (EditText) findViewById(R.id.yanzhengma);
        mima= (EditText) findViewById(R.id.mima);
        send= (Button) findViewById(R.id.send);
        regest= (Button) findViewById(R.id.zhuceButton);

        regest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verySms();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }

    private void verySms() {
        String message=yanzhengma.getText().toString();
        String tel=number.getText().toString();
        BmobSMS.verifySmsCode(ResActivity.this, tel, message, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    write();
                } else {
                    Toast.makeText(ResActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void write() {
        String user_name=name.getText().toString();
        String uesr_pass = mima.getText().toString();
        String user_num=number.getText().toString();
        Person person = new Person();
        person.setName(user_name);
        person.setNumber(user_num);
        person.setPassword(uesr_pass);
        person.save(ResActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ResActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ResActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ResActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSms() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime=simpleDateFormat.format(new Date());
        String tel=number.getText().toString();
        BmobSMS.requestSMSCode(ResActivity.this, tel, "小白短信验证", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {


            }
        });

    }
}
