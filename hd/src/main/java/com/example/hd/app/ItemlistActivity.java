package com.example.hd.app;

import android.os.Bundle;
import android.renderscript.Element;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.FileUpload;
import com.hd.rock.dao.Rock;
import com.hd.rock.dao.rockdao;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Adapter.ListviewAdapter;

public class ItemlistActivity extends ActionBarActivity {
    private ListView lv;
    private ListviewAdapter lAdapter;
    private ArrayList<String> list;
    private List<Rock> rocks = null;
    private Button bt_cancel, bt_upload;
    private List<Rock> uprocks;
    private int checkNum; // 记录选中的条目数量
    private TextView tv_show;// 用于显示选中的条目数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);
        lv = (ListView) findViewById(R.id.lv);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_upload = (Button) findViewById(R.id.bt_upload);
        tv_show = (TextView) findViewById(R.id.tv);
        rockdao dao = new rockdao(this);
        rocks = dao.FindAll();
        list = new ArrayList<String>();
        // 为Adapter准备数据
        initDate();
        // 实例化自定义的MyAdapter
        //lAdapter = new ListviewAdapter(list, this);
        lAdapter = new ListviewAdapter(rocks, this);
        // 绑定Adapter
        lv.setAdapter(lAdapter);
        // 绑定listView的监听器
        // lv.setItemChecked(position, value)
        // 取消按钮的回调接口
        /*bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 遍历list的长度，将已选的按钮设为未选
                for (int i = 0; i < rocks.size(); i++) {
                    if (lAdapter.getIsSelected().get(i)) {
                        lAdapter.getIsSelected().put(i, false);
                        checkNum--;// 数量减1
                    }
                }
                //刷新listview和TextView的显示
                dataChanged();

            }
        });*/
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uprocks = GetRocks();
                if (uprocks != null) {
                    Document doc = WriteXML();
                    doc.getBaseURI();
                    File file = new File();

                    String url = "http://192.168.0.101:8080/RockServers/RockServlet";
                    FileUpload fu = new FileUpload();
                    try {
                        fu.UpLoad(url, file, ItemlistActivity.this);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(ItemlistActivity.this, "请选择要上传的数据", Toast.LENGTH_SHORT).show();
                }

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

    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        lAdapter.notifyDataSetChanged();
        // TextView显示最新的选中数目
        tv_show.setText("已选中" + checkNum + "项");
    }

    private List<Rock> GetRocks() {
        List<Rock> uprocks = new ArrayList<Rock>();
        for (int i = 0; i < rocks.size(); i++) {
            if (lAdapter.getIsSelected().get(i)) {
                uprocks.add(rocks.get(i));
            } else {
                Toast.makeText(ItemlistActivity.this, "请选择要上传的数据", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return uprocks;
    }

    private Document WriteXML() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
            System.exit(1);
        }
        Document doc = db.newDocument();
        Element root = (Element) doc.createElement("Rocks");
        doc.appendChild((Node) root);
        for (int i = 0; i < uprocks.size(); i++) {
            Rock rock = uprocks.get(i);
            Element Rock = (Element) doc.createElement("Rock");
            ((Node) root).appendChild((Node) Rock);
            Element time = (Element) doc.createElement("时间");
            ((Node) Rock).appendChild((Node) time);
            Text tTime = doc.createTextNode(rock.getTime());
            ((Node) time).appendChild(tTime);
            Element longitude = (Element) doc.createElement("经度");
            ((Node) Rock).appendChild((Node) longitude);
            Text tLongitude = doc.createTextNode(rock.getLongitude());
            ((Node) longitude).appendChild(tLongitude);
            Element latitude = (Element) doc.createElement("纬度");
            ((Node) Rock).appendChild((Node) latitude);
            Text tLatitude = doc.createTextNode(rock.getLatitude());
            ((Node) latitude).appendChild(tLatitude);


        }
        return doc;
    }


}