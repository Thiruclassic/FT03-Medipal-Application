package iss.medipal.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.ui.fragments.HealthBioFragment;
import iss.medipal.ui.fragments.ViewCategoryFragment;
import iss.medipal.util.AppHelper;

public class ViewCategoryActivity extends AppCompatActivity {

    private ImageView imageBack;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        addCategoryFragment(ViewCategoryFragment.newInstance());
        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.category_title);
        imageBack.setVisibility(View.VISIBLE);
        setListeners();
    }

    public void addCategoryFragment(Fragment fragment)
    {
        FragmentTransaction ft = ((FragmentActivity) this).getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.categoryContainer, fragment).commit();
    }

    public void setListeners()
    {
        ImageView.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        imageBack.setOnClickListener(clickListener);
    }



}
