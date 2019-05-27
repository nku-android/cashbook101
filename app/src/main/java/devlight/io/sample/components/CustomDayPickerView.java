package devlight.io.sample.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

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

        @Override
        public int getMaxYear() {
            return 0;
        }

        @Override
        public void onDayOfMonthSelected(int year, int month, int day) {

            Toast.makeText(getContext(), String.format(Locale.CHINA, "select %d年%d月%d日", year, month, day), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            Log.i(TAG, "onDateRangeSelected");
        }
    }

}
