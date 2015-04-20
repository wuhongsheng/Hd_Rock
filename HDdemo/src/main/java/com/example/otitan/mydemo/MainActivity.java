package com.example.otitan.mydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


public class MainActivity extends Activity {
    EditText et_name;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    public void Login(View view) {
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String url = "http://192.168.1.112:8080/WuServer/loginservlet";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("password", password);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void signin(View view) {
        Intent intent = new Intent(MainActivity.this, ItemlistActivity.class);
        startActivity(intent);


    }

}