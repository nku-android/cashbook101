package devlight.io.sample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import devlight.io.sample.components.MySQLiteOpenHelper;

import static android.content.ContentValues.TAG;


public class PageTodolist extends Fragment implements ListAdapter.InnerItemOnclickListener {


    private List<ListAdapter.itemHolder> list = new ArrayList<ListAdapter.itemHolder>();
    private ListView todoList;
    private ListAdapter listAdapter;
    private MySQLiteOpenHelper dbHelper;
    private int insertPosition = 1;
    private boolean flag = false;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1_todolist, container, false);
        dbHelper = MySQLiteOpenHelper.getInstance(getContext());



        ListAdapter.itemHolder title_undo = new ListAdapter.itemHolder();
        title_undo.text = "主人~ 努力啊，还有这么多没完成呢~";
        title_undo.type = 0;
        list.add(title_undo);

        ListAdapter.itemHolder item_undo;
        String sql = "SELECT * FROM tb_todo WHERE is_done==0";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        ContentValues cv_undo = new ContentValues();
        while (cursor.moveToNext()) {
            item_undo = new ListAdapter.itemHolder();
            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv_undo);
            DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv_undo);
            DatabaseUtils.cursorIntToContentValues(cursor, "id", cv_undo);
            item_undo.text = cv_undo.getAsString("title");
            item_undo.time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cv_undo.getAsLong("alert_time"));
            item_undo.type = 1;
            item_undo.id = cv_undo.getAsInteger("id");
            list.add(item_undo);
            insertPosition++;
        }


//        for (int i = 0; i < 10; i++) {
//            test2 = new ListAdapter.itemHolder();
//            test2.text = "todolist" + i;
//            test2.time = i + ":00 pm";
//            test2.btn = R.drawable.ic_button_off;
//            test2.type = 1;
//            list.add(test2);
//        }

        ListAdapter.itemHolder tile_done = new ListAdapter.itemHolder();
        tile_done.text = "哇哦~超级棒!";
        tile_done.type = 0;
        list.add(tile_done);

        sql = "SELECT * FROM tb_todo";

        ListAdapter.itemHolder item_done;
        sql = "SELECT * FROM tb_todo WHERE is_done==1";
        cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        ContentValues cv_done = new ContentValues();
        while (cursor.moveToNext()) {
            item_done = new ListAdapter.itemHolder();
            DatabaseUtils.cursorStringToContentValues(cursor, "title", cv_done);
            DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv_done);
            DatabaseUtils.cursorIntToContentValues(cursor, "id", cv_done);
            item_done.text = cv_done.getAsString("title");
            item_done.time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cv_done.getAsLong("alert_time"));
            item_done.type = 2;
            item_done.id = cv_done.getAsInteger("id");
            list.add(item_done);
        }
//
//        for (int i = 0; i < 10; i++) {
//            test4 = new ListAdapter.itemHolder();
//            test4.text = "alreadydolist" + i;
//            test4.time = i + ":00 pm";
//            test4.btn = R.drawable.ic_button_on;
//            test4.type = 2;
//            list.add(test4);
//        }

        todoList = view.findViewById(R.id.todolist);

        FloatingActionButton addbutton;
        addbutton = view.findViewById(R.id.add_btn);
        listAdapter = new ListAdapter(getActivity(), R.layout.item_todolist, list, todoList);
        listAdapter.setOnInnerItemOnClickListener(this);
        todoList.setAdapter(listAdapter);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), addtask.class));

            }
        });



        return view;
    }


    @Override
    public void itemClick(View v) {
        ImageButton btn = getView().findViewById(v.getId());
        int position = (int) v.getTag();


        if(v.getId() == R.id.delete_btn){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("tb_todo","id= ?",new String[]{list.get(position).id+""});
            flag = true;
        }
        AnimatorSet animatorSet = Animations.DeleteAnimation(todoList, position);

        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                remove(position,flag);
                // 动画结束后，恢复ListView所有子View的属性
                for (int i = 0; i < todoList.getChildCount(); ++i) {
                    View v = todoList.getChildAt(i);
                    v.setAlpha(1f);
                    v.setTranslationY(0);
                    v.setTranslationX(0);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



    private void remove(int position, boolean flag) {
        if (position < list.size()) {
            if(flag){
                list.remove(position);
                flag = false;
            }else{
                if(list.get(position).type == 1){
                    ContentValues values = new ContentValues();
                    values.put("is_done",1);
                    dbHelper.getWritableDatabase().update("tb_todo",values,"id=?",new String[]{list.get(position).id+""});
                    insertPosition--;
                    ListAdapter.itemHolder change_item =  list.get(position);
                    list.remove(position);
                    change_item.type = 2;
                    list.add(change_item);

                }else{
                    ContentValues values = new ContentValues();
                    values.put("is_done",0);
                    dbHelper.getWritableDatabase().update("tb_todo",values,"id=?",new String[]{list.get(position).id+""});
                    ListAdapter.itemHolder change_item =  list.get(position);
                    list.remove(position);
                    change_item.type = 1;
                    list.add(insertPosition,change_item);
                    insertPosition++;
                }
            }

        }

        listAdapter.notifyDataSetChanged();
    }

}

