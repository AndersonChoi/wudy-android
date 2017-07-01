package com.wudy.newsmakers.pager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.wudy.newsmakers.MainActivity;
import com.wudy.newsmakers.R;
import com.wudy.newsmakers.opengraph.MetaElement;
import com.wudy.newsmakers.opengraph.OpenGraph;
import com.wudy.newsmakers.opengraph.OpenGraphVO;

public class ParallaxFragment extends Fragment {

    private ParallaxAdapter mCatsAdapter;
    private OpenGraph openGraphParser;
    private OpenGraphVO openGraphVO;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.e("parserTest","parse start");

        View v = inflater.inflate(R.layout.demo_fragment_parallax, container, false);
        final ImageView pagerImageView = (ImageView) v.findViewById(R.id.main_background_image);
        final TextView pagerTitletext = (TextView)v.findViewById(R.id.main_title_text);
        final TextView pagerDescriptiontext = (TextView)v.findViewById(R.id.main_description_text);
        final String tempUrl = getArguments().getString("mainPagerUrl");

        FABRevealLayout fabRevealLayout = (FABRevealLayout) v.findViewById(R.id.fab_reveal_layout);
        configureFABReveal(fabRevealLayout);

        mHandler = new Handler();
        openGraphVO = new OpenGraphVO();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TmonMonsori.ttf.ttf");
        pagerTitletext.setTypeface(font);

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
                    openGraphVO.setOgTitle(Html.fromHtml(openGraphParser.getContent("title")).toString() );
                    openGraphVO.setOgDescription(Html.fromHtml(openGraphParser.getContent("description")).toString());
                    openGraphVO.setOgImage(openGraphParser.getContent("image"));
                    openGraphVO.setOgUrl(openGraphParser.getContent("url"));
                    mHandler.post(mRunnable);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("thread","thraed :"+e);
                }


            }
        }.start();

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
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {}

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                prepareBackTransition(fabRevealLayout);
            }
        });
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 2000);
    }

    public void setAdapter(ParallaxAdapter catsAdapter) {
        mCatsAdapter = catsAdapter;
    }
}
