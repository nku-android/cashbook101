package devlight.io.sample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devlight.io.sample.components.MySQLiteOpenHelper;

public class PageCalender extends Fragment {
    private final String TAG = getClass().getName();
    private MySQLiteOpenHelper dbHelper;


    @BindView(R.id.one_day_todo)
    ListView one_day_todo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2_calender, container, false);
        ButterKnife.bind(this, view);

        ArrayList<ListAdapter.itemHolder> list = new ArrayList<>();

        this.dbHelper = MySQLiteOpenHelper.getInstance(getContext());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long today_start = calendar.getTimeInMillis();
        long today_end = today_start + 86400;

        String sql = "SELECT * FROM tb_todo where alert_time > ? and alert_time < ?";
        Cursor cursor = this.dbHelper.getWritableDatabase().rawQuery(sql, new String[]{Long.toString(today_start), Long.toString(today_end)});
        while (cursor.moveToNext()) {

            ContentValues cv = new ContentValues();
            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
            DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv);
            DatabaseUtils.cursorIntToContentValues(cursor, "id", cv);
            DatabaseUtils.cursorIntToContentValues(cursor, "is_done", cv);
            ListAdapter.itemHolder item = new ListAdapter.itemHolder();

            item.text = cv.getAsString("title");
            item.time = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(cv.getAsLong("alert_time"));

            item.type = cv.getAsInteger("is_done");
            item.id = cv.getAsInteger("id");
            list.add(item);
        }


        ListAdapter mListAdapter = new ListAdapter(getContext(), R.layout.item_todolist, list, one_day_todo);
        one_day_todo.setAdapter(mListAdapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
