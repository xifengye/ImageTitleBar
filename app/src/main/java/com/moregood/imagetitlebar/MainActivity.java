package com.moregood.imagetitlebar;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Data data;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private TabLayout mytab;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle data) {
        super.onCreate(data);
        setContentView(R.layout.activity_main);
        initSubView();
    }

    protected void initSubView() {
        data = new Data("Hello");
        pager = findViewById(R.id.pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        mytab = findViewById(R.id.tabs);

        mytab.setupWithViewPager(pager);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView titleView = toolbar.findViewById(R.id.toolbar_title);
        titleView.setText("Title");

        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state,float value) {
                if( state == State.EXPANDED ) {
                    toolbar.setAlpha(value);
                }else if(state == State.COLLAPSED){
                    toolbar.setAlpha(value);
                }else {
                    toolbar.setAlpha(value);
                }
            }
        });

    }

    public abstract static class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
        public enum State {
            EXPANDED,//展开状态
            COLLAPSED,//折叠状态
            IDLE//中间状态
        }

        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED,0);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED,1);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                onStateChanged(appBarLayout, State.IDLE,Math.abs(i)*1.0f/appBarLayout.getTotalScrollRange());
                mCurrentState = State.IDLE;
            }
        }
        public abstract void onStateChanged(AppBarLayout appBarLayout, State state,float value);
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] items;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.items = getResources().getStringArray(R.array.tags);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items[position];
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(data);
        }

    }
}
