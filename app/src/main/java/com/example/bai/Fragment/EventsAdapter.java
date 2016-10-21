package com.example.bai.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.bai.CreatEvent;
import com.example.bai.Events;
import com.example.bai.R;

import java.util.List;

/**
 * Created by 逸林 on 2016/3/2.
 */
public class EventsAdapter extends BaseAdapter {
    private List<CreatEvent> eventsList;

    public EventsAdapter(List<CreatEvent> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
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
            viewHolder.time= (TextView) convertView.findViewById(R.id.eventtime);
            viewHolder.beizhu= (TextView) convertView.findViewById(R.id.eventsbeizhu);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.events.setText(eventsList.get(position).getEvent());
        viewHolder.time.setText(eventsList.get(position).getStartTime()+"——"+eventsList.get(position).getEndTime());
        viewHolder.beizhu.setText(eventsList.get(position).getBeizhu());
        return convertView;
    }

    private class ViewHolder {
        public TextView time;
        public TextView events;
        public TextView beizhu;
    }
}
