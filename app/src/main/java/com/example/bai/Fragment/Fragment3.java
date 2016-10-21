package com.example.bai.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;


import com.example.bai.CreatEvent;
import com.example.bai.CreateActivity;
import com.example.bai.DetailActivity;
import com.example.bai.Events;
import com.example.bai.MyApplication;
import com.example.bai.R;
import com.example.bai.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 逸林 on 2016/3/1.
 */
public class Fragment3 extends Fragment {
    String calanderURL = "content://com.android.calendar/calendars";
    String calanderEventURL = "content://com.android.calendar/events";
    String calanderRemiderURL = "content://com.android.calendar/reminders";
    List<CreatEvent> eventsList=new ArrayList<CreatEvent>();
    String id="ef9596c4652fba4a22c982a128cd7875";
    EventsAdapter eventAdapter;
    User user=MyApplication.getLoginUser();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment4_layout, container, false);


        ListView listView= (ListView) rootView.findViewById(R.id.listView_eve1);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title", eventsList.get(position).getEvent());
                intent.putExtra("start_time", eventsList.get(position).getStartTime());
                intent.putExtra("end_time", eventsList.get(position).getEndTime());
                intent.putExtra("beizhu", eventsList.get(position).getBeizhu());
                intent.putExtra("weizhi", eventsList.get(position).getLocation());
                intent.putExtra("zhanghu", eventsList.get(position).getName());
                intent.putExtra("objectId", eventsList.get(position).getObjectId());
                startActivity(intent);
                getActivity().finish();
            }
        });


        Bmob.initialize(getContext(), id);
        eventAdapter=new EventsAdapter(eventsList);
        listView.setAdapter(eventAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "haaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        BmobQuery<CreatEvent> creatEventBmobQuery = new BmobQuery<CreatEvent>();

        User user4=MyApplication.getLoginUser();
        if (user4==null)
        {
            creatEventBmobQuery.addWhereEqualTo("name", "user");
            creatEventBmobQuery.findObjects(getActivity(), new FindListener<CreatEvent>() {
                @Override
                public void onSuccess(List<CreatEvent> list) {
                    if (list.size() == 0) {
                        eventsList.clear();
                        eventAdapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "您还没有日程安排", Toast.LENGTH_SHORT).show();
                    } else {
                        eventsList.clear();
                        eventsList.addAll(list);
                        eventAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });

        }
        else
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd");
            SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("MM");
            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy");
            Date curDate=new Date(System.currentTimeMillis());
            String day=simpleDateFormat.format(curDate);
            String mouth=simpleDateFormat2.format(curDate);
            String year=simpleDateFormat1.format(curDate);
            int mouth1=Integer.parseInt(mouth);
            int day1=Integer.parseInt(day);
            creatEventBmobQuery.addWhereEqualTo("name",user4.getPerson_tel());
            creatEventBmobQuery.addWhereEqualTo("startTime", year + "年" + mouth1 + "月" + day1 + "日");
            creatEventBmobQuery.findObjects(getActivity(), new FindListener<CreatEvent>() {
                @Override
                public void onSuccess(List<CreatEvent> list) {
                    if (list.size() == 0) {
                        eventsList.clear();
                        eventAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "您还没有日程安排", Toast.LENGTH_SHORT).show();
                    } else {
                        eventsList.clear();
                        eventsList.addAll(list);
                        eventAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

        CalendarView calendarView= (CalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "adsfasdfasdf", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                BmobQuery<CreatEvent> creatEventBmobQuery = new BmobQuery<CreatEvent>();
                if(user==null)
                {
                    creatEventBmobQuery.addWhereEqualTo("name", "user");
                    creatEventBmobQuery.addWhereEqualTo("startTime",year+"年"+(month+1)+"月"+dayOfMonth+"日");
                    creatEventBmobQuery.findObjects(getActivity(), new FindListener<CreatEvent>() {
                        @Override
                        public void onSuccess(List<CreatEvent> list) {
                            if (list.size() == 0) {
                                eventAdapter.notifyDataSetChanged();
                            } else {
                                eventsList.clear();
                                eventsList.addAll(list);
                                eventAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }

                else
                {
                    creatEventBmobQuery.addWhereEqualTo("name", user.getPerson_tel());
                    creatEventBmobQuery.addWhereEqualTo("startTime",year+"年"+(month+1)+"月"+dayOfMonth+"日");
                    creatEventBmobQuery.findObjects(getActivity(), new FindListener<CreatEvent>() {
                        @Override
                        public void onSuccess(List<CreatEvent> list) {
                            if (list.size() == 0) {
                                eventsList.clear();
                                eventAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "您还没有日程安排", Toast.LENGTH_SHORT).show();
                            } else {
                                eventsList.clear();
                                eventsList.addAll(list);
                                eventAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }



            }
        });



        return rootView;
    }



    private void selectEvents()
    {

    }


}
