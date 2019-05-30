package devlight.io.sample.views;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import devlight.io.sample.components.MessageEvent;
import devlight.io.sample.R;


public class HorizontalNtbActivity extends FragmentActivity {

    FragmentManager fm = getSupportFragmentManager();
    private final String TAG = getClass().getName();



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        ViewPager viewPager = (ViewPager)findViewById(R.id.vp_horizontal_ntb);
        String waitPayFlag = getIntent().getStringExtra("id") ;
        if(!TextUtils.isEmpty(waitPayFlag)){
            if ("1".equals(waitPayFlag)) {
                // 这里设置要跳转到第几个fragment
                viewPager.setCurrentItem(1);
            }
        }
        initUI();
    }


    private void initUI() {
        // 找到 viewpager

        final ViewPager viewPager = findViewById(R.id.vp_horizontal_ntb);
        viewPager.setBackgroundColor(Color.parseColor("#FFF5EE"));
        List<Fragment> view_container = new ArrayList<>();
        PageTodolist pageTodolist = new PageTodolist();
        view_container.add(pageTodolist);

        PageLemon pageLemon = new PageLemon();
        PageCalender pageCalender = new PageCalender();


        view_container.add(pageCalender);
        view_container.add(pageLemon);


        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                return view_container.get(position);
            }

        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_list),
                        Color.parseColor(colors[2]))
                        .title("List")
                        .badgeTitle("state")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_calendar),
                        Color.parseColor(colors[5]))
                        .title("Calendar")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_lemon),
                        Color.parseColor(colors[1]))
                        .title("Lemon Tree")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
                if (position == 1) {
                    EventBus.getDefault().post(MessageEvent.UpdateTodo());
                }

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(() -> {
            for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                navigationTabBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //model.showBadge();
                    }
                }, i * 100);
            }
        }, 500);
    }


}
