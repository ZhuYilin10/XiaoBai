package com.example.bai;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class DetailActivity extends AppCompatActivity {
    EditText titleName,zhanghuName,startTime,endTime,location,beizhuName;
    CreatEvent creatEvent;
    String object;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String zhanghu = intent.getStringExtra("zhanghu");
        String start_time = intent.getStringExtra("start_time");
        String end_time = intent.getStringExtra("end_time");
        String beizhu = intent.getStringExtra("beizhu");
        String weizhi = intent.getStringExtra("weizhi");

        ImageView exit = (ImageView) findViewById(R.id.detial_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        titleName = (EditText) findViewById(R.id.detial_event);
        startTime = (EditText) findViewById(R.id.detial_time_star);
        endTime = (EditText) findViewById(R.id.detial_time_end);
        zhanghuName = (EditText) findViewById(R.id.detail_name);
        beizhuName = (EditText) findViewById(R.id.detial_and);
        location = (EditText) findViewById(R.id.detial_place);
        titleName.setText(title);
        titleName.setEnabled(false);
        startTime.setText(start_time);
        endTime.setText(end_time);
        zhanghuName.setText(zhanghu);
        zhanghuName.setEnabled(false);
        beizhuName.setText(beizhu);
        location.setText(weizhi);
        delete = (Button) findViewById(R.id.detial_del);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOnline();
                ContentResolver contentResolver = getContentResolver();
                ContentValues values = new ContentValues();
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.detail_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<CreatEvent> create = new BmobQuery<CreatEvent>();
                create.addWhereEqualTo("event", titleName.getText().toString());
                create.addWhereEqualTo("name", zhanghuName.getText().toString());
                System.out.println(titleName.getText().toString() + "===================" + zhanghuName.getText().toString());
                create.findObjects(DetailActivity.this, new FindListener<CreatEvent>() {
                    @Override
                    public void onSuccess(List<CreatEvent> list) {
                        if (list.size() == 0) {
                            Toast.makeText(DetailActivity.this, "11111", Toast.LENGTH_SHORT).show();
                        } else {
                            creatEvent = new CreatEvent();
                            creatEvent.setEvent(titleName.getText().toString());
                            creatEvent.setBeizhu(beizhuName.getText().toString());
                            creatEvent.setEndTime(endTime.getText().toString());
                            creatEvent.setStartTime(startTime.getText().toString());
                            creatEvent.setLocation(location.getText().toString());
                            object = list.get(0).getObjectId();
                            creatEvent.update(DetailActivity.this,object, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(DetailActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                    Intent i =new Intent(DetailActivity.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        });

    }

    private void delOnline() {
        BmobQuery<CreatEvent> create = new BmobQuery<CreatEvent>();
        create.addWhereEqualTo("event", titleName.getText().toString());
        create.addWhereEqualTo("name", zhanghuName.getText().toString());
        create.findObjects(DetailActivity.this, new FindListener<CreatEvent>() {
            @Override
            public void onSuccess(List<CreatEvent> list) {
                if (list.size() == 0) {
                    Toast.makeText(DetailActivity.this, "11111", Toast.LENGTH_SHORT).show();
                } else {
                    creatEvent = new CreatEvent();
                    String object = list.get(0).getObjectId();
                    creatEvent.setObjectId(object);
                    creatEvent.delete(DetailActivity.this, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DetailActivity.this, "该事件已删除", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


}
