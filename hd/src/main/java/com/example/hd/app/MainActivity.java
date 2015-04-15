package com.example.hd.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
/*定位*/
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
//如果使用地理围栏功能，需要import如下类
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hd.rock.dao.Rock;
import com.hd.rock.dao.rockdao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends Activity  {
    public MapView Bdmapview=null;
    private BaiduMap map;
    private long exitTime = 0;
    private int index=0;

    // private TextView editText;
    private double latitude=32.270115;
    private double longtitude=118.306446;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private Button bt_add;
    private Button bt_search;
    private Button bt_upfile;

    //监听按键并响应
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){

            if((System.currentTimeMillis()-exitTime) > 2000){
                //setContentView(R.layout.activity_index);

                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1,1,1,"查询");
        menu.add(1,2,2,"添加");
        menu.add(1,3,3,"上传");
        menu.add(1,4,4,"定位");
        //menu.getItem(4).setIntent(new Intent(this,activity_arcgismapvity_arcgismap.xml));
        //getMenuInflater().inflate(R.menu.test, menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 2:
                rockdao dao=new rockdao(this);
                dao.Add("32","118","20150105");

                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
            case 4:
               // setIntent(new Intent(this,LocationActivity.class));
              /*  Intent intent=new Intent();
                setIntent(LocationActivity.class);
                startActivity(intent);*/
                //map.hideInfoWindow();

        }
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_index);
       /* setContentView(R.layout.activity_index);*/

         Bdmapview= (MapView) findViewById(R.id.bmapView);
         bt_add= (Button) findViewById(R.id.bt_add);
        bt_search= (Button) findViewById(R.id.bt_search);
        bt_upfile= (Button) findViewById(R.id.bt_upfile);

       // editText= (TextView) findViewById(R.id.editText);


       //定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        InitialLocation();
        mLocationClient.start();
        InitialMap(Bdmapview);
        mLocationClient.stop();
         //加载数据库
       CopyDatabase();




    }
   //复制数据库到SD卡
    private void CopyDatabase() {
        try {
            InputStream db=getResources().openRawResource(R.raw.test);
            FileOutputStream fos=new FileOutputStream("/sdcard/test.db");
            byte[] buffer=new byte[8129];
            int count=0;
            //开始复制数据库

            while ((count =db.read(buffer)) >=0)
            {
                fos.write(buffer,0,count);
            }

            fos.close();
            db.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void InitialMap(MapView Bdmapview) {
        map = Bdmapview.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);
        //设定中心点坐标
        LatLng cenpt = new LatLng(latitude,longtitude);
    //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(16)
                .build();
      //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
        map.setMapStatus(mMapStatusUpdate);
        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }

    private void InitialLocation() {
        LocationClientOption option = new LocationClientOption();
               option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        Bdmapview.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        Bdmapview.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        Bdmapview.onPause();
    }

//按钮点击事件
    public void QUERY(View view) {
        String[] Serchcontent =new String[]{"磁性查询","电性查询","放射性查询","密度查询"};
        new AlertDialog.Builder(this).setTitle("请选择添加方式").setSingleChoiceItems(Serchcontent, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (index == 0) {
                    Intent intent_mg = new Intent(MainActivity.this, Serch_Magnetism.class);
                    startActivityForResult(intent_mg,1);

                    //startActivity(intent);




                } else if (index == 1) {
                    Intent intent = new Intent(MainActivity.this, addActivity.class);
                    startActivity(intent);
                }

            }


        }).show();
       /* ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.list);
        ExpandableListAdapter adapter =new MyExpandableListAdapter();
        expandableListView.setAdapter(adapter);
        Fragment fragment_query=new fragment_query();
        FragmentManager fg=getFragmentManager();
        FragmentTransaction ft=fg.beginTransaction();
        ft.replace(android.R.id.content,fragment_query);
        ft.commit();*/
        //Activity 跳转
       /* Intent intent = new Intent(MainActivity.this, SerchActivity.class);
        startActivity(intent);*/

     }
    public void ADD(final View view)
    {

        String[] method =new String[]{"当前位置","输入坐标"};
      new AlertDialog.Builder(this).setTitle("请选择添加方式").setSingleChoiceItems(method, 0, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              index = which;
          }
      }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
              if (index == 0) {
                  //new AlertDialog.Builder(MainActivity.this).setMessage("选择了"+ index ).show();
                  //定义Maker坐标点
                  LatLng point = new LatLng(32.270115, 118.306446);
                  //构建Marker图标
                  BitmapDescriptor bitmap = BitmapDescriptorFactory
                          .fromResource(R.drawable.ic_pointmaker);
                  //构建MarkerOption，用于在地图上添加Marker
                  OverlayOptions option = new MarkerOptions()
                          .position(point)
                          .icon(bitmap);
                  //在地图上添加Marker，并显示
                  map.addOverlay(option);
                  map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                      @Override
                      public boolean onMarkerClick(Marker marker) {
                          //创建InfoWindow展示的view
                          //Button button = new Button(getApplicationContext());
                          TextView location = new TextView(getApplicationContext());
                          //location.setBackgroundResource(R.drawable.location_tips);
                          location.setTextColor(Color.WHITE);
                          location.setBackgroundColor(Color.BLACK);
                          location.setPadding(30, 20, 30, 50);
                          location.setText("1号岩石样本");
                          // button.setBackgroundResource(R.drawable.ic_launcher);
                          //定义用于显示该InfoWindow的坐标点
                          LatLng pt = new LatLng(32.270115, 118.306446);
                          //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                          InfoWindow mInfoWindow = new InfoWindow(location, pt, -137);
                          //显示InfoWindow
                          map.showInfoWindow(mInfoWindow);
                          return true;
                      }
                  });
                  Intent intent=new Intent(MainActivity.this, addActivity.class);
                  intent.putExtra("经度",latitude);
                  intent.putExtra("纬度",longtitude);

                  startActivity(intent);
                 /* Fragment fragment_add=new com.fragment.fragment_add();
                  FragmentManager fg=getFragmentManager();
                  FragmentTransaction ft=fg.beginTransaction();
                  ft.add(android.R.id.content,fragment_add);
                  ft.commit();*/
              } else if (index == 1) {
                Intent intent=new Intent(MainActivity.this, addActivity.class);
                startActivity(intent);

                /*  View formview = view.inflate(MainActivity.this, R.layout.form_add, null);
                  setContentView(formview);*/
                  //formview.isShown();

              }
          }


      }).show();
    }

    public void UPLOAD(View view) {
        String[] method =new String[]{"上传所有数据","选择上传"};
        new AlertDialog.Builder(this).setTitle("请选择上传方式").setSingleChoiceItems(method, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (index == 0) {

                    Toast.makeText(getApplicationContext(), "上传数据成功", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                    startActivity(intent);
                }
            }


        }).show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==1)
        {
            if (resultCode==Serch_Magnetism.RESULT_OK)
            {
                //Intent intent_mg=getIntent();

              //  List<Rock> rocks = (List<Rock>) intent_mg.getSerializableExtra("Rocks");
                List<Rock> rocks = (List<Rock>)data.getSerializableExtra("Rocks");
                for (Rock i : rocks) {
                    double latitude = Double.parseDouble(i.getLatitude());
                    double longitude = Double.parseDouble(i.getLongitude());
                    LatLng pt1 = new LatLng(latitude,longitude);

                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_pointmaker);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(pt1)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示

                    map = Bdmapview.getMap();

                    map.addOverlay(option);
                    //定义地图状态
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(pt1)
                            .zoom(14)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                    MapStatusUpdate wMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    //改变地图状态
                    map.setMapStatus(wMapStatusUpdate);
                }
            }
        }
    }
    /*初始化定位类*/
    private class MyLocationListener implements BDLocationListener {
        @Override
                public void onReceiveLocation(BDLocation location) {
                  latitude =location.getLatitude();
                longtitude =location.getLongitude();
            /*  StringBuffer sb=new StringBuffer();
             sb.append("纬度"+location.getLatitude());
             sb.append("纬度"+location.getLongitude());
             editText.setText(sb.toString());*/



        }
    }

}
