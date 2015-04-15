package com.example.hd.app;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class SerchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.list);
        ExpandableListAdapter adapter =new MyExpandableListAdapter();
        expandableListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_serch, menu);
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

    public void QUERY(View view) {
    }

    /* 初始化可扩展列表控件数据适配器*/
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private String[] querycontent={"磁性查询","放射性查询","电性查询","密度查询"};
        private String[][] ee={
                {"a","b"},
                {"a","b"},
                {"a","b"},
                {"a","b"}
        };
        private int[] vv={R.layout.view_1,R.layout.view_1,R.layout.view_1,R.layout.view_1};
        @Override
        public int getGroupCount() {
            return querycontent.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
           // return ee[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return querycontent[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
           // return null;
            return vv[groupPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       /* LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setOrientation(0);*/

            TextView textView = new TextView(SerchActivity.this);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(16);
            textView.setText(getGroup(groupPosition).toString());


            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            TextView textView = new TextView(SerchActivity.this);
            textView.setTextColor(Color.BLACK);
            View views=new View( SerchActivity.this);
           /* views.set

            return views[groupPosition];*/
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
