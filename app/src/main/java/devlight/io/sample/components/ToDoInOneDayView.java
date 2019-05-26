package devlight.io.sample.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ToDoInOneDayView extends Fragment {
    private final String TAG = getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Button btn = new Button(getContext());
        btn.setText("test");

        btn.setOnClickListener(v -> {
            String uri = "content://devlight.io.sample.components.DbContentProvider/TODO";

            Cursor ret = getContext().getContentResolver().query(Uri.parse(uri), new String[]{"*"}, null, null, null);

            Bundle bundle = ret.getExtras();
            ret.close();
            Log.i(TAG, Boolean.toString(btn.isShown()));

        });

        container.addView(btn);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
