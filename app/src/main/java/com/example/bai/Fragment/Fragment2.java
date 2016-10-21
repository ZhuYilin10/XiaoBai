package com.example.bai.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.bai.CalendarUtils;
import com.example.bai.MainActivity;
import com.example.bai.NongliActivity;
import com.example.bai.R;
import com.example.bai.Weather;
import com.example.bai.WeatherClass;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 逸林 on 2016/3/1.
 */
public class Fragment2 extends Fragment {


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyListener();
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    Weather weather=new Weather();
    TextView tem_high,tem_low;
    WeatherClass weatherClass0;
    TextView location;



    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    tem_high= (TextView) getActivity().findViewById(R.id.temp_high);
                    tem_low= (TextView) getActivity().findViewById(R.id.temp_low);
                    weatherClass0= (WeatherClass) msg.obj;
                    tem_high.setText(weatherClass0.getInfo().get(0).getNow().getCond().getTxt());
                    tem_low.setText(weatherClass0.getInfo().get(0).getNow().getTmp()+"℃");
                    break;
                default:
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment2_layout, container, false);


        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        set();


        TextView main_day= (TextView) rootView.findViewById(R.id.main_day);
        TextView main_mouth= (TextView) rootView.findViewById(R.id.main_mouth);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd");
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy");
        Date curDate=new Date(System.currentTimeMillis());
        String day=simpleDateFormat.format(curDate);
        String mouth=simpleDateFormat2.format(curDate);
        String year = simpleDateFormat3.format(curDate);
        main_day.setText(day);
        main_mouth.setText(mouth);

        int y = Integer.parseInt(year);
        int m = Integer.parseInt(mouth);
        int d = Integer.parseInt(day);

        CalendarUtils ca = new CalendarUtils();
        TextView nongliyue = (TextView) rootView.findViewById(R.id.nognliyue);
        nongliyue.setText(ca.getLunarString(y,m,d));
        nongliyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NongliActivity.class);
                startActivity(intent);
            }
        });
        TextView nonglinian = (TextView) rootView.findViewById(R.id.nonglinian);
        nonglinian.setText(ca.cyclical(y,m,d)+"年");


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void set() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    private class MyListener implements AMapLocationListener {
        String stringBuffer;
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    StringBuffer stringBuffer=new StringBuffer();
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    // stringBuffer.append("纬度："+amapLocation.getLatitude()+"\n") ;//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    // stringBuffer.append("省份："+amapLocation.getProvince()+"\n");//省信息
                    stringBuffer.append(amapLocation.getCity());//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    System.out.println(stringBuffer);
                    System.out.println(this.stringBuffer+"asdfsdafasfd");
                    if(this.stringBuffer==null||!this.stringBuffer.equals(stringBuffer.toString()))
                    {
                        this.stringBuffer=stringBuffer.toString();
                        location= (TextView) getActivity().findViewById(R.id.city_name);
                        String location_name=stringBuffer.substring(0,stringBuffer.length()-1);
                        System.out.println(location_name+"nihao");
                        location.setText(location_name);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(location.getText().toString());
                                String httpUrl = "http://apis.baidu.com/heweather/weather/free";
                                String httpArg = "city=" + Uri.encode(location.getText().toString());
                                String jsonResult = weather.request(httpUrl, httpArg);
                                System.out.println(jsonResult);
                                String json2 = jsonResult.replaceAll("HeWeather data service 3.0", "info");
                                System.out.println(json2);
                                Gson gson = new Gson();
                                WeatherClass weatherClass = gson.fromJson(json2, WeatherClass.class);
                                Message message=new Message();
                                message.what=1;
                                message.obj=weatherClass;
                                handler.sendMessage(message);
                            }
                        }).start();
                    }



                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    }







}
