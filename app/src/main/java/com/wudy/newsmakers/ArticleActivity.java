package com.wudy.newsmakers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.wudy.newsmakers.opengraph.OpenGraphVO;
import android.webkit.WebViewClient;

public class ArticleActivity extends Activity {


    private TextView articleTitle;
    private Intent intent;
    private View activityTransitionView;
    private Typeface font;
    private OpenGraphVO articleData;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articleTitle = (TextView) findViewById(R.id.article_title_text);
        mWebView = (WebView) findViewById(R.id.article_webview);

        font = Typeface.createFromAsset(getAssets(), "fonts/BMDOHYEON_ttf.ttf");
        articleTitle.setTypeface(font);

        intent = getIntent();
        articleData = (OpenGraphVO) intent.getSerializableExtra("openGraphVO");


        articleTitle.setText(articleData.getOgTitle());

        activityTransitionView = findViewById(R.id.article_summary_layout_top);
        activityTransitionView.setTransitionName(MainActivity.ARTICLE_TITLE_VIEW);
        mWebView.setTransitionName(MainActivity.ARTICLE_WEB_VIEW);




        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(articleData.getOgUrl().replaceAll("entertain", "m.entertain"));
        mWebView.setWebViewClient(new WebViewClient());


    }
}
