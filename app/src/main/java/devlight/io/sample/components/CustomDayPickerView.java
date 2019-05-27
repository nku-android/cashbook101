package devlight.io.sample.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.sample.ListAdapter;
import devlight.io.sample.R;
import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;

public class CustomDayPickerView extends DayPickerView {


    // xml init
    public CustomDayPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setController(new DatePickerControllerImpl());

    }


    public class DatePickerControllerImpl implements DatePickerController {
        private final String TAG = this.getClass().getName();
        ListView listView;
        MySQLiteOpenHelper dbHelper;

        public DatePickerControllerImpl() {
            this.dbHelper = MySQLiteOpenHelper.getInstance(getContext());
            this.listView = findViewById(R.id.one_day_todo);

        }

        @Override
        public int getMaxYear() {
            return 0;
        }

        @Override
        public void onDayOfMonthSelected(int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            updateTodo(calendar.getTimeInMillis());
            Toast.makeText(getContext(), String.format(Locale.CHINA, "select %d年%d月%d日", year, month + 1, day), Toast.LENGTH_SHORT).show();
        }

        private void updateTodo(long today_start) {
            long today_end = today_start + 86400000;

            String sql = "SELECT * FROM tb_todo where (alert_time >= ? AND alert_time < ?)";
            Cursor cursor = this.dbHelper.getWritableDatabase().rawQuery(sql, new String[]{Long.toString(today_start), Long.toString(today_end)});

            ArrayList<ListAdapter.itemHolder> list = new ArrayList<>();

            while (cursor.moveToNext()) {

                ContentValues cv = new ContentValues();
                DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
                DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv);
                DatabaseUtils.cursorIntToContentValues(cursor, "id", cv);
                DatabaseUtils.cursorIntToContentValues(cursor, "is_done", cv);
                ListAdapter.itemHolder item = new ListAdapter.itemHolder();

                item.text = cv.getAsString("title");
                item.time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cv.getAsLong("alert_time"));

                item.type = 1;
                item.id = cv.getAsInteger("id");
                list.add(item);
            }

            ListView one_day_todo = getRootView().findViewById(R.id.one_day_todo);

            ListAdapter mListAdapter = new ListAdapter(getContext(), R.layout.item_todolist, list, one_day_todo);
            one_day_todo.setAdapter(mListAdapter);
        }

        @Override
        public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            Log.i(TAG, "onDateRangeSelected");
        }
    }

}
