package devlight.io.sample.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;

public class CustomDayPickerView extends DayPickerView {


    // xml init
    public CustomDayPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    public SimpleMonthAdapter getAdapter() {
        return this.mAdapter;
    }


    @Override
    public DatePickerController getController() {
        return super.getController();
    }
}
