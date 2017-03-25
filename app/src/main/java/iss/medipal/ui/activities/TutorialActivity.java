package iss.medipal.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import iss.medipal.R;
import iss.medipal.ui.adapters.TutorialAdapter;
import iss.medipal.ui.customViews.CircleIndicator;
import iss.medipal.ui.utils.TutorialViewHelper;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 25/3/17.
 */
public class TutorialActivity extends BaseActivity {

    static final int NUM_PAGES = 5;

    private Button mSkipButton;
    private Button mDoneButton;
    private ViewPager mPager;
    private CircleIndicator mIndicator;

    private TutorialAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_tutorial);
        setViews();
    }

    private void setViews(){
        mPager = (ViewPager) findViewById(R.id.pager);
        mSkipButton = (Button)findViewById(R.id.skip);
        mDoneButton = (Button)findViewById(R.id.done);
        mIndicator = (CircleIndicator)findViewById(R.id.indicator);
        mSkipButton.setOnClickListener(mEndTutorialClickListener);
        mDoneButton.setOnClickListener(mEndTutorialClickListener);
        mAdapter = new TutorialAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
        mPager.setPageTransformer(true, new CrossfadePageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == NUM_PAGES - 1) {
                    mSkipButton.setVisibility(View.GONE);
                    mDoneButton.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 1) {
                    mSkipButton.setVisibility(View.VISIBLE);
                    mDoneButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    View.OnClickListener mEndTutorialClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(SharedPreferenceManager.isAppInitialLaunch(TutorialActivity.this)) {
                launchActivity(UserProfileActivity.class);
            } else {
                finish();
            }
        }
    };

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.tut_fragment);
            View text_head= page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage01 = page.findViewById(R.id.tut_image);

            if(0 <= position && position < 1){
                TutorialViewHelper.setTranslationX(page,pageWidth * -position);
            }
            if(-1 < position && position < 0){
                TutorialViewHelper.setTranslationX(page,pageWidth * -position);
            }

            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    TutorialViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));

                }

                if (text_head != null) {
                    TutorialViewHelper.setTranslationX(text_head,pageWidth * position);
                    TutorialViewHelper.setAlpha(text_head,1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    TutorialViewHelper.setTranslationX(text_content,pageWidth * position);
                    TutorialViewHelper.setAlpha(text_content,1.0f - Math.abs(position));
                }

                if (welcomeImage01 != null) {
                    TutorialViewHelper.setTranslationX(welcomeImage01,(float)(pageWidth/2 * position));
                    TutorialViewHelper.setAlpha(welcomeImage01,1.0f - Math.abs(position));
                }
            }
        }
    }
}
