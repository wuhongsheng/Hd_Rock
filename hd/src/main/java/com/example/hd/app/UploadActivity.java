package com.example.hd.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.FileUpload;
import com.hd.rock.dao.Rock;
import com.hd.rock.dao.rockdao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UploadActivity extends ActionBarActivity {
     ListView listView;
    List<Rock> rocks=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        rockdao dao=new rockdao(this);
        rocks = dao.FindAll();
        listView= (ListView) findViewById(R.id.lv);
        listView.setAdapter(new lvAdapter(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
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

    public void Upload(View view) throws FileNotFoundException {

        File file=new File("/sdcard/test.db");
        if(file.exists()&&file.length()>0)
        {
            String url="http://192.168.0.101:8080/RockServers/RockServlet";
            FileUpload fu = new FileUpload();
            fu.UpLoad(url, file, this);
           /* AsyncHttpClient asyclient=new AsyncHttpClient();

            RequestParams params=new RequestParams();
            params.put("db",file);
            String username="wu";
            params.put("username",username);
            asyclient.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
            });*/
        }else{
            Toast.makeText(UploadActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
        }


    }

    private  class lvAdapter extends BaseAdapter {
        public  Map<Integer, Boolean> isSelected;
        private LayoutInflater mInflater;
        public lvAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            init();
        }

        private void init() {
            isSelected = new HashMap<Integer, Boolean>();
            for (int i = 0; i < rocks.size(); i++) {
                isSelected.put(i, false);
            }
        }

        @Override
        public int getCount() {
            return rocks.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv=new TextView(UploadActivity.this);
            tv.setTextSize(20);
            tv.setHeight(50);
            tv.setTextColor(Color.BLACK);
            Rock rock=rocks.get(position);
            tv.setText(rock.getTime().toString()+"岩石样本");
            return tv;
        }
    }
}
