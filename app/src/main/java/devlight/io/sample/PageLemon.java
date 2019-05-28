package devlight.io.sample;


import android.content.DialogInterface;
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
    WaveProgressView waveProgresView;
    private CountDownTimer mTimer;
    private TextView textView;
    private Button button;
    int t;
    TextView show;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};

//    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);

        waveProgresView = (WaveProgressView) view.findViewById(R.id.wave_progress_view);
       button = (Button) view.findViewById(R.id.btn_test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                waveProgresView.setProgress(100);
//            }
//        } );
//
       textView = view.findViewById(R.id.time);
        show = view.findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (mTimer == null) {

                    mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            textView.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒" + "  加油！坚持！");
                            waveProgresView.setProgress(0.1f);
//                            for (int i=1;i<=t;i++)
//                            {

//                            }
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


//        FillableLoader fillableLoader = (FillableLoader) view.findViewById(R.id.fillableLoader)
//        String svg = "M693.403-9.263l-376.691 4.628c-93.005 1.142-141.47 64.931-141.47 121.987v760.555c0 70.909 48.497 147.571 146.098 145.113l367.435-9.257c88.235-2.223 160.61-37.038 160.001-135.856l-4.628-751.298c-0.55-89.394-34.658-137.298-150.745-135.872zM777.275 882.536c0 38.027-35.535 68.978-79.226 68.978h-372.081c-43.673 0-79.207-30.951-79.207-68.978v-755.926h0.018c0-38.043 35.535-68.978 79.207-68.978h372.063c43.673 0 79.226 30.937 79.226 68.978zM737.025 124.46h-450.014c-5.407 0-9.776 3.851-9.776 8.617v669.925c0 4.749 4.369 8.633 9.776 8.633h450.014c5.407 0 9.794-3.867 9.794-8.633v-669.925c0-4.766-4.387-8.617-9.794-8.617zM704.108 766.6l-379.551-4.628-4.628-592.51 379.551 4.628zM512.018 825.994c-32.969 0-62.476 23.634-62.476 52.691 0 29.058 33.092 58.667 72.035 51.497 32.423-5.97 56.768-24.541 56.768-53.583 0-29.058-33.359-50.606-66.327-50.606zM512.018 914.128c-22.191 0-40.231-15.901-40.231-35.443s18.041-35.459 40.231-35.459c22.173 0 50.361 14.874 50.361 34.416 0 19.542-28.188 36.486-50.361 36.486zM447.319 90.589c0-3.515 3.895-6.353 8.702-6.353h111.975c4.805 0 8.684 2.841 8.684 6.353 0 3.514-3.877 13.472-8.684 13.523l-111.975 1.195c-4.788 0.051-8.684-11.205-8.702-14.719z";
//
//        fillableLoader.setSvgPath(svg);
//        fillableLoader.setOriginalDimensions(100,100);
//
//        fillableLoader.start();
//

//        return view;
//    }





//    private void test() {
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                percent = 0;
//                while (percent <= 1) {
//                    mSinkingView.setPercent(percent);
//                    percent += 0.01f;
//                    try {
//                        Thread.sleep(40);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                percent = 0.56f;
//                mSinkingView.setPercent(percent);
//                // mSinkingView.clear();
//            }
//        });
//        thread.start();
//    }
}

