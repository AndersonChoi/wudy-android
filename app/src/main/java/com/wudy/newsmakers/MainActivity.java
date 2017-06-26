package com.wudy.newsmakers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.wudy.newsmakers.list.MainListViewAdaptor;

//webhook test
public class MainActivity extends Activity {

    private RelativeLayout mainTitleLayout;
    private ListView listview;
    private MainListViewAdaptor adapter;
    private boolean titleFlag = true;
    private boolean transitionFlag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTitleLayout = (RelativeLayout) findViewById(R.id.main_title_layout);

        // Adapter 생성
        adapter = new MainListViewAdaptor();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.main_list_view);
        listview.setAdapter(adapter);
        AddItems(adapter);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Raleway_Light.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Reckoner_Bold.ttf");

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.main_rotating_title);
        rotatingTextWrapper.setSize(35);
        rotatingTextWrapper.setTypeface(typeface2);
        rotatingTextWrapper.setContent("News Maker");



        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int recentItemPosition = listview.getFirstVisiblePosition();
                if (recentItemPosition > 0) {
                    titleFlag = false;
                } else {
                    titleFlag = true;
                }


                if (titleFlag == false && transitionFlag == true) {
                    AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
                    fadeOutAnimation.setDuration(600);
                    fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                            mainTitleLayout.setVisibility(View.GONE);
                        }

                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    mainTitleLayout.startAnimation(fadeOutAnimation);
                } else if (titleFlag == true && transitionFlag == false) {
                    AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
                    fadeInAnimation.setDuration(300);
                    fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                            mainTitleLayout.setVisibility(View.VISIBLE);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    mainTitleLayout.startAnimation(fadeInAnimation);
                }
                transitionFlag = titleFlag;

            }
        });


    }


    private void AddItems(MainListViewAdaptor adapter) {
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image01),
                "문재인 대통령 “6.25 전쟁, 아픈 역사…대통령으로서 모든 노력 다 할 것”", "문재인 대통령은 6.25 전쟁 67주년을 맞이해 \"참전 용사와 그 유족의 희생에 고개를 숙인다\"고 추모했다. 문 대통령은 25일 자신의 페이스북을 통해 \"6.25 전쟁은 아픈 역사다\"라며 \"한반도 땅 대부분이 전쟁의 참상을 겪었고, 수백만 명에 이르는 사람들이 목숨을 잃거나 부상을 당했다\"라고 밝혔다.");

        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image02),
                "갑작스런 유가 급락, 한국경제 돌발악재 '급부상'", "[이데일리 김정남 기자] 회복 조짐을 보이던 우리 경제에 돌발악재가 나타났다. 이번달 국제유가가 갑자기 배럴당 40달러 초반대로 급락하면서 정책당국이 촉각을 곤두세우고 있다.");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image03),
                "중단됐던 서울 '지하철 6호선 급행화' 9개월만에 재검토", "(서울=연합뉴스) 박초롱 기자 = 서울시가 비용과 안전문제 때문에 중단했던 지하철 6호선 급행화 사업에 대한 재검토에 나섰다. 서울 6호선·분당선·수인선·경의선 등으로 급행화 노선을 확대해 수도권 외곽 주민의 출퇴근 시간을 단축하겠다는 게 문재인 대통령 공약이기 때문이다.");

        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image),
                "Ind", "Assignment Ind Black 36dp");

        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image),
                "Ind", "Assignment Ind Black 36dp");

        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.temp_image),
                "Ind", "Assignment Ind Black 36dp");
    }


}
