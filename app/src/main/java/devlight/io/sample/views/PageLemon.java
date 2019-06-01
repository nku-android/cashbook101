package devlight.io.sample.views;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import devlight.io.sample.R;


public class PageLemon extends Fragment {
    private CountDownTimer mTimer;
    int t;
    final String[] items = new String[]{"10分钟","30分钟", "60分钟", "90分钟", "120分钟"};
    private WaveView waveProgressView_0, waveProgressView_1, waveProgressView_2;
    private Button start;
    private TextView time;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);
        Resources resources = getContext().getResources();
        Drawable btnDrawable = resources.getDrawable(R.drawable.background);
        view.setBackground(btnDrawable);


        waveProgressView_0 = (WaveView) view.findViewById(R.id.wpv_0);
        time=(TextView)view.findViewById(R.id.time);
        start=(Button)view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(v);
            }
        });
            time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(getActivity())
                        .setTitle("时长选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        time.setText(items[which]);
                        t = Integer.parseInt(items[which].substring(0, 2));
                    }
                }).show();
            }
        });

        return view;
    }

    public void start(View view) {
        //ObjectAnimator objectAnimator0 = ObjectAnimator.ofFloat(waveProgressView_0, "progress", 0f, 100f);
        if (mTimer == null) {

            mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub
                  time.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒");
                  waveProgressView_0.setProgress((float)(Math.round(t*60*1000-millisUntilFinished)/(t*60.0*1000.0)*100));
                }

                @Override
                public void onFinish() {
                    time.setText("任务完成，很棒哦！");
                }
            }.start();
        }


//        objectAnimator0.setDuration(t * 1000);
//        objectAnimator0.setInterpolator(new LinearInterpolator());
//        objectAnimator0.start();

    }
}

