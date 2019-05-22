package devlight.io.sample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.Logger;

public class ListAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public ListAdapter(Context context,List<String> data){
        this.context=context;
        this.data=data;
    }

    /*  由系统调用，获取一个View对象，作为ListView的条目，屏幕上能显示多少个条目，getView方法就会被调用多少次
     *  position：代表该条目在整个ListView中所处的位置，从0开始
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView text;
        if (convertView == null) {
            //若没有缓存布局，则加载
            //首先获取布局填充器，然后使用布局填充器填充布局文件
            convertView=LayoutInflater.from(context).inflate(R.layout.main_todolist,parent,false);

            //存储子项布局中子控件对象
            text = (TextView) convertView.findViewById(R.id.list_item);

            // 将内部类对象存储到View对象中
            convertView.setTag(text);

        } else {
            //若有缓存布局，则直接用缓存（利用的是缓存的布局，利用的不是缓存布局中的数据）
            convertView = (TextView) convertView.getTag();
            text = (TextView) convertView.findViewById(R.id.list_item);
        }

        Log.i("当前是data",data.get(position));
        text.setText(data.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
