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

import java.util.ArrayList;
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
        ListAdapter.itemHolder test2;
        for (int i = 0; i < 10; i++) {
            test2 = new ListAdapter.itemHolder();
            test2.text = "todolist" + i;
            test2.time = i + ":00 pm";
            test2.btn = R.drawable.ic_button_off;
            test2.type = 1;
            list.add(test2);
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
