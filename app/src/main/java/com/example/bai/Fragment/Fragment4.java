package com.example.bai.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bai.CreatEvent;
import com.example.bai.Events;
import com.example.bai.MyApplication;
import com.example.bai.R;
import com.example.bai.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 逸林 on 2016/3/1.
 */
public class Fragment4 extends Fragment {
    private List<CreatEvent> eventsList=new ArrayList<CreatEvent>();
    String id="ef9596c4652fba4a22c982a128cd7875";
    EventsAdapter eventAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment5_layout, container, false);

        ListView listView= (ListView) rootView.findViewById(R.id.listView_eve);
        Bmob.initialize(getContext(), id);
        eventAdapter=new EventsAdapter(eventsList);
        listView.setAdapter(eventAdapter);

        BmobQuery<CreatEvent> creatEventBmobQuery=new BmobQuery<CreatEvent>();
        User user= MyApplication.getLoginUser();
        creatEventBmobQuery.addWhereEqualTo("name","15161028557");
        creatEventBmobQuery.findObjects(getActivity(), new FindListener<CreatEvent>() {
            @Override
            public void onSuccess(List<CreatEvent> list) {
                if (list.size()==0)
                {
                    Toast.makeText(getActivity(), "您还没有日程安排", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    eventsList.addAll(list);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        return rootView;

    }


}
