package devlight.io.sample;


import android.app.Activity;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.github.jorgecastillo.FillableLoader;


public class PageLemon extends Fragment {
    private CountDownTimer mTimer;
    private TextView textView;
    private Button button;
    int t;
    TextView show;
    final String[] items = new String[]{"30分钟", "60分钟", "90分钟", "120分钟",};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_lemon, container, false);
//        String svg = "M 108.54,26.04\n" +
//                "C 131.59,52.94 125.04,97.93 93.00,114.74\n" +
//                " 79.15,122.00 75.25,122.17 60.00,122.00\n" +
//                " 22.32,121.56 -2.52,80.38 8.75,46.00\n" +
//                " 16.01,23.85 33.60,10.58 56.00,6.44\n" +
//                " 75.35,3.96 95.68,11.04 108.54,26.04 Z\n" +
//                "M 56.97,45.15\n" +
//                "C 52.64,43.32 36.54,44.00 31.00,44.00\n" +
//                " 31.00,44.00 31.00,76.00 31.00,76.00\n" +
//                " 31.02,78.13 30.74,81.51 32.60,82.98\n" +
//                " 34.49,84.47 42.35,84.00 45.00,84.00\n" +
//                " 49.96,83.99 56.82,83.94 60.73,80.43\n" +
//                " 66.36,75.36 66.06,66.13 56.97,63.00\n" +
//                " 56.97,63.00 56.97,61.00 56.97,61.00\n" +
//                " 65.30,56.20 63.38,47.91 56.97,45.15 Z\n" +
//                "M 75.00,47.00\n" +
//                "C 75.00,47.00 75.00,49.00 75.00,49.00\n" +
//                " 75.00,49.00 90.00,49.00 90.00,49.00\n" +
//                " 90.00,49.00 90.00,47.00 90.00,47.00\n" +
//                " 90.00,47.00 75.00,47.00 75.00,47.00 Z\n" +
//                "M 53.69,50.02\n" +
//                "C 56.68,52.02 56.68,57.83 53.69,59.83\n" +
//                " 51.13,61.53 42.36,61.00 39.00,61.00\n" +
//                " 39.00,61.00 39.00,49.00 39.00,49.00\n" +
//                " 42.16,49.00 51.36,48.47 53.69,50.02 Z\n" +
//                "M 90.00,71.00\n" +
//                "C 101.61,70.78 95.70,60.78 91.70,57.84\n" +
//                " 81.22,50.14 65.06,59.66 68.95,74.00\n" +
//                " 72.11,85.62 91.42,89.31 96.00,75.95\n" +
//                " 89.20,76.54 82.26,84.08 76.93,75.95\n" +
//                " 76.03,74.51 75.51,72.57 75.00,71.00\n" +
//                " 75.00,71.00 90.00,71.00 90.00,71.00 Z\n" +
//                "M 91.00,67.00\n" +
//                "C 91.00,67.00 75.00,67.00 75.00,67.00\n" +
//                " 77.80,56.34 89.44,55.99 91.00,67.00 Z\n" +
//                "M 48.00,65.14\n" +
//                "C 49.46,65.02 51.56,65.02 52.95,65.14\n" +
//                " 57.94,66.90 59.11,73.01 55.57,76.57\n" +
//                " 52.93,79.23 48.51,79.13 45.00,78.98\n" +
//                " 42.86,78.89 40.68,78.89 39.60,76.69\n" +
//                " 38.72,74.89 39.00,67.39 39.00,65.14\n" +
//                " 39.00,65.14 48.00,65.14 48.00,65.14 Z";
//        FillableLoader fillableLoader = (FillableLoader) getView().findViewById(R.id.fillableLoader);
//
//        //将生成svg作为字符串传参进来
//        fillableLoader.setSvgPath(svg);
//        fillableLoader.start();

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

