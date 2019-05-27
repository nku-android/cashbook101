package devlight.io.sample;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
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

import java.nio.file.Paths;


public class PageLemon extends Fragment {
    private CountDownTimer mTimer;
    private TextView textView;
    private Button button;
    int t;
    TextView show;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);
        FillableLoader fillableLoader = (FillableLoader) view.findViewById(R.id.fillableLoader);
        String svg = "M505.22,27.03c-0.21,0 -0.45,0 -0.7,0 -270.14,0 -489.12,218.99 -489.12,489.12 0,270.14 218.99,489.12 489.12,489.12 0.25,0 0.49,0 0.74,-0 0.17,0 0.41,0 0.66,0 270.14,0 489.12,-218.99 489.12,-489.12 0,-270.14 -218.99,-489.12 -489.12,-489.12 -0.25,0 -0.49,0 -0.74,0z" ;

        fillableLoader.setSvgPath(svg);
        fillableLoader.setOriginalDimensions(100,100);

        fillableLoader.start();

//        textView = view.findViewById(R.id.time);
//        show = view.findViewById(R.id.textView);
//        button = view.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTimer == null) {
//                    mTimer = new CountDownTimer((long) (t * 60 * 1000), 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            // TODO Auto-generated method stub
//                            textView.setText("还剩" + millisUntilFinished / (60 * 1000) + "分钟" + (millisUntilFinished % (60 * 1000)) / 1000 + "秒" + "  加油！坚持！");
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
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Log.i("dddd","ddgggg");
//                // TODO Auto-generated method stub
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("时长选择").setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        show.setText(items[which]);
//                        t = Integer.parseInt(items[which].substring(0, 2));
//
//                    }
//                }).show();
//            }
//        });
        return view;
    }
}

