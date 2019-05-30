package devlight.io.sample.views;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import devlight.io.sample.R;
import devlight.io.sample.components.MessageEvent;
import devlight.io.sample.components.TodoItem;
import devlight.io.sample.components.ListAdapter;
import devlight.io.sample.components.MySQLiteOpenHelper;


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
        todoList = view.findViewById(R.id.todolist);
        update_todo();

        FloatingActionButton addBtn = view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), EditTask.class));
        });


        return view;
    }

    private void getAllTodos() {
        list = new ArrayList<>();
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
            DatabaseUtils.cursorIntToContentValues(cursor, TodoItem.ID, cv_undo);
            DatabaseUtils.cursorIntToContentValues(cursor, TodoItem.IS_DONE, cv_undo);
            DatabaseUtils.cursorStringToContentValues(cursor, TodoItem.TITLE, cv_undo);
            DatabaseUtils.cursorLongToContentValues(cursor, TodoItem.ALERT_TIME, cv_undo);

            item_undo = ListAdapter.itemHolder.fromContentValues(cv_undo);
            list.add(item_undo);
            insertPosition++;
        }


        ListAdapter.itemHolder tile_done = new ListAdapter.itemHolder();
        tile_done.text = "哇哦~超级棒!";
        tile_done.type = 0;
        list.add(tile_done);

        ListAdapter.itemHolder item_done;
        sql = "SELECT * FROM tb_todo WHERE is_done==1";
        cursor = dbHelper.getWritableDatabase().rawQuery(sql, null);
        ContentValues cv_done = new ContentValues();
        while (cursor.moveToNext()) {
            DatabaseUtils.cursorIntToContentValues(cursor, TodoItem.ID, cv_done);
            DatabaseUtils.cursorStringToContentValues(cursor, TodoItem.TITLE, cv_done);
            DatabaseUtils.cursorLongToContentValues(cursor, TodoItem.ALERT_TIME, cv_done);
            DatabaseUtils.cursorIntToContentValues(cursor, TodoItem.IS_DONE, cv_done);
            item_done = ListAdapter.itemHolder.fromContentValues(cv_done);
            list.add(item_done);
        }
    }

    @Override
    public void itemClick(View v) {
        int position = (int) v.getTag();

        if (v.getId() == R.id.list_item_text || v.getId() == R.id.list_item_time) {
            int id = list.get(position).id;
            Intent intent = new Intent(getActivity(), EditTask.class);
            intent.putExtra("id", id);
            startActivity(intent);

        } else {

            if (v.getId() == R.id.delete_btn) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("tb_todo", "id= ?", new String[]{list.get(position).id + ""});
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
                    remove(position, flag);
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
            if (flag) {
                list.remove(position);
                flag = false;
            } else {
                if (list.get(position).type == 1) {
                    ContentValues values = new ContentValues();
                    values.put("is_done", 1);
                    dbHelper.getWritableDatabase().update("tb_todo", values, "id=?", new String[]{list.get(position).id + ""});
                    insertPosition--;
                    ListAdapter.itemHolder change_item = list.get(position);
                    list.remove(position);
                    change_item.type = 2;
                    list.add(change_item);

                } else {
                    ContentValues values = new ContentValues();
                    values.put("is_done", 0);
                    dbHelper.getWritableDatabase().update("tb_todo", values, "id=?", new String[]{list.get(position).id + ""});
                    ListAdapter.itemHolder change_item = list.get(position);
                    list.remove(position);
                    change_item.type = 1;
                    list.add(insertPosition, change_item);
                    insertPosition++;
                }
            }

        }

        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (MessageEvent.UPDATE_TODO.equals(messageEvent.message)) {
            update_todo();
        }
    }

    private void update_todo() {
        getAllTodos();

        listAdapter = new ListAdapter(getActivity(), R.layout.item_todolist, list, todoList);
        listAdapter.setOnInnerItemOnClickListener(this);
        todoList.setAdapter(listAdapter);
    }
}

