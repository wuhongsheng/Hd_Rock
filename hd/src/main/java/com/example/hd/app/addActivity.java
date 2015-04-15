package com.example.hd.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.rock.dao.rockdao;


public class addActivity extends ActionBarActivity {
    private EditText ET_latitude;
    private EditText ET_longitude;
    private EditText ET_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
         ET_latitude= (EditText) findViewById(R.id.ET_latitude);
         ET_longitude= (EditText) findViewById(R.id.ET_longitude);
         ET_time= (EditText) findViewById(R.id.ET_time);
        Intent intent=getIntent();
        double latitude=intent.getDoubleExtra("经度", 0);
        double longitude=intent.getDoubleExtra("纬度",0);
        ET_latitude.setText(String.valueOf(latitude));
        ET_longitude.setText(longitude+"");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submit(View view) {
        String latitude=ET_latitude.getText().toString();
        String longitude=ET_longitude.getText().toString();
        String time=ET_time.getText().toString();
        rockdao dao=new rockdao(this);
        dao.Add(latitude,longitude,time);
        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
        this.finishActivity(0);
    }
}
