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
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devlight.io.sample.components.MySQLiteOpenHelper;

public class PageCalender extends Fragment {
    private final String TAG = getClass().getName();
    private MySQLiteOpenHelper dbHelper;
    @BindView(R.id.test_btn)
    Button testBtn;

    @OnClick(R.id.test_btn)
    public void testBtn() {
        Log.i(TAG, "clicked");

        dbHelper = MySQLiteOpenHelper.getInstance(getContext());

        String sql = String.format(Locale.getDefault(), "SELECT * FROM %s", TodoItem.DB_NAME);
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);

        ContentValues cv = new ContentValues();
        while (cursor.moveToNext()) {
            DatabaseUtils.cursorStringToContentValues(cursor, TodoItem.TITLE, cv);
            DatabaseUtils.cursorStringToContentValues(cursor, TodoItem.IS_DONE, cv);
            DatabaseUtils.cursorStringToContentValues(cursor, TodoItem.ALERT_TIME, cv);

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2_calender, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
