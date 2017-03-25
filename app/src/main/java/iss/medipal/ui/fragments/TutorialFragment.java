package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;

/**
 * Created by junaidramis on 25/3/17.
 */

public class TutorialFragment extends BaseFragment {

    final static String POSITION = "POSITION";

    private int[] mTutImageIds = {
            R.drawable.tut_image_1, R.drawable.tut_image_2,
            R.drawable.tut_image_3, R.drawable.tut_image_4, R.drawable.tut_image_5};

    private ImageView mImage;
    private TextView mHeading;
    private TextView mDescription;

    private int mPos = 0;

    public static TutorialFragment newInstance(int position) {
        TutorialFragment pane = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tutorial, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            mPos = getArguments().getInt(POSITION);
        }
        setUi(view);
    }

    private void setUi(View view){
        mImage = (ImageView) view.findViewById(R.id.tut_image);
        mHeading = (TextView) view.findViewById(R.id.heading);
        mDescription = (TextView) view.findViewById(R.id.content);
        mImage.setImageResource(mTutImageIds[mPos]);
        mHeading.setText(getResources().getStringArray(R.array.tut_heading_array)[mPos]);
        mDescription.setText(getResources().getStringArray(R.array.tut_content_array)[mPos]);
    }
}
