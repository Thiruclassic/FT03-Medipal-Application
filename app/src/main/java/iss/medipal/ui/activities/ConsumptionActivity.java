package iss.medipal.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.ui.adapters.ConsumptionTabAdapter;
import iss.medipal.ui.fragments.ConsumptionDateFragment;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumptionActivity extends BaseActivity {

    private ImageView mLeftToolbarImageView;
    private ImageView mRightToolbarImageView;
    private TextView mRightToolbarTextView;
    private TextView mToolbarTitle;
    private TabLayout mTab;
    private ViewPager mPager;
    private ConsumptionTabAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumtion);
        initialiseViews();
    }

    private void initialiseViews(){
        mLeftToolbarImageView = (ImageView) findViewById(R.id.toolbar_left_icon);
        mRightToolbarImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        mRightToolbarTextView = (TextView) findViewById(R.id.toolbar_right_text);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mTab = (TabLayout) findViewById(R.id.consumption_tab);
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ConsumptionTabAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
        mToolbarTitle.setText(getString(R.string.consumption));
        mRightToolbarImageView.setVisibility(View.GONE);
        mRightToolbarTextView.setVisibility(View.GONE);
        mLeftToolbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
