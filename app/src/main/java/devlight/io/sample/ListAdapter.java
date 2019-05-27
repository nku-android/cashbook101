package devlight.io.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;



public class ListAdapter extends ArrayAdapter<ListAdapter.itemHolder> implements View.OnClickListener , PinnedSectionListView.PinnedSectionListAdapter {

    private int resourceId;
    private ListView listView;
    private Animation animation;
    private boolean isScrollDown;
    private int mFirstTop, mFirstPosition;
    private InnerItemOnclickListener mListener;
    private List<itemHolder> mitemList;

    @SuppressLint("ResourceType")
    public ListAdapter(Context context, int textViewResourceId, List<itemHolder> objects, ListView mlistView){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        listView = mlistView;
        animation = AnimationUtils.loadAnimation(context,R.animator.item_list_in_anim);
        mitemList = objects;

        AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstChild = view.getChildAt(0);
                if (firstChild == null) return;
                int top = firstChild.getTop();
                /**
                 * firstVisibleItem > mFirstPosition表示向下滑动一整个Item
                 * mFirstTop > top表示在当前这个item中滑动
                 */
                isScrollDown = firstVisibleItem > mFirstPosition || mFirstTop > top;
                mFirstTop = top;
                mFirstPosition = firstVisibleItem;
            }
        };

        listView.setOnScrollListener(mOnScrollListener);
    }

    /*  由系统调用，获取一个View对象，作为ListView的条目，屏幕上能显示多少个条目，getView方法就会被调用多少次
     *  position：代表该条目在整个ListView中所处的位置，从0开始
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        itemHolder data = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            //若没有缓存布局，则加载
            //首先获取布局填充器，然后使用布局填充器填充布局文件
            convertView=LayoutInflater.from(getContext()).inflate(resourceId,null);

            //存储子项布局中子控件对象
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.list_item_text);
            viewHolder.time = (TextView) convertView.findViewById(R.id.list_item_time);
            viewHolder.btn = (ImageButton) convertView.findViewById(R.id.list_item_btn);


            // 将内部类对象存储到View对象中
            convertView.setTag(viewHolder);

        } else {
            //若有缓存布局，则直接用缓存（利用的是缓存的布局，利用的不是缓存布局中的数据）
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //清除当前显示区域中所有item的动画
        for (int i=0;i<listView.getChildCount();i++){
            View view = listView.getChildAt(i);
            view.clearAnimation();
        }

        //然后给当前item添加上动画
        if (isScrollDown) {
            convertView.startAnimation(animation);
        }


        viewHolder.text.setText(data.text);

        if (data.type == 0) {
            viewHolder.btn = null;
            convertView.setBackgroundColor(Color.parseColor("#ddeef8"));
            viewHolder.text.setBackgroundColor(Color.parseColor("#ddeef8"));
            viewHolder.time.setBackgroundColor(Color.parseColor("#ddeef8"));
            viewHolder.text.setTextColor(Color.parseColor("#708090"));
        }
        if (data.type == 1) {
            viewHolder.btn.setImageResource(R.drawable.select_button_on);
            viewHolder.btn.setTag(position);
            viewHolder.btn.setOnClickListener(this);
            viewHolder.time.setText(data.time);
        }
        if (data.type == 2) {
            viewHolder.btn.setImageResource(R.drawable.select_button_off);
            viewHolder.btn.setTag(position);
            viewHolder.btn.setOnClickListener(this);
            viewHolder.time.setText(data.time);
        }




        return convertView;
    }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }


    public final static class ViewHolder {
        ImageButton btn;
        TextView text;
        TextView time;
    }

    public final static class itemHolder{
        String text;
        String time;
        int type;
        int id;
    }

    // We implement this method to return 'true' for all view types we want to pin
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (mitemList.get(position).type == 0)
            return 1;
        else
            return 0;
    }
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        if (viewType == 1)
                return true;
        else
            return false;
    }


}
