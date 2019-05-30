package devlight.io.sample;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.FillableLoaderBuilder;
import com.github.jorgecastillo.clippingtransforms.PlainClippingTransform;
import com.yhongm.wave_progress_view.WaveProgressView;

import java.nio.file.Paths;


public class PageLemon extends Fragment {
    private CountDownTimer mTimer;
    int t;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};
    private waveview waveProgressView_0, waveProgressView_1, waveProgressView_2;
    private Button start;
    private TextView time;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);

        waveProgressView_0 = (waveview) view.findViewById(R.id.wpv_0);
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

