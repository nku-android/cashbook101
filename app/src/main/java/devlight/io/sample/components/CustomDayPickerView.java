package devlight.io.sample.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;

public class CustomDayPickerView extends DayPickerView {


    public CustomDayPickerView(Context context) {
        super(context);
        this.setController(new DatePickerControllerImpl());

    }

    // xml init
    public CustomDayPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setController(new DatePickerControllerImpl());
    }

    public CustomDayPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            Log.i(TAG, "onDayOfMonthSelected");
        }

        @Override
        public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            Log.i(TAG, "onDateRangeSelected");
        }
    }

}
