package com.example.bai;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class CreateActivity extends AppCompatActivity {
    private User user;
    TextView account;
    TextView star_min1, star_min, startTime, endTime;
    EditText loc, bei, eventName;
    int starty, startm, startd, endy, endm, endd, starh, starm, endh, endM;
    String id = "ef9596c4652fba4a22c982a128cd7875";
    public static final String[] EVENT_PROJECTION = new String[]
            {
                    CalendarContract.Calendars._ID,
                    CalendarContract.Calendars.ACCOUNT_NAME,
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                    CalendarContract.Calendars.OWNER_ACCOUNT
            };
    public static final String[] EVENT_PROJECTION1 = new String[]
            {
                    CalendarContract.Events.CALENDAR_ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DESCRIPTION,
            };

    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    String calanderURL = "content://com.android.calendar/calendars";
    String calanderEventURL = "content://com.android.calendar/events";
    String calanderRemiderURL = "content://com.android.calendar/reminders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Bmob.initialize(this, id);
        account = (TextView) findViewById(R.id.account_name);
        user = MyApplication.getLoginUser();
        if (user == null) {
            account.setText("localhost");
        } else {
            account.setText(user.getPerson_tel());
        }
        ImageView exit_image = (ImageView) findViewById(R.id.exit);
        exit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        star_min = (TextView) findViewById(R.id.account_time_min);
        star_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        star_min.setText(hourOfDay + "：" + minute);
                        starh = hourOfDay;
                        starm = minute;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }
        });
        star_min1 = (TextView) findViewById(R.id.account_time_min2);

        star_min1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        star_min1.setText(hourOfDay + "：" + minute);
                        endh = hourOfDay;
                        endM = minute;
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });


        startTime = (TextView) findViewById(R.id.account_time_star);
        endTime = (TextView) findViewById(R.id.account_time_end);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(CreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endTime.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        endy = year;
                        endm = monthOfYear;
                        endd = dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(CreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startTime.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        starty = year;
                        startm = monthOfYear;
                        startd = dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.fab);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                online();
                localSave();
            }
        });
    }

    private void localSave() {
        user = MyApplication.getLoginUser();
        String name = user.getPerson_tel();
        String starTime = startTime.getText().toString();
        String endtime = endTime.getText().toString();
        loc = (EditText) findViewById(R.id.account_place);
        String location = loc.getText().toString();
        bei = (EditText) findViewById(R.id.account_and);
        String beizhu = bei.getText().toString();
        eventName = (EditText) findViewById(R.id.event_name);
        String eventname = eventName.getText().toString();
        EditText tixing = (EditText) findViewById(R.id.account_event_object);
        String tixingshijian = tixing.getText().toString();

        long calID = 1;
        long startMillis = 0;
        long endMillis = 0;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(starty, startm, startd, starh, starm);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endy, endm, endd, endh, endm);
        endMillis = endTime.getTimeInMillis();
        System.out.println(endy + "年" + endd + "riqi" + "yue" + endm + 1);


        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, eventname);
        values.put(CalendarContract.Events.DESCRIPTION, beizhu);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);

        Uri newEvents = contentResolver.insert(Uri.parse(calanderEventURL), values);

        long id = Long.parseLong(newEvents.getLastPathSegment());
        ContentResolver contentResolver2 = getContentResolver();
        ContentValues values2 = new ContentValues();
        values2.put(CalendarContract.Reminders.MINUTES, tixingshijian);
        values2.put(CalendarContract.Reminders.EVENT_ID, id);
        values2.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = contentResolver2.insert(Uri.parse(calanderRemiderURL), values2);
        Toast.makeText(CreateActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    private void online() {
        user=MyApplication.getLoginUser();
        String name=user.getPerson_tel();
        String starTime=startTime.getText().toString();
        String endtime=endTime.getText().toString();
        loc= (EditText) findViewById(R.id.account_place);
        String location=loc.getText().toString();
        bei= (EditText) findViewById(R.id.account_and);
        String beizhu=bei.getText().toString();
        eventName= (EditText) findViewById(R.id.event_name);
        String eventname=eventName.getText().toString();

        CreatEvent creatEvent=new CreatEvent();
        creatEvent.setName(name);
        creatEvent.setStartTime(starTime);
        creatEvent.setEndTime(endtime);
        creatEvent.setLocation(location);
        creatEvent.setBeizhu(beizhu);
        creatEvent.setEvent(eventname);

        creatEvent.save(CreateActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(CreateActivity.this, "云端存储成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CreateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(CreateActivity.this, "添加失败，请检查网络后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
