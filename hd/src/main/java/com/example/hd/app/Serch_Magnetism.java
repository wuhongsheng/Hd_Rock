package com.example.hd.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.hd.rock.dao.Rock;
import com.hd.rock.dao.rockdao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.hd.app.R.id.bmapView;


public class Serch_Magnetism extends Activity {
   private EditText Et_mgmax;
    private EditText Et_mgmin;
    private EditText Et_mgavg;
    private EditText Et_mgqd;
    public MapView Bdmapview=null;
    public BaiduMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch__magnetism);

        Et_mgmax=(EditText)findViewById(R.id.mgmax);
        Et_mgmin=(EditText)findViewById(R.id.mgmin);
        Et_mgavg=(EditText)findViewById(R.id.mg_ev);
        Et_mgqd=(EditText)findViewById(R.id.mg_qd);
        LayoutInflater layout   =  LayoutInflater.from(Serch_Magnetism.this);
        View view=layout.inflate(R.layout.activity_main, null);
        Bdmapview= (MapView) view.findViewById(R.id.bmapView);



    }
    public  void  mgserch (View view)
    {
        String mgmax=Et_mgmax.getText().toString();
        String mgmin=Et_mgmin.getText().toString();
        String mg_ev=Et_mgavg.getText().toString();
        String mg_qd=Et_mgqd.getText().toString();
        rockdao dao=new rockdao(this);
        List<Rock> rocks=dao.Serch(mgmax,mgmin,mg_ev,mg_qd);
        if(rocks !=null)
        {
            Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
            Intent intent_mg = getIntent();
            intent_mg.putExtra("Rocks",(Serializable)rocks);
            this.setResult(RESULT_OK,intent_mg);
            this.finish();
        }
        else
        {
            Toast.makeText(this, "未查询到相关数据", Toast.LENGTH_SHORT).show();

        }



       // this.finishActivity(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.serch__magnetism, menu);
        return true;
    }


}
