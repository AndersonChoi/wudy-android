package com.wudy.newsmakers.pager;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Pair;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.wudy.newsmakers.ArticleActivity;
import com.wudy.newsmakers.R;
import com.wudy.newsmakers.opengraph.OpenGraph;
import com.wudy.newsmakers.opengraph.OpenGraphVO;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class ParallaxFragment extends Fragment {

    public final static String ARTICLE_TITLE_VIEW = "ARTICLE_TITLE_VIEW";
    public final static String ARTICLE_WEB_VIEW = "ARTICLE_WEB_VIEW";




    private ParallaxAdapter mCatsAdapter;
    private OpenGraph openGraphParser;
    private OpenGraphVO openGraphVO;
    private Handler mHandler;
    private Runnable mRunnable;
    private ImageView pagerImageView;
    private TextView pagerTitletext;
    private TextView slidngUpTitleText;
    private TextView pagerDescriptiontext;
    private String tempUrl;
    private Button showArticleButton;
    private Button likeArticleButton;
    //private ImageView shareArticleButton;
    private ImageView likeArticleImage;
    private RelativeLayout likeAnimationLayout;
    private RelativeLayout articleSummaryLayout;
    private ImageView articleBottomLine;
    private Typeface font;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private RelativeLayout slidingUpTitleLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.demo_fragment_parallax, container, false);
        pagerImageView = (ImageView) v.findViewById(R.id.main_background_image);
        pagerTitletext = (TextView) v.findViewById(R.id.main_title_text);
        pagerDescriptiontext = (TextView) v.findViewById(R.id.main_description_text);
        showArticleButton = (Button) v.findViewById(R.id.show_article_button);
//        shareArticleButton = (ImageView) v.findViewById(R.id.share_article_icon);
        likeArticleButton = (Button) v.findViewById(R.id.like_article_button);
        slidngUpTitleText = (TextView) v.findViewById(R.id.article_title_text2);
        likeArticleImage = (ImageView) v.findViewById(R.id.like_article_icon);
        likeAnimationLayout = (RelativeLayout) v.findViewById(R.id.like_animation_layout);
        articleSummaryLayout = (RelativeLayout) v.findViewById(R.id.article_summary_layout);
        articleBottomLine = (ImageView) v.findViewById(R.id.article_bottom_line);
        slidingUpTitleLayout = (RelativeLayout)v.findViewById(R.id.sliding_up_layout);

        tempUrl = getArguments().getString("mainPagerUrl");


        mHandler = new Handler();
        openGraphVO = new OpenGraphVO();


        slidingUpPanelLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e("asdf", "onPanelSlide, offset " + slideOffset);

                slidingUpTitleLayout.setVisibility(View.VISIBLE);
                AlphaAnimation animation1 = new AlphaAnimation(slideOffset, slideOffset);
                animation1.setDuration(1);
                animation1.setFillAfter(true);
                slidingUpTitleLayout.startAnimation(animation1);


            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.e("asdf", "onPanelStateChanged " + newState);
                if(newState ==PanelState.COLLAPSED){
                    slidingUpTitleLayout.setVisibility(View.INVISIBLE);
                }
            }
        });




        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BMDOHYEON_ttf.ttf");
        pagerTitletext.setTypeface(font);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IropkeBatangM.ttf");
        pagerDescriptiontext.setTypeface(font);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(getActivity()).load(openGraphVO.getOgImage()).into(pagerImageView);
                }catch(Exception e){
                    Log.e("glide","e:"+e);
                }
                pagerTitletext.setText(openGraphVO.getOgTitle());
                slidngUpTitleText.setText(openGraphVO.getOgTitle());
                pagerDescriptiontext.setText(openGraphVO.getOgDescription());
            }
        };


        new Thread() {
            public void run() {

                try {
                    openGraphParser = new OpenGraph(tempUrl, true);
                    openGraphVO.setOgTitle(Html.fromHtml(openGraphParser.getContent("title")).toString());
                    openGraphVO.setOgDescription(Html.fromHtml(openGraphParser.getContent("description")).toString());
                    openGraphVO.setOgImage(openGraphParser.getContent("image"));
                    openGraphVO.setOgUrl(openGraphParser.getContent("url"));
                    mHandler.post(mRunnable);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("thread", "thraed :" + e);
                }


            }
        }.start();
//
//
//        shareArticleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int animationDuration = 700;
//                YoYo.with(Techniques.Shake)
//                        .duration(animationDuration)
//                        .repeat(1)
//                        .playOn(shareArticleButton);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        fabRevealLayout.revealMainView();
//                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//                        share.setType("text/plain");
//                        share.putExtra(Intent.EXTRA_SUBJECT, "News Maker");
//                        share.putExtra(Intent.EXTRA_TEXT, openGraphParser.getContent("url"));
//                        startActivity(Intent.createChooser(share, "Share link!"));
//                    }
//                }, (animationDuration / 4));
//            }
//        });
//

        showArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("openGraphVO", openGraphVO);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        Pair.create((View)pagerTitletext, ARTICLE_TITLE_VIEW),
                        Pair.create((View)articleBottomLine, ARTICLE_WEB_VIEW));

                startActivity(intent, options.toBundle());


            }
        });

        likeArticleButton.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {

                    likeArticleImage.setVisibility(View.VISIBLE);
                    int animationDuration = 800;
                    YoYo.with(Techniques.Tada)
                            .duration(animationDuration)
                            .repeat(1)
                            .playOn(likeArticleImage);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Drawable yourDrawable = MaterialDrawableBuilder.with(getActivity())
                                    .setIcon(MaterialDrawableBuilder.IconValue.HEART)
                                    .setColor(Color.RED)
                                    .build();
                            likeArticleImage.setImageDrawable(yourDrawable);


                            int cx = (likeAnimationLayout.getLeft() + likeAnimationLayout.getRight()) / 2;
                            int cy = (likeAnimationLayout.getTop() + likeAnimationLayout.getBottom()) ;
                            int finalRadius = Math.max(likeAnimationLayout.getWidth(), likeAnimationLayout.getHeight());
                            Animator anim =
                                    ViewAnimationUtils.createCircularReveal(likeAnimationLayout, cx, cy, 0, finalRadius);
                            likeAnimationLayout.setVisibility(View.VISIBLE);
                            anim.start();
                        }
                    }, (animationDuration / 2));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            likeArticleImage.setVisibility(View.INVISIBLE);
                        }
                    }, animationDuration + 100);

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


        return v;
    }

    public void setAdapter(ParallaxAdapter catsAdapter) {
        mCatsAdapter = catsAdapter;
    }





}
