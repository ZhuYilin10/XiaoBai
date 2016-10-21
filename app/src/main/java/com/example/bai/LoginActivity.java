package com.example.bai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
    private String id="ef9596c4652fba4a22c982a128cd7875";

    EditText num;
    EditText pass;
    Button login;
    private Context mContext;
    private ShareHelper sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, id);

        mContext=getApplicationContext();
        sh=new ShareHelper(mContext);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num= (EditText) findViewById(R.id.num);
                pass= (EditText) findViewById(R.id.pass);
                String tel = num.getText().toString();
                String password = pass.getText().toString();
                if (tel.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码与手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    BmobQuery<Person> person1 = new BmobQuery<Person>();
                    person1.addWhereEqualTo("number", tel);
                    person1.addWhereEqualTo("password", password);
                    person1.findObjects(LoginActivity.this, new FindListener<Person>() {
                        @Override
                        public void onSuccess(List<Person> list) {
                            if (list.size() == 0) {
                                Toast.makeText(LoginActivity.this, "手机号码与密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                User user = new User(list.get(0).getName(), list.get(0).getNumber(), list.get(0).getPassword());
                                MyApplication.setLoginUser(user);
                                sh.save(list.get(0).getNumber(), list.get(0).getPassword(),list.get(0).getName());
                                System.out.println(list.get(0).getName());
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        TextView zhuce= (TextView) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> data = sh.read();
        num= (EditText) findViewById(R.id.num);
        pass= (EditText) findViewById(R.id.pass);
        num.setText(data.get("username"));
        pass.setText(data.get("passwd"));
    }


}
