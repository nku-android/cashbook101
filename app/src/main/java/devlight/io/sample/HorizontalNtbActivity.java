package devlight.io.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import devlight.io.library.ntb.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIGAMOLE on 28.03.2016.
 */

public class HorizontalNtbActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);
        initUI();

    }

    private void initUI()  {
        // 找到 viewpager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        // 找到三个页面，放入容器中
        final View page1_todolist = LayoutInflater.from(
                getBaseContext()).inflate(R.layout.page1_todolist, null, false);
        final View page2_calender = LayoutInflater.from(
                getBaseContext()).inflate(R.layout.page2_calender,null,false);
        final View page3_lemon = LayoutInflater.from(
                getBaseContext()).inflate(R.layout.page3_lemon,null,false);

        List<View> view_container = new ArrayList<View>();

        view_container.add(page1_todolist);
        view_container.add(page2_calender);
        view_container.add(page3_lemon);


        startActivity(new Intent(HorizontalNtbActivity.this, lemontime.class));


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                container.addView(view_container.get(position));
                return view_container.get(position);
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_list),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("List")
                        .badgeTitle("state")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_calendar),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Calendar")
                        // .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_lemon),
                        Color.parseColor(colors[2]))
                        .title("Lemon Tree")
                        //  .badgeTitle("with")
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
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
}
