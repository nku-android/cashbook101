package devlight.io.sample.views;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import devlight.io.sample.R;
import devlight.io.sample.components.MySQLiteOpenHelper;
import devlight.io.sample.components.TodoItem;
import devlight.io.sample.utils.AlarmUtils;
import devlight.io.sample.utils.MyDateUtils;

public class EditTask extends Activity {
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    private ImageButton data;
    private TextView time_data;

    private RadioGroup clock;
    private RadioButton yes, no;
    private TextView clockText;

    private EditText item;
    private EditText content;

    private Button save;

    private List<String> options1Items = new ArrayList<>();
    private List<String> hourItems = new ArrayList<>();
    private List<String> minItems = new ArrayList<>();
    private MySQLiteOpenHelper dbHelper;


    String str;
    private int id;

    // private OptionsPickerView pvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        dbHelper = MySQLiteOpenHelper.getInstance(EditTask.this);


        item = (EditText) findViewById(R.id.item);
        content = (EditText) findViewById(R.id.content);
        spinner = (Spinner) findViewById(R.id.spinner);
        clock = (RadioGroup) findViewById(R.id.clock);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        no.setChecked(true);
        clockText = (TextView) findViewById(R.id.clocktext);
        clockText.setText("提醒时间:不需要提醒");
        time_data = (TextView) findViewById(R.id.time_data);
        time_data.setText(MyDateUtils.formatDate("yyyy-MM-dd", MyDateUtils.getTodayTimestamp()));

        data = (ImageButton) findViewById(R.id.data);
        data.setOnClickListener(v -> showDialog(DATE_DIALOG));


        //数据
        data_list = Arrays.asList("无", "高", "中", "低");
        //适配器
        arr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //加载适配器
        spinner.setAdapter(arr_adapter);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);


        if (id != -1) {
            ContentValues cv = new ContentValues();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "SELECT * FROM tb_todo WHERE id==?";
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, new String[]{id + ""});

            while (cursor.moveToNext()) {
                DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
                DatabaseUtils.cursorStringToContentValues(cursor, "alert_time", cv);
                DatabaseUtils.cursorStringToContentValues(cursor, "content", cv);
                DatabaseUtils.cursorStringToContentValues(cursor, "clock", cv);
                DatabaseUtils.cursorStringToContentValues(cursor, "importance", cv);
            }

            String data_list_select = cv.getAsString("importance");
            switch (data_list_select) {
                case "高":
                    spinner.setSelection(1);
                    break;
                case "中":
                    spinner.setSelection(2);
                    break;
                case "低":
                    spinner.setSelection(3);
                    break;
                default:
                    spinner.setSelection(0);
            }

            item.setText(cv.getAsString("title"));
            content.setText(cv.getAsString("content"));
            time_data.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cv.getAsLong(TodoItem.ALERT_TIME)));

            clockText.setText("提醒时间:");
            long clock_time = cv.getAsLong(TodoItem.CLOCK);
            if (clock_time == -1) {
                no.setChecked(true);
                clockText.append("不需要提醒");
            } else {
                clockText.append(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(clock_time));
                yes.setChecked(true);
            }

        }


        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                str = (String) spinner.getItemAtPosition(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);


        clockText.setOnClickListener(this::showPickerView);

        //给RadioGroup设置事件监听
        clock.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == no.getId()) {
                clockText.setText("提醒时间:不需要提醒");
            } else if (checkedId == yes.getId()) {
                yes.setOnClickListener(this::showPickerView);
            }
        });
        save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            if (item.getText() == null || "".equals(item.getText().toString())) {
                Toast.makeText(this, "主题不能为空", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(EditTask.this);
                alterDialog.setTitle("保存");
                alterDialog.setMessage("是否保存修改");
                alterDialog.setCancelable(false);
                alterDialog.setPositiveButton("确定", (dialog, which) -> {
                    displayDatabaseInfo();
                    Toast.makeText(EditTask.this, "设置成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(EditTask.this, HorizontalNtbActivity.class);
                    startActivity(intent1);
                });
                alterDialog.setNegativeButton("取消", (dialog, which) -> {
                    Toast.makeText(EditTask.this, "取消", Toast.LENGTH_SHORT).show();
                });
                alterDialog.show();
            }
        });

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateListener, mYear, mMonth, mDay);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            time_data.setText(MyDateUtils.formatDate("yyyy-MM-dd", year, monthOfYear, dayOfMonth));
//            display();
        }
    };


    private void initOptionSelectorData() {
        options1Items.add("当天");
        options1Items.add("提前1天");
        options1Items.add("提前2天");
        options1Items.add("提前3天");
        options1Items.add("提前一周");

        for (int i = 0; i < 24; i++) {
            hourItems.add("" + i);
        }
        for (int i = 0; i < 60; i++) {
            minItems.add("" + i);
        }


    }


    public void showPickerView(View view) {
        initOptionSelectorData();
        OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                long clock_time = MyDateUtils.parseDate("yyyy-MM-dd", time_data.getText().toString());

                switch (options1Items.get(options1)) {
                    case "当天":
                        break;
                    case "提前1天":
                        clock_time -= MyDateUtils.MillisInDay;
                        break;
                    case "提前2天":
                        clock_time -= 2 * MyDateUtils.MillisInDay;
                        break;
                    case "提前3天":
                        clock_time -= 3 * MyDateUtils.MillisInDay;
                    case "提前一周":
                        clock_time -= 7 * MyDateUtils.MillisInDay;
                    default:
                        throw new InvalidParameterException();
                }

                clock_time += Integer.parseInt(hourItems.get(options2)) * MyDateUtils.MillisInHour;

                clock_time += Integer.parseInt(minItems.get(options3)) * MyDateUtils.MillisInMinute;
//                String str = options1Items.get(options1) + "的"
//                        + hourItems.get(options2) + "时"
//                        + minItems.get(options3) + "分";
                clockText.setText(String.format("提醒时间: %s", MyDateUtils.formatDate("yyyy-MM-dd HH:mm", clock_time)));
            }
        }).setOptionsSelectChangeListener((options1, options2, options3) -> {
            String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
            //        Toast.makeText(AddTaskActivity.this, str, Toast.LENGTH_SHORT).show();
        }).build();
        pvNoLinkOptions.setNPicker(options1Items, hourItems, minItems);
        pvNoLinkOptions.setSelectOptions(0, 0, 0);
        pvNoLinkOptions.show();

    }

    private void displayDatabaseInfo() {


        dbHelper = MySQLiteOpenHelper.getInstance(EditTask.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title", item.getText().toString());
        cv.put("content", content.getText().toString());
        String clockTimeText = clockText.getText().toString();
        long clockTime = -1;
        if (!clockTimeText.contains("不")) {
            clockTime = MyDateUtils.parseDate("yyyy-MM-dd HH:mm", clockTimeText, 5);
        }
        cv.put("clock", clockTime);

        cv.put("importance", str);

        cv.put("alert_time", MyDateUtils.parseDate("yyyy-MM-dd", time_data.getText().toString()));

        if (id != -1) {
            db.update("tb_todo", cv, "id=?", new String[]{id + ""});
        } else {
            id = (int) db.insert("tb_todo", null, cv);
        }
        if (clockTime != -1) {
            AlarmUtils.setAlarm(getApplicationContext(), id, clockTime, AlarmManager.RTC_WAKEUP);
        }
    }


}
