package com.wudy.newsmakers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.wudy.newsmakers.dummy.Dummy;
import com.wudy.newsmakers.pager.PagerTransFormer;
import com.wudy.newsmakers.pager.ParallaxAdapter;
import com.wudy.newsmakers.pager.ParallaxFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity {

    private static boolean IS_ANIMATE_PAGER = false;
    private static int ANIMATE_NEXT_PAGER_X = 70;
    private ViewPager viewPager;
    private ParallaxAdapter mAdapter;
    private Dummy dummy;
    private int positionXCount = 1;
    private int positionX = 1;
    private Handler pageAnimationHandler;
    private Runnable pageAnimationRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);

        setMainParallaxPager();
        setPagerDatas();

        viewPager.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        pageAnimationRunnable = new Runnable() {
            @Override
            public void run() {

                if(!IS_ANIMATE_PAGER) {
                    viewPager.setScrollX(positionX);
                    Log.e("scroll", "positionX:" + positionX);
                    positionX = positionXCount < ANIMATE_NEXT_PAGER_X ? positionXCount + 1 : (ANIMATE_NEXT_PAGER_X * 2) - positionXCount;
                    positionXCount++;

                    if (positionX == 0) {
                        IS_ANIMATE_PAGER=true;
                        return;
                    }

                    if (positionX == ANIMATE_NEXT_PAGER_X) {
                        pageAnimationHandler.postDelayed(pageAnimationRunnable, 100);
                    } else if (positionXCount < ANIMATE_NEXT_PAGER_X) {
                        pageAnimationHandler.postDelayed(pageAnimationRunnable, 1);
                    } else if (positionXCount > ANIMATE_NEXT_PAGER_X) {
                        pageAnimationHandler.postDelayed(pageAnimationRunnable, 8);
                    }
                }
            }
        };
        pageAnimationHandler = new Handler();
        pageAnimationHandler.postDelayed(pageAnimationRunnable, 500);

    }

    private void setPagerDatas() {

        dummy = new Dummy();
        int index = 0;
        for (String mainUrl : dummy.dummyURL) {
            Bundle tempBundle = new Bundle();
            tempBundle.putString("mainPagerUrl", mainUrl);
            ParallaxFragment parallaxFragment = new ParallaxFragment();
            parallaxFragment.setArguments(tempBundle);
            mAdapter.add(parallaxFragment);
        }

    }

    private void setMainParallaxPager() {
        viewPager.setBackgroundColor(0xFF000000);
        viewPager.setPageTransformer(false, new PagerTransFormer());
        mAdapter = new ParallaxAdapter(getSupportFragmentManager());
        mAdapter.setPager(viewPager);
    }


}