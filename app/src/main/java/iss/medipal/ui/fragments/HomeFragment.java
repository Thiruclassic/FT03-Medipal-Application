package iss.medipal.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.Medicine;
import iss.medipal.model.homeMedicineModels.MedDayModel;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.adapters.DoseViewPagerAdapter;
import iss.medipal.ui.customViews.CircleIndicator;
import iss.medipal.ui.interfaces.HomeInterface;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 19/3/17.
 */

public class HomeFragment extends BaseFragment implements
        OnedayDoseFragment.OnMedicineArrowClickListener,
        HomeInterface {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private RelativeLayout mMedPresentLayout;
    private FrameLayout mInnerFragmentFrame;
    private RelativeLayout mOuterFragmentFrame;
    private RelativeLayout mMedAbsentLayout;
    private LinearLayout mDateLayout;
    private Button mNoMedsBtn;
    private TextView mDateText;
    private ImageButton mLeftArrow;
    private ImageButton mRightArrow;

    private ArrayList<Medicine> mMedicines;
    private DoseContainer mDoseContainer;
    private Calendar mToday;
    private Calendar mCurrentDay;
    private MedInterface mCallback;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseViews(view);
        setListeners();
        setLayout(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MedInterface)context;
        ((MainActivity)context).registerDataUpdateListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        ((MainActivity)getActivity()).unregisterDataUpdateListener();
    }

    @Override
    public void onMedAdded() {
        setLayout(true);
    }

    @Override
    public void onNextMedArrowClicked() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void onPreviousMedArrowClicked() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    private void initialiseViews(View view){
        mDateText = (TextView) view.findViewById(R.id.date_text);
        mDateLayout = (LinearLayout) view.findViewById(R.id.date_layout);
        mNoMedsBtn = (Button) view.findViewById(R.id.go_to_meds_btn);
        mMedAbsentLayout = (RelativeLayout) view.findViewById(R.id.med_absent_layout);
        mOuterFragmentFrame = (RelativeLayout) view.findViewById(R.id.outer_fragment_frame);
        mInnerFragmentFrame = (FrameLayout) view.findViewById(R.id.inner_fragment_frame);
        mMedPresentLayout = (RelativeLayout) view.findViewById(R.id.med_present_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mRightArrow = (ImageButton) view.findViewById(R.id.next_day_button);
        mLeftArrow = (ImageButton) view.findViewById(R.id.prev_day_button);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
    }

    private void setListeners(){
        mRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextDayClick();
            }
        });
        mLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousDayClick();
            }
        });
        mNoMedsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.gotoAddMed();
                }
            }
        });
    }

    private void setLayout(boolean isReload){
        mMedicines = MediPalApplication.getPersonStore().getmPersonalBio().getMedicines();
        if(AppHelper.isListEmpty(mMedicines)){
            mMedAbsentLayout.setVisibility(View.VISIBLE);
            mDateLayout.setVisibility(View.GONE);
            mCircleIndicator.setVisibility(View.GONE);
        } else {
            mDateLayout.setVisibility(View.VISIBLE);
            mCircleIndicator.setVisibility(View.VISIBLE);
            mMedAbsentLayout.setVisibility(View.GONE);
            mDoseContainer = DoseContainer.getInstance(getActivity());
            if(isReload){
                mDoseContainer.reloadData(isReload);
            }
            setTodayCurrent();
            ArrayList<MedDayModel> tempMeds = mDoseContainer.getDosesForDay(mCurrentDay);
            setDateArrowVisibility(mCurrentDay);
            populateUi(tempMeds);
        }
    }

    private void setTodayCurrent() {
        mToday = Calendar.getInstance();
        mCurrentDay = Calendar.getInstance();
        mCurrentDay.set(Calendar.HOUR, 0);
        mCurrentDay.set(Calendar.MINUTE, 0);
        mCurrentDay.set(Calendar.SECOND, 0);
        mCurrentDay.set(Calendar.MILLISECOND, 0);
        Calendar dayCal = mDoseContainer.getCurrentDate();
        updateDisplayDate(dayCal);
    }

    /**
     * populate Ui
     */
    public void populateUi(ArrayList<MedDayModel> tempMeds){
        mViewPager.setAdapter(new DoseViewPagerAdapter(getChildFragmentManager(), tempMeds, mMedicines, mCurrentDay));
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mCircleIndicator.setViewPager(mViewPager);
    }

    /**
     * Update date in view
     */
    private void updateDisplayDate(Calendar dayCal) {
        if (mDoseContainer != null) {
            int month = dayCal.get(Calendar.MONTH);
            int day = dayCal.get(Calendar.DAY_OF_MONTH);
            String text = AppHelper.monthName(month) + " " + day;
            mDateText.setText(text);
            mCurrentDay.set(Calendar.DAY_OF_MONTH, day);
            mCurrentDay.set(Calendar.MONTH, month);
            mCurrentDay.set(Calendar.YEAR, dayCal.get(Calendar.YEAR));
        }
    }

    /**
     * Setup date arrow visibility
     */
    private void setDateArrowVisibility(Calendar cal){
        cal.add(Calendar.DATE, 1);
        int count = mDoseContainer.getDosesCountForDay(cal);
        if (count > 0){
            mRightArrow.setVisibility(View.VISIBLE);
        } else {
            mRightArrow.setVisibility(View.INVISIBLE);
        }
        cal.add(Calendar.DATE, -2);
        count = mDoseContainer.getDosesCountForDay(cal);
        if (count > 0){
            mLeftArrow.setVisibility(View.VISIBLE);
        } else {
            mLeftArrow.setVisibility(View.INVISIBLE);
        }
        cal.add(Calendar.DATE, 1);
    }


    public void onPreviousDayClick() {
        mCurrentDay.add(Calendar.DATE, -1);
        ArrayList<MedDayModel> tempMeds = mDoseContainer.getDosesForDay(mCurrentDay);
        if(!AppHelper.isListEmpty(tempMeds)){
            updateDisplayDate(mCurrentDay);
            setDateArrowVisibility(mCurrentDay);
            populateUi(tempMeds);
        } else {
            mCurrentDay.add(Calendar.DATE, 1);
        }
    }

    public void onNextDayClick() {
        mCurrentDay.add(Calendar.DATE, 1);
        ArrayList<MedDayModel> tempMeds = mDoseContainer.getDosesForDay(mCurrentDay);
        if(!AppHelper.isListEmpty(tempMeds)){
            updateDisplayDate(mCurrentDay);
            setDateArrowVisibility(mCurrentDay);
            populateUi(tempMeds);
        } else {
            mCurrentDay.add(Calendar.DATE, -1);
        }
    }

    public interface MedInterface {
        void gotoAddMed();
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                view.setAlpha(0);
            }
        }
    }
}
