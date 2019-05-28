package devlight.io.sample;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
        ViewGroup.LayoutParams params = new ViewPager.LayoutParams();

        FillableLoader fillableLoader = (FillableLoader) view.findViewById(R.id.fillableLoader);
//        String svg = "M 117.029,490.057 \n"+
//                "L 146.286,490.057 \n"+
//                "L 146.286,519.314 \n"+
//                "L 117.029,519.314 Z \n"+
//                "M 804.571,490.057 \n"+
//                "L833.829,490.057 \n"+
//                "L 833.829,519.314 \n"+
//                "L 804.572,519.314 Z \n"+
//                "M 204.8,533.943 \n"+
//                "L 234.057,533.943 \n"+
//                "L 234.057,563.2 \n"+
//                "L 204.8,563.2 Z \n"+
//                "M 234.057,592.457 \n"+
//                "L 263.314,592.457 \n"+
//                "L 263.314,621.714 \n"+
//                "L 234.057,621.714 Z \n"+
//                "M 277.943,636.343 \n"+
//                "L 307.2,636.343 \n"+
//                "L 307.2,665.6 \n"+
//                "L 277.943,665.6 Z \n"+
//                "M 321.829,680.229 \n"+
//                "L 351.086,680.229 \n"+
//                "L 351.086,709.486 \n"+
//                "L 321.829,709.486 Z \n"+
//                "M 380.343,709.486 \n"+
//                "L 409.6,709.486 \n"+
//                "L 409.6,738.743 \n"+
//                "L 380.343,738.743 Z \n"+
//                "M 702.171,870.4 \n"+
//                "C 548.571,870.4 424.229,746.057 424.229,592.457 \n"+
//                "L 424.229,577.828 \n"+
//                "L 980.115,577.828 \n"+
//                "L 980.115,592.457 \n"+
//                "C 980.115,746.057 855.772,870.4 702.172,870.4 Z \n"+
//                "M 453.486,607.086 \n"+
//                "C 460.8,737.28 569.052,841.143 702.172,841.143 \n"+
//                "C 833.829,841.143 943.543,737.28 950.858,607.086 \n"+
//                "L 453.486,607.086 Z ";

        String svg = "M 108.54,26.04\n" +
                "C 131.59,52.94 125.04,97.93 93.00,114.74\n" +
                " 79.15,122.00 75.25,122.17 60.00,122.00\n" +
                " 22.32,121.56 -2.52,80.38 8.75,46.00\n" +
                " 16.01,23.85 33.60,10.58 56.00,6.44\n" +
                " 75.35,3.96 95.68,11.04 108.54,26.04 Z\n" +
                "M 56.97,45.15\n" +
                "C 52.64,43.32 36.54,44.00 31.00,44.00\n" +
                " 31.00,44.00 31.00,76.00 31.00,76.00\n" +
                " 31.02,78.13 30.74,81.51 32.60,82.98\n" +
                " 34.49,84.47 42.35,84.00 45.00,84.00\n" +
                " 49.96,83.99 56.82,83.94 60.73,80.43\n" +
                " 66.36,75.36 66.06,66.13 56.97,63.00\n" +
                " 56.97,63.00 56.97,61.00 56.97,61.00\n" +
                " 65.30,56.20 63.38,47.91 56.97,45.15 Z\n" +
                "M 75.00,47.00\n" +
                "C 75.00,47.00 75.00,49.00 75.00,49.00\n" +
                " 75.00,49.00 90.00,49.00 90.00,49.00\n" +
                " 90.00,49.00 90.00,47.00 90.00,47.00\n" +
                " 90.00,47.00 75.00,47.00 75.00,47.00 Z\n" +
                "M 53.69,50.02\n" +
                "C 56.68,52.02 56.68,57.83 53.69,59.83\n" +
                " 51.13,61.53 42.36,61.00 39.00,61.00\n" +
                " 39.00,61.00 39.00,49.00 39.00,49.00\n" +
                " 42.16,49.00 51.36,48.47 53.69,50.02 Z\n" +
                "M 90.00,71.00\n" +
                "C 101.61,70.78 95.70,60.78 91.70,57.84\n" +
                " 81.22,50.14 65.06,59.66 68.95,74.00\n" +
                " 72.11,85.62 91.42,89.31 96.00,75.95\n" +
                " 89.20,76.54 82.26,84.08 76.93,75.95\n" +
                " 76.03,74.51 75.51,72.57 75.00,71.00\n" +
                " 75.00,71.00 90.00,71.00 90.00,71.00 Z\n" +
                "M 91.00,67.00\n" +
                "C 91.00,67.00 75.00,67.00 75.00,67.00\n" +
                " 77.80,56.34 89.44,55.99 91.00,67.00 Z\n" +
                "M 48.00,65.14\n" +
                "C 49.46,65.02 51.56,65.02 52.95,65.14\n" +
                " 57.94,66.90 59.11,73.01 55.57,76.57\n" +
                " 52.93,79.23 48.51,79.13 45.00,78.98\n" +
                " 42.86,78.89 40.68,78.89 39.60,76.69\n" +
                " 38.72,74.89 39.00,67.39 39.00,65.14\n" +
                " 39.00,65.14 48.00,65.14 48.00,65.14 Z";

//        String svg = "M 505.221,27.034 \n"+
//                "C 505.013,27.034 504.768,27.034 504.522,27.034 \n"+
//                " 234.386,27.034 15.398,246.022 15.398,516.158 \n"+
//                " 15.398,786.294 234.386,1005.28 504.522,1005.28 \n"+
//                " 504.768,1005.28 505.014,1005.28 505.26,1005.28 \n"+
//                " 505.43,1005.28 505.675,1005.28 505.921,1005.28 \n"+
//                " 776.057,1005.28 995.045,786.292 995.045,516.156 \n"+
//                " 995.045,246.02 776.057,27.032 505.921,27.032 \n"+
//                " 505.675,27.032 505.429,27.032 505.183,27.033 Z \n"+
//                "M 881.48,537.994 \n" +
//                "L 549.679,537.994 \n"+
//                "L 782.829,771.144 \n"+
//                "C 839.947,708.982 876.33,627.774 881.481,537.995 Z \n"+
//                "M 881.823,501.166 \n"+
//                "C 878.249,410.404 842.762,328.038 786.083,264.76 \n"+
//                "L 549.678,501.166 \n"+
//                "L 881.823,501.166 Z \n"+
//                "M 486.81,475.122 \n"+
//                "L 486.81,139.731 \n"+
//                "C 395.674,144.139 313.185,180.711 250.24,238.557 \n"+
//                "L 486.81,475.122 Z \n"+
//                "M 523.638,475.126 \n" +
//                "L 760.208,238.556 \n"+
//                "C 697.262,180.705 614.768,144.138 523.638,139.73 \n"+
//                "L 523.638,475.126 Z \n"+
//                "M 460.769,501.166 \n"+
//                "L 224.36,264.76 \n"+
//                "C 167.676,328.038 132.19,410.399 128.615,501.166 \n"+
//                "L 460.77,501.166 Z \n"+
//                "M 486.809,564.035 \n"+
//                "L 253.825,797.02 \n"+
//                "C 316.309,852.992 397.395,888.264 486.811,892.59 \n"+
//                "L 486.811,564.036 Z \n"+
//                "M 460.769,537.995 \n"+
//                "L 128.968,537.995 \n"+
//                "C 134.118,627.778 170.496,708.987 227.62,771.144 \n"+
//                "L 460.77,537.994 Z";
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

