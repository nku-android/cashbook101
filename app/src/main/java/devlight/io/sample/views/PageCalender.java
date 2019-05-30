package devlight.io.sample.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.sample.R;
import devlight.io.sample.components.ListAdapter;
import devlight.io.sample.components.MessageEvent;
import devlight.io.sample.components.MySQLiteOpenHelper;
import devlight.io.sample.utils.Animations;
import devlight.io.sample.utils.MyDateUtils;
import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.SimpleMonthAdapter;

public class PageCalender extends Fragment {
    private final String TAG = getClass().getName();
    private MySQLiteOpenHelper dbHelper = MySQLiteOpenHelper.getInstance(getContext());
    private ListAdapter mListAdapter;
    private long current_timestamp;

//
//    void test(View view) {
//        AlarmUtils.setAlarm(getContext(), "TIMER_ACTION", 1, System.currentTimeMillis() + 5000, AlarmManager.RTC_WAKEUP);
//    }

    @BindView(R.id.one_day_todo)
    ListView one_day_todo;

    @BindView(R.id.calendar_view)
    CustomDayPickerView dayPickerView;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2_calender, container, false);
        ButterKnife.bind(this, view);
        this.current_timestamp = MyDateUtils.getTodayTimestamp();
        update_todo();


        this.dayPickerView.setController(new DatePickerController() {

            @Override
            public int getMaxYear() {
                return 0;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, 0, 0, 0);
                current_timestamp = calendar.getTimeInMillis();
                update_todo();
            }


            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            }
        });
        SimpleMonthAdapter.CalendarDay today = new SimpleMonthAdapter.CalendarDay(System.currentTimeMillis());
        this.dayPickerView.getAdapter().setSelectedDay(today);
        this.dayPickerView.getController().onDayOfMonthSelected(today.getYear(), today.getMonth(), today.getDay());

        return view;
    }

    private void update_todo() {
        ArrayList<ListAdapter.itemHolder> list = getOneDayTodo(this.current_timestamp);

        mListAdapter = new ListAdapter(getContext(), R.layout.item_todolist, list, one_day_todo);
        mListAdapter.setOnInnerItemOnClickListener(v -> {
            int position = (int) v.getTag();
            AnimatorSet animatorSet = Animations.DeleteAnimation(one_day_todo, position);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (position < list.size()) {
                        if (list.get(position).type == 1) {
                            ContentValues values = new ContentValues();
                            values.put("is_done", 1);
                            dbHelper.getWritableDatabase().update("tb_todo", values, "id=?", new String[]{list.get(position).id + ""});
                            list.get(position).type = 2;
                        } else if (list.get(position).type == 2) {
                            ContentValues values = new ContentValues();
                            values.put("is_done", 0);
                            dbHelper.getWritableDatabase().update("tb_todo", values, "id=?", new String[]{list.get(position).id + ""});
                            list.get(position).type = 1;
                        }

                    }
                    mListAdapter.setItemList(list);
                    one_day_todo.setAdapter(mListAdapter);
                    for (int i = 0; i < one_day_todo.getChildCount(); ++i) {
                        View child = one_day_todo.getChildAt(i);
                        child.setAlpha(1f);
                        child.setTranslationY(0);
                        child.setTranslationX(0);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animatorSet.start();


        });

        one_day_todo.setAdapter(mListAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (MessageEvent.UPDATE_TODO.equals(messageEvent.message)) {
            update_todo();
        }
    }

    private ArrayList<ListAdapter.itemHolder> getOneDayTodo(long today_start) {
        long today_end = today_start + 86400000;

        String sql = "SELECT * FROM tb_todo where (alert_time >= ? AND alert_time < ?)";

        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, new String[]{Long.toString(today_start), Long.toString(today_end)});

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

            item.type = cv.getAsInteger("is_done") + 1;
            item.id = cv.getAsInteger("id");
            list.add(item);
        }

        return list;
    }
}
