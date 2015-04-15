package com.example.hd.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by whs on 2014/11/3.
 */
public class LocationActivity extends Activity {
    private TextView LoctionText;
    private double latitude=0;
    private double longtitude=0;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_3);
        LoctionText= (TextView) findViewById(R.id.editText);
        //定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        InitialLocation();
        mLocationClient.start();

        mLocationClient.stop();
    }
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude =location.getLatitude();
            longtitude =location.getLongitude();
              StringBuffer sb=new StringBuffer();
             sb.append("纬度"+location.getLatitude());
             sb.append("纬度"+location.getLongitude());
            LoctionText.setText(sb.toString());



        }
    }
    private void InitialLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        /**
         84          * 返回国测局经纬度坐标系 coor=gcj02
         85          * 返回百度墨卡托坐标系 coor=bd09
         86          * 返回百度经纬度坐标系 coor=bd09ll
         87          */
        option.setCoorType("bd09ll");// 百度手机地图对外接口中的坐标系默认是bd09ll，如果配合百度地图产品的话，需要注意坐标系对应问题
        /**
         90          * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation()
         91          * 后，每隔设定的时间，定位SDK就会进行一次定位。
         92          * 如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；
         93          * 如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。定时定位时，调用一次requestLocation，会定时监听到定位结果。
         94          *
         95          * 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。每调用一次requestLocation()，
         96          * 定位SDK会发起一次定位。请求定位与监听结果一一对应。
         97          *
         98          * 设定了定时定位后，可以热切换成一次定位，需要重新设置时间间隔小于1000（ms）即可。locationClient对象stop后，将不再进行定位
         99          * 。 如果设定了定时定位模式后，多次调用requestLocation（），则是每隔一段时间进行一次定位，同时额外的定位请求也会进行定位，
         100          * 但频率不会超过1秒一次。
         101          */
        option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        option.setOpenGps(true); // 打开GPS
        // String 值为all时，表示返回地址信息，其他值都表示不返回地址信息(官方指南说有这个方法，但类中却没有，不知道为什么)
        // option.setAddrType("all");
        option.setProdName("com.example.textandroid"); // 设置产品线名称，百度建议
        mLocationClient.setLocOption(option);

    }
}
