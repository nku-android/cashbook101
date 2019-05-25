package devlight.io.sample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public class PageTodolist extends Fragment implements ListAdapter.InnerItemOnclickListener {


    private List<ListAdapter.itemHolder> list = new ArrayList<ListAdapter.itemHolder>();
    private ListView todoList;
    private ListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1_todolist,container,false);

        ListAdapter.itemHolder test1 = new ListAdapter.itemHolder();
        test1.text = "主人~ 努力啊，还有这么多没完成呢~";
        test1.type = 0;
        list.add(test1);


        ListAdapter.itemHolder test2;
        for(int i=0;i<20;i++){
            test2 = new ListAdapter.itemHolder();
            test2.text = "todolist"+i;
            test2.time = i+":00 pm";
            test2.btn = R.drawable.ic_button_off;
            test2.type = 1;
            list.add(test2);
        }

        ListAdapter.itemHolder test3 = new ListAdapter.itemHolder();
        test3.text="哇哦~超级棒!";
        test3.type = 0;
        list.add(test3);

        ListAdapter.itemHolder test4;
        for(int i=0;i<20;i++){
            test4 = new ListAdapter.itemHolder();
            test4.text="alreadydolist"+i;
            test4.time = i+":pm";
            test4.btn=R.drawable.ic_button_on;
            test4.type = 2;
            list.add(test4);
        }



        todoList = (ListView) view.findViewById(R.id.todolist);
        listAdapter = new ListAdapter(getActivity(),R.layout.item_todolist,list,todoList);
        listAdapter.setOnInnerItemOnClickListener(this);
        todoList.setAdapter(listAdapter);

        return view;
    }


    @Override
    public void itemClick(View v) {
        ImageButton btn =  getView().findViewById(v.getId());
        int position = (int) v.getTag();

        AnimatorSet animatorSet = getDeleteAnimation(position);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                remove(position);
                // 动画结束后，恢复ListView所有子View的属性
                for (int i=0; i<todoList.getChildCount() ;++i){
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



    private AnimatorSet getDeleteAnimation(int position){

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
        for (int i=firstViewToMove;i < todoList.getChildCount(); ++i){
            View viewToMove = todoList.getChildAt(i);
            ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(viewToMove, "translationY", 0,  - viewHeight);
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

    private void remove(int position){
        if (position < list.size()){
            list.remove(position);
        }
        listAdapter.notifyDataSetChanged();
    }

}

