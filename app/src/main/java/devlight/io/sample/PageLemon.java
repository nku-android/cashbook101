package devlight.io.sample;


import android.app.Activity;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import devlight.io.sample.R;


public class PageLemon extends Fragment {
    private CountDownTimer mTimer;
    private TextView textView;
    private Button button;
    int t;
    TextView show;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);
        textView = view.findViewById(R.id.time);
        show = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer == null) {
                    mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            textView.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒" + "  加油！坚持！");
                        }

                        @Override
                        public void onFinish() {
                            textView.setText("任务完成，很棒哦！");
                        }
                    }.start();
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("dddd","ddgggg");
                // TODO Auto-generated method stub
                new AlertDialog.Builder(getActivity())
                        .setTitle("时长选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.setText(items[which]);
                        t = Integer.parseInt(items[which].substring(0, 2));

                    }
                }).show();
            }
        });
        return view;
    }
}

