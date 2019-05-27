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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;

import devlight.io.sample.components.MySQLiteOpenHelper;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class addtask extends Activity {
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    private TextView data;

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


//        item = (EditText) findViewById(R.id.item);
//        item.setOnKeyListener(new EditText.OnKeyListener(){
//            @Override
//            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
//                // TODO Auto-generated method stub
//                item.setText(item.getText());//获取edittext的内容
//                return false;
//            }
//        });

        item=(EditText)findViewById(R.id.item);
        content=(EditText)findViewById(R.id.content);
        spinner = (Spinner) findViewById(R.id.spinner);
        //数据
        data_list = new ArrayList<String>();
        data_list.add("无");
        data_list.add("高");
        data_list.add("中");
        data_list.add("低");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        //textview4 = (TextView)findViewById(R.id.save_where);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
               str = (String) spinner.getItemAtPosition(arg2);
               // textview4.setText("您所在的城市是：" + str);//文本说明
            }

            @Override
           public void onNothingSelected(AdapterView<?> parent) {
                //textview4.setText("未选择");
            }
        });

        data=(TextView)findViewById(R.id.data) ;
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("dddd","ddgggg");
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
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(addtask.this);
                alterDialog.setTitle("保存");
                alterDialog.setMessage("是否设置该任务");
                alterDialog.setCancelable(false);
                alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayDatabaseInfo();
                        Toast.makeText(addtask.this, "设置成功", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(addtask.this, PageTodolist.class);
//                        startActivity(intent);
                    }
                });
                alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(addtask.this, "取消", Toast.LENGTH_SHORT).show();
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
        data.setText(new StringBuffer().append("时间：").append(mYear).append("-").append(mMonth+1).append("-").append(mDay).append(" "));
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

//        loopView.setItems(options1Items);
//        loopView.setInitPosition(5);//初始化，设置默认选项
//        loopView.setListener(new OnItemSelectedListener() {//设置监听
//            @Override
//            public void onItemSelected(int index) {
//                Toast.makeText(addtask.this, options1Items.get(index), Toast.LENGTH_SHORT).show();
//            }
//        });

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
             //   Toast.makeText(addtask.this, str, Toast.LENGTH_SHORT).show();
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

        dbHelper = MySQLiteOpenHelper.getInstance(addtask.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title",item.getText().toString());
        cv.put("content",content.getText().toString());
        cv.put("clock",clocktext.getText().toString());
        cv.put("importance",str);
        cv.put("alert_time",data.getText().toString());

        db.insert("tb_todo",null,cv);

        String sql = "SELECT * FROM tb_todo";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        TextView num=(TextView)findViewById(R.id.num);
        num.setText("目前有:"+cursor.getCount()+"个任务");

//        while (cursor.moveToNext()) {
//            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
//            DatabaseUtils.cursorIntToContentValues(cursor, "alert_time", cv);
//        }
    }

}
