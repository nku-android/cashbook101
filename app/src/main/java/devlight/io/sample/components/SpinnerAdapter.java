package devlight.io.sample.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

import devlight.io.sample.R;


public class SpinnerAdapter extends BaseAdapter {

    private Context ctx;
    private List<itemHolder> data;
    private Spinner spinner;

    public SpinnerAdapter(Context ctx, List<itemHolder> data, Spinner spinner){
        this.ctx = ctx;
        this.data = data;
        this.spinner = spinner;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public itemHolder getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        itemHolder data = getItem(position);

        TextView text = convertView.findViewById(R.id.important_text);
        text.setText(data.text);
        ImageView img = convertView.findViewById(R.id.important_img);
        img.setImageResource(data.img);

//        viewHolder.text = (TextView) convertView.findViewById(R.id.important_text).toString();
//        viewHolder.time = (TextView) convertView.findViewById(R.id.list_item_time);
//        viewHolder.btn = (ImageButton) convertView.findViewById(R.id.list_item_btn);
//        viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete_btn);

        return convertView;
    }

    public final static class itemHolder{
        String text;
        int img;
    }
}
