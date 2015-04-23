package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hd.app.R;
import com.hd.rock.dao.Rock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListviewAdapter extends BaseAdapter {
    // 填充数据的list
    private ArrayList<String> list;
    //数据
    private List<Rock> rocks;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    // 上下文
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;

    // 构造器
    public ListviewAdapter(ArrayList<String> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initData();
    }

    public ListviewAdapter(List<Rock> rocks, Context context) {
        this.context = context;
        this.rocks = rocks;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initData();
    }


    private void initData() {
        for (int i = 0; i < rocks.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rocks.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return rocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.listview, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }


        // 设置list中TextView的显示
        //Rock rock=rocks.get(position);
        holder.tv.setText(rocks.get(position).getTime().toString() + "岩石样本");
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ListviewAdapter.isSelected = isSelected;
    }

    public final class ViewHolder {
        public TextView tv;
        public CheckBox cb;

    }

}
