package devlight.io.sample.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;

import java.util.ArrayList;

public final class Animations {
    public static AnimatorSet DeleteAnimation(ListView listView, int position) {
        // 存储所有的Animator，利用AnimatorSet直接播放
        ArrayList<Animator> animators = new ArrayList<Animator>();
        //获取显示的一个view的position
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        View deleteView = listView.getChildAt(position - firstVisiblePosition);
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
        for (int i = firstViewToMove; i < listView.getChildCount(); ++i) {
            View viewToMove = listView.getChildAt(i);
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

}
