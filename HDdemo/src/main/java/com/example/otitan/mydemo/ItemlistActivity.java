package com.example.otitan.mydemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otitan.mydemo.Adapter.ListviewAdapter;

import java.util.ArrayList;

public class ItemlistActivity extends ActionBarActivity {
    private ListView lv;
    private ListviewAdapter lAdapter;
    private ArrayList<String> list;

    private Button bt_cancel;

    private int checkNum; // 记录选中的条目数量
    private TextView tv_show;// 用于显示选中的条目数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);
        lv = (ListView) findViewById(R.id.lv);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        tv_show = (TextView) findViewById(R.id.tv);
        list = new ArrayList<String>();
        // 为Adapter准备数据
        initDate();
        // 实例化自定义的MyAdapter
        lAdapter = new ListviewAdapter(list, this);
        // 绑定Adapter
        lv.setAdapter(lAdapter);
        // 绑定listView的监听器
        // lv.setItemChecked(position, value)
        // 取消按钮的回调接口
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的按钮设为未选
                for (int i = 0; i < list.size(); i++) {
                    if (lAdapter.getIsSelected().get(i)) {
                        lAdapter.getIsSelected().put(i, false);
                        checkNum--;// 数量减1
                    }
                }
                // 刷新listview和TextView的显示
                // dataChanged();

            }
        });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                ListviewAdapter.ViewHolder holder = (ListviewAdapter.ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.cb.toggle();
                // 将CheckBox的选中状况记录下来
                lAdapter.getIsSelected().put(arg2, holder.cb.isChecked());
                // 调整选定条目
                if (holder.cb.isChecked() == true) {

                    checkNum++;
                } else {
                    checkNum--;
                }
                // 用TextView显示
                tv_show.setText("已选中" + checkNum + "项");

            }
        });

    }

    // 初始化数据
    private void initDate() {
        for (int i = 0; i < 15; i++) {
            list.add("data" + "   " + i);
        }
    }


}