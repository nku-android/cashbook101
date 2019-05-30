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


    private List<ListAdapter.itemHolder> list;
    private ListView todoList;
    private ListAdapter listAdapter;
    private MySQLiteOpenHelper dbHelper;
    private int insertPosition;
    private boolean flag = false;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1_todolist, container, false);
        dbHelper = MySQLiteOpenHelper.getInstance(getContext());

        list = new ArrayList<ListAdapter.itemHolder>();
        insertPosition = 1;


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
            DatabaseUtils.cursorStringToContentValues(cursor, "alert_time", cv_undo);
            DatabaseUtils.cursorIntToContentValues(cursor,"id",cv_undo);
            DatabaseUtils.cursorLongToContentValues(cursor, "alert_time", cv_undo);
            DatabaseUtils.cursorIntToContentValues(cursor, "id", cv_undo);

            item_undo.text = cv_undo.getAsString("title");
            item_undo.time = cv_undo.getAsString("alert_time");
            String time = cv_undo.getAsString("alert_time");;
            item_undo.time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6);
            item_undo.type = 1;
            item_undo.id = cv_undo.getAsInteger("id");
            list.add(item_undo);
            insertPosition++;
        }



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
            String time = cv_done.getAsString("alert_time");;
            item_done.time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6);
            item_done.type = 2;
            item_done.id = cv_done.getAsInteger("id");
            list.add(item_done);
        }


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
        int position = (int) v.getTag();

        if(v.getId() == R.id.list_item_text || v.getId() == R.id.list_item_time){
            int id = list.get(position).id;
            Intent intent = new Intent(getActivity(),EditTask.class);
            intent.putExtra("id",id);
            startActivity(intent);

        }else{

            if (v.getId() == R.id.delete_btn){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("tb_todo","id= ?",new String[]{list.get(position).id+""});
                flag = true;
            }

            AnimatorSet animatorSet = getDeleteAnimation(position);
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

    }


    private AnimatorSet getDeleteAnimation(int position) {

        // 存储所有的Animator，利用AnimatorSet直接播放
        ArrayList<Animator> animators = new ArrayList<Animator>();
        //获取显示的一个view的position
        int firstVisiblePosition = todoList.getFirstVisiblePosition();
        View deleteView = todoList.getChildAt(position - firstVisiblePosition);
        int viewHeight = deleteView.getHeight();
        int viewWidth = deleteView.getWidth();

        //平移动画
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(deleteView, "translationX", viewWidth);
        translationXAnimator.setDuration(500);
        animators.add(translationXAnimator);

        //透明动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(deleteView, "alpha", 1, 0);
        alphaAnimator.setDuration(500);
        animators.add(alphaAnimator);

        int delay = 500;
        int firstViewToMove = position + 1;
        for (int i = firstViewToMove; i < todoList.getChildCount(); ++i) {
            View viewToMove = todoList.getChildAt(i);
            ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(viewToMove, "translationY", 0, -viewHeight);
            moveAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            moveAnimator.setStartDelay(delay);

            delay += 100;

            animators.add(moveAnimator);
        }
        //动画集合
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(animators);

        return animationSet;

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

