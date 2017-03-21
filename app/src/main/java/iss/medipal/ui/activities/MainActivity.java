package iss.medipal.ui.activities;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import iss.medipal.R;
import iss.medipal.ui.adapters.MainPagerAdapter;
import iss.medipal.ui.customViews.CommonTabLayout;
import iss.medipal.ui.entities.TabEntity;
import iss.medipal.ui.fragments.HomeFragment;
import iss.medipal.ui.fragments.ViewMedicineFragment;
import iss.medipal.ui.interfaces.CustomTabEntity;
import iss.medipal.ui.interfaces.HomeInterface;
import iss.medipal.ui.interfaces.OnTabSelectListener;
import iss.medipal.util.AppHelper;

public class MainActivity extends BaseActivity implements HomeFragment.MedInterface,
        ViewMedicineFragment.HomeInterface {

    private String[] mTabItems;

    private ViewPager mPager;
    private CommonTabLayout mTabLayout;
    private TextView mToolbarTitle;
    private ArrayList<CustomTabEntity> mTabEntities;
    private int[] mIconselectIds = {
            R.mipmap.home, R.mipmap.pills,
            R.mipmap.calendar_g_w, R.mipmap.bell, R.mipmap.more};
    private int[] mIconUnselectIds = {
            R.mipmap.home_white, R.mipmap.pills_white,
            R.mipmap.calendar_g_b, R.mipmap.bell_white, R.mipmap.more_white};
   private HomeInterface mHomeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        setPager();
        setTabs();
    }

    @Override
    public void gotoAddMed() {
        mTabLayout.setCurrentTab(1);
        mPager.setCurrentItem(1);
    }

    @Override
    public void onMedAdded() {
        mHomeInterface.onMedAdded();
    }

    private void initialise(){
        mTabItems = getResources().getStringArray(R.array.tab_items);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (CommonTabLayout) findViewById(R.id.tab_layout);
    }

    public synchronized void registerDataUpdateListener(HomeInterface listener) {
        mHomeInterface = listener;
    }

    public synchronized void unregisterDataUpdateListener() {
        mHomeInterface = null;
    }

    private void setPager(){
        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mTabItems));
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
                mToolbarTitle.setText(mTabItems[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setCurrentItem(0);
        mToolbarTitle.setText(mTabItems[0]);
    }

    private void setTabs(){
        mTabEntities = new ArrayList<>();
        for(int i = 0; i < mTabItems.length; i++){
            mTabEntities.add(new TabEntity(mTabItems[i], mIconselectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPager.setCurrentItem(position);
                mToolbarTitle.setText(mTabItems[position]);
                AppHelper.hideKeyboard(MainActivity.this);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
