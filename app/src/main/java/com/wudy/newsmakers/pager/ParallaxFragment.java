package com.wudy.newsmakers.pager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.wudy.newsmakers.ArticleActivity;
import com.wudy.newsmakers.MainActivity;
import com.wudy.newsmakers.R;
import com.wudy.newsmakers.opengraph.MetaElement;
import com.wudy.newsmakers.opengraph.OpenGraph;
import com.wudy.newsmakers.opengraph.OpenGraphVO;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class ParallaxFragment extends Fragment {

    private ParallaxAdapter mCatsAdapter;
    private OpenGraph openGraphParser;
    private OpenGraphVO openGraphVO;
    private Handler mHandler;
    private Runnable mRunnable;
    private ImageView pagerImageView;
    private TextView pagerTitletext;
    private TextView pagerDescriptiontext;
    private String tempUrl;
    private Button exitFabLayoutButton;
    private Button showArticleButton;
    private ImageView shareArticleButton;
    private ImageView likeArticleButton;
    private FABRevealLayout fabRevealLayout;
    private Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.demo_fragment_parallax, container, false);
        pagerImageView = (ImageView) v.findViewById(R.id.main_background_image);
        pagerTitletext = (TextView) v.findViewById(R.id.main_title_text);
        pagerDescriptiontext = (TextView) v.findViewById(R.id.main_description_text);
        exitFabLayoutButton = (Button) v.findViewById(R.id.fab_exit_button);
        shareArticleButton = (ImageView) v.findViewById(R.id.share_article_icon);
        likeArticleButton = (ImageView) v.findViewById(R.id.like_article_icon);
        showArticleButton = (Button)v.findViewById(R.id.show_article_button);

        tempUrl = getArguments().getString("mainPagerUrl");

        fabRevealLayout = (FABRevealLayout) v.findViewById(R.id.fab_reveal_layout);
        configureFABReveal(fabRevealLayout);

        mHandler = new Handler();
        openGraphVO = new OpenGraphVO();

        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BMDOHYEON_ttf.ttf");
        pagerTitletext.setTypeface(font);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IropkeBatangM.ttf");
        pagerDescriptiontext.setTypeface(font);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity()).load(openGraphVO.getOgImage()).into(pagerImageView);
                pagerTitletext.setText(openGraphVO.getOgTitle());
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


        exitFabLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabRevealLayout.revealMainView();
            }
        });


        shareArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int animationDuration=700;
                YoYo.with(Techniques.Shake)
                        .duration(animationDuration)
                        .repeat(1)
                        .playOn(shareArticleButton);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                        share.setType("text/plain");

                        share.putExtra(Intent.EXTRA_SUBJECT, "현준이 바보");
                        share.putExtra(Intent.EXTRA_TEXT,openGraphParser.getContent("url") );
                        startActivity(Intent.createChooser(share, "Share link!"));
                    }
                }, (animationDuration/4));
            }
        });

        likeArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int animationDuration=700;
                YoYo.with(Techniques.Tada)
                        .duration(animationDuration)
                        .repeat(2)
                        .playOn(likeArticleButton);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Drawable yourDrawable = MaterialDrawableBuilder.with(getActivity()) // provide a context
                                .setIcon(MaterialDrawableBuilder.IconValue.HEART) // provide an icon
                                .setColor(Color.RED) // set the icon color
                                .build(); // Finally call build
                        likeArticleButton.setImageDrawable(yourDrawable);
                    }
                }, (animationDuration/2));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabRevealLayout.revealMainView();
                    }
                }, animationDuration+100);
            }
        });

        showArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
            }
        });



//
//        TextView more = (TextView)v.findViewById(R.id.more);
//
//        more.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mCatsAdapter != null) {
//                    mCatsAdapter.remove(ParallaxFragment.this);
//                    mCatsAdapter.notifyDataSetChanged();
//                }
//                return true;
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCatsAdapter != null) {
//                    int select = (int) (Math.random() * 4);
//
//                    int[] resD = {R.drawable.temp_image, R.drawable.temp_image02, R.drawable.temp_image03,R.drawable.temp_image01};
//                    String[] resS = {"Nina", "Niju", "Yuki", "Kero"};
//
//                    ParallaxFragment newP = new ParallaxFragment();
//                    Bundle b = new Bundle();
//                    b.putInt("image", resD[select]);
//                    b.putString("name", resS[select]);
//                    newP.setArguments(b);
//                    mCatsAdapter.add(newP);
//                }
//            }
//        });


        return v;
    }


    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
            }
        });
    }

    public void setAdapter(ParallaxAdapter catsAdapter) {
        mCatsAdapter = catsAdapter;
    }
}
