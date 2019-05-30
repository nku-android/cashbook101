package devlight.io.sample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.MacAddress;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devlight.io.sample.components.MySQLiteOpenHelper;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class EditTask extends Activity {
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    private ImageButton data;
    private TextView time_data;

    private RadioGroup clock;
    private RadioButton yes,no;
    private TextView clocktext;

    private EditText item;
    private EditText content;

    private Button save;

    private List<String> options1Items = new ArrayList<>();
    private List<String> hourItems = new ArrayList<>();
    private List<String> minItems = new ArrayList<>();

    private MySQLiteOpenHelper dbHelper;

    String str;

    // private OptionsPickerView pvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);



        item=(EditText)findViewById(R.id.item);
        content=(EditText)findViewById(R.id.content);
        spinner = (Spinner) findViewById(R.id.spinner);
        time_data = (TextView)findViewById(R.id.time_data);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("无");
        data_list.add("高");
        data_list.add("中");
        data_list.add("低");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                str = (String) spinner.getItemAtPosition(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        data=(ImageButton)findViewById(R.id.data) ;
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);


        clock=(RadioGroup)findViewById(R.id.clock);
        yes=(RadioButton)findViewById(R.id.yes);
        no=(RadioButton)findViewById(R.id.no);
        clocktext=(TextView)findViewById(R.id.clocktext);

        //给RadioGroup设置事件监听
        clock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(checkedId==no.getId()){
                    clocktext.setText("提醒时间：不需要提醒");
                }else if(checkedId==yes.getId()){
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPickerView(v);
                        }
                    });
                }
            }
        });
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(EditTask.this);
                alterDialog.setTitle("保存");
                alterDialog.setMessage("是否设置该任务");
                alterDialog.setCancelable(false);
                alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayDatabaseInfo();
                        Toast.makeText(EditTask.this, "设置成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditTask.this, HorizontalNtbActivity.class);
                        intent.putExtra("id",1);
                        startActivity(intent);
                    }
                });
                alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditTask.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                });
                alterDialog.show();

            }
        });

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        time_data.setText(new StringBuffer().append("时间: ").append(mYear).append("-").append(mMonth+1).append("-").append(mDay).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };



    private void initData() {
        options1Items.add("当天");
        options1Items.add("提前1天");
        options1Items.add("提前2天");
        options1Items.add("提前3天");
        options1Items.add("提前一周");

        for (int i = 0 ;i < 24 ; i ++){
            hourItems.add(""+i);
        }
        for (int i = 0 ;i < 60 ; i ++){
            minItems.add(""+i);
        }


    }


    public void showPickerView(View view) {
        initData();
        OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str =  options1Items.get(options1)+"的"
                        + hourItems.get(options2)+"时"
                        + minItems.get(options3)+"分";
                clocktext.setText("提醒时间为："+str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        //        Toast.makeText(addtask.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvNoLinkOptions.setNPicker(options1Items, hourItems, minItems);
        pvNoLinkOptions.setSelectOptions(0, 0, 0);
        pvNoLinkOptions.show();

    }

    private void displayDatabaseInfo() {


        dbHelper = MySQLiteOpenHelper.getInstance(EditTask.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title",item.getText().toString());
        cv.put("content",content.getText().toString());
        cv.put("clock",clocktext.getText().toString());
        cv.put("importance",str);
        String tmp = time_data.getText().toString();
        cv.put("alert_time",tmp.substring(tmp.indexOf(':')+1));

        db.insert("tb_todo",null,cv);

        String sql = "SELECT * FROM tb_todo";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        TextView num=(TextView)findViewById(R.id.num);
        num.setText("目前有:"+cursor.getCount()+"个任务");

    }



}
