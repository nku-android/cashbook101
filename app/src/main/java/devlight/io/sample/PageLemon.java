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
//    waveview waveProgresView;
    private CountDownTimer mTimer;
//    private TextView textView;
//    private Button button;
    int t;
//    TextView show;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};
private waveview waveProgressView_0, waveProgressView_1, waveProgressView_2;
Button start;
TextView show;
TextView time;
//    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);


        waveProgressView_0 = (waveview) view.findViewById(R.id.wpv_0);
        show=(TextView)view.findViewById(R.id.show);
        time=(TextView)view.findViewById(R.id.time);
//        waveProgressView_1 = (waveview) view.findViewById(R.id.wpv_1);
//        waveProgressView_2 = (waveview) view.findViewById(R.id.wpv_2);
        start=(Button)view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(v);
            }
        });
//        waveProgressView_0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//        });
//        waveProgresView = (waveview) view.findViewById(R.id.wave_progress_view);
//       button = (Button) view.findViewById(R.id.btn_test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                waveProgresView.setProgress(100);
//            }
//        } );
//
//       textView = view.findViewById(R.id.time);
//        show = view.findViewById(R.id.textView);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//              //  waveProgresView.setProgress(0.5f);
//                if (mTimer == null) {
//
//                    mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            // TODO Auto-generated method stub
//                            textView.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒" + "  加油！坚持！");
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            textView.setText("任务完成，很棒哦！");
//                        }
//                    }.start();
//                }
//            }
//        });

            time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("dddd","ddgggg");
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
        ObjectAnimator objectAnimator0 = ObjectAnimator.ofFloat(waveProgressView_0, "progress", 0f, 100f);
                if (mTimer == null) {

                    mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            show.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒" + "  加油！坚持！");

                        }

                        @Override
                        public void onFinish() {
                            show.setText("任务完成，很棒哦！");
                        }
                    }.start();
                }

        objectAnimator0.setDuration(t*1000);
        objectAnimator0.setInterpolator(new LinearInterpolator());
        objectAnimator0.start();

//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(waveProgressView_1, "progress", 0f, 100f);
//        objectAnimator1.setDuration(3300);
//        objectAnimator1.setInterpolator(new AccelerateInterpolator());
//        objectAnimator1.start();
//
//        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(waveProgressView_2, "progress", 0f, 120f);
//        objectAnimator2.setDuration(5000);
//        objectAnimator2.setInterpolator(new BounceInterpolator());
//        objectAnimator2.start();
    }
}

