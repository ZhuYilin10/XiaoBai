package com.example.bai;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bai.Fragment.EventsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends AppCompatActivity {
    EditText search_edit;
    ListView search_event;
    List<CreatEvent> creatEventList = new ArrayList<CreatEvent>();
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_edit = (EditText) findViewById(R.id.searche_edit);
        search_event = (ListView) findViewById(R.id.search_event);
        search_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
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


        search_event.setDivider(null);
        search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()){
                    String title = search_edit.getText().toString();
                    System.out.println(title);
                    User user=MyApplication.getLoginUser();
                    if (user!=null){
                        String userName = user.getPerson_tel();
                        BmobQuery<CreatEvent> creatEventBmobQuery = new BmobQuery<CreatEvent>();
                        creatEventBmobQuery.addWhereEqualTo("name",userName);
                        creatEventBmobQuery.addWhereContains("event",title);
                        creatEventBmobQuery.findObjects(SearchActivity.this, new FindListener<CreatEvent>() {
                            @Override
                            public void onSuccess(List<CreatEvent> list) {
                                if (list.size()==0){
                                    Toast.makeText(SearchActivity.this, "对不起没有找到相关事务", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    creatEventList = list;
                                    search_event.setAdapter(new MyAdapter());
                                    myAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(int i, String s) {

                            }
                        });
                    }
                }
                return false;
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
