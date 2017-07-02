package com.wudy.newsmakers.pager;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.wudy.newsmakers.R;

public class PagerTransFormer implements ViewPager.PageTransformer {
    private View backgroundImageView;
    private View titleView;
    private View descriptionView;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        backgroundImageView = page.findViewById(R.id.main_background_image);
        titleView = page.findViewById(R.id.main_title_text);
        descriptionView = page.findViewById(R.id.main_description_text);

        backgroundImageView.setTranslationX(-position * (pageWidth/2));
        titleView.setTranslationX((position) * (pageWidth / 3));
        descriptionView.setTranslationX((position) * (pageWidth / 1.5f));
    }
}