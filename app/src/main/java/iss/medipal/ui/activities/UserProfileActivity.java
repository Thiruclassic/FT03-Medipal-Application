package iss.medipal.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.ui.fragments.UserProfileEditFragment;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */

public class UserProfileActivity extends BaseActivity {

    private ImageView mLeftToolbarImageView;
    private ImageView mRightToolbarImageView;
    private TextView mRightToolbarTextView;
    private TextView mToolbarTitle;

    private EditClickListener mEditClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        AppHelper.addFragment(this, UserProfileEditFragment.newInstance(SharedPreferenceManager.isAppInitialLaunch(this)));
        initialiseViews();
    }

    private void initialiseViews(){
        mLeftToolbarImageView = (ImageView) findViewById(R.id.toolbar_left_icon);
        mRightToolbarImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        mRightToolbarTextView = (TextView) findViewById(R.id.toolbar_right_text);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mLeftToolbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRightToolbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditClickListener != null){
                    mRightToolbarImageView.setVisibility(View.GONE);
                    mRightToolbarTextView.setVisibility(View.VISIBLE);
                    mEditClickListener.onEditClicked();
                }
            }
        });
        mRightToolbarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditClickListener != null){
                    mRightToolbarTextView.setVisibility(View.GONE);
                    mRightToolbarImageView.setVisibility(View.VISIBLE);
                    mEditClickListener.onSaveClicked();
                }
            }
        });
    }

    public void setToolbarTitle(String title){
        mToolbarTitle.setText(title);
    }

    public void setToolbar(boolean isEdit){
        if(isEdit){
            setToolbarTitle(getString(R.string.enter_details_text));
            mLeftToolbarImageView.setVisibility(View.GONE);
            mRightToolbarImageView.setVisibility(View.GONE);
            mRightToolbarTextView.setVisibility(View.GONE);
        } else {
            setToolbarTitle(getString(R.string.profile_text));
            mLeftToolbarImageView.setVisibility(View.VISIBLE);
            mRightToolbarImageView.setVisibility(View.VISIBLE);
            mRightToolbarTextView.setVisibility(View.GONE);
        }
    }

    public void setmEditClickListener(EditClickListener mEditClickListener) {
        this.mEditClickListener = mEditClickListener;
    }

    public interface EditClickListener {
        void onEditClicked();
        void onSaveClicked();
    }
}
