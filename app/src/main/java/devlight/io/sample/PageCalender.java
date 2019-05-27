package devlight.io.sample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        String sql = "SELECT * FROM tb_todo";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);

        ContentValues cv = new ContentValues();
        while (cursor.moveToNext()) {
            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv);
            DatabaseUtils.cursorIntToContentValues(cursor, "alert_time", cv);
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
