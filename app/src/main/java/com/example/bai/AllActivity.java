package com.example.bai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class AllActivity extends AppCompatActivity {
    ListView all_list;
    List<CreatEvent> creatEventList = new ArrayList<CreatEvent>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        User user=MyApplication.getLoginUser();
        String name = user.getPerson_tel();
        all_list = (ListView) findViewById(R.id.all_list);
        all_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllActivity.this,DetailActivity.class);
                intent.putExtra("title",creatEventList.get(position).getEvent());
                intent.putExtra("start_time",creatEventList.get(position).getStartTime());
                intent.putExtra("end_time",creatEventList.get(position).getEndTime());
                intent.putExtra("beizhu",creatEventList.get(position).getBeizhu());
                intent.putExtra("weizhi",creatEventList.get(position).getLocation());
                intent.putExtra("zhanghu",creatEventList.get(position).getName());
                intent.putExtra("objectId",creatEventList.get(position).getObjectId());
                startActivity(intent);
                finish();
            }
        });
        BmobQuery<CreatEvent> creatEventBmobQuery = new BmobQuery<CreatEvent>();
        creatEventBmobQuery.addWhereEqualTo("name",name);
        creatEventBmobQuery.findObjects(AllActivity.this, new FindListener<CreatEvent>() {
            @Override
            public void onSuccess(List<CreatEvent> list) {
                if (list.size()==0){
                    Toast.makeText(AllActivity.this, "您还没有任何事物", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyAdapter myAdapter = new MyAdapter();
                    creatEventList = list;
                    all_list.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return creatEventList.size();
        }

        @Override
        public Object getItem(int position) {
            return creatEventList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(null==convertView)
            {
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_events,null,false);
                viewHolder=new ViewHolder();
                viewHolder.events= (TextView) convertView.findViewById(R.id.eventname);
                viewHolder.times= (TextView) convertView.findViewById(R.id.eventtime);
                viewHolder.describe= (TextView) convertView.findViewById(R.id.eventsbeizhu);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.events.setText(creatEventList.get(position).getEvent());
            viewHolder.times.setText(creatEventList.get(position).getStartTime()+"——"+creatEventList.get(position).getEndTime());
            viewHolder.describe.setText(creatEventList.get(position).getBeizhu());
            return convertView;
        }
        public class ViewHolder{
            TextView events;
            TextView times;
            TextView describe;
        }
    }
}
