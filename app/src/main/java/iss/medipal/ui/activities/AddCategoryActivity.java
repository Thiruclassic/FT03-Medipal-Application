package iss.medipal.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.model.Category;
import iss.medipal.model.PersonStore;
import iss.medipal.util.DialogUtility;

/**
 * Created by Thirumal on 22/3/2017.
 */

public class AddCategoryActivity extends AppCompatActivity {
    private AppCompatEditText mCategoryCode;
    private AppCompatEditText mCategoryName;
    private AppCompatEditText mCategoryDescription;
    private Switch mRemindSwitch;
    private Button mSaveCategoryButton;
    private ImageView imageBack;
    private TextView title;
    Category category;
    boolean isEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mCategoryCode=(AppCompatEditText)findViewById(R.id.categoryCodeText);
        mCategoryName=(AppCompatEditText)findViewById(R.id.categoryNameText);
        mCategoryDescription=(AppCompatEditText)findViewById(R.id.categoryDescriptionText);
        mRemindSwitch=(Switch)findViewById(R.id.category_remindSwitch);
        mSaveCategoryButton=(Button)findViewById(R.id.saveCategory);
        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.category_title);
        imageBack.setVisibility(View.VISIBLE);

        Bundle bundle=getIntent().getExtras();
        if(bundle !=null)
        {
            setData(bundle);
            isEdit=Boolean.TRUE;
        }
        setListeners();
    }
    public void setListeners()
    {
        Button.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    List<Category> categories = MediPalApplication.getPersonStore().getCategory();
                    setCategoryData();
                    MediPalApplication.getPersonStore().addUpdateCategory(category, isEdit);
                    Toast.makeText(MediPalApplication.getAppContext(), DBConstants.TABLE_CATEGORY + Constants.SPACE + category.getCategory() + Constants.SPACE + getString(R.string.category_save_pop_up_text), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        ImageView.OnClickListener backClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        imageBack.setOnClickListener(backClickListener);
        mSaveCategoryButton.setOnClickListener(clickListener);

    }
    public void setData(Bundle bundle)
    {
        category=bundle.getParcelable(DBConstants.TABLE_CATEGORY);
        mCategoryCode.setText(category.getCode());
        mCategoryName.setText(category.getCategory());
        mCategoryDescription.setText(category.getDescription());
        mRemindSwitch.setChecked(category.isRemind());
        mCategoryCode.setEnabled(false);
    }
    public void setCategoryData()
    {
        if(category==null) {
            category = new Category();
        }
        category.setCode(String.valueOf(mCategoryCode.getText()));
        category.setCategory(String.valueOf(mCategoryName.getText()));
        category.setDescription(String.valueOf(mCategoryDescription.getText()));
        category.setRemind(mRemindSwitch.isChecked());
    }

    public boolean validate()
    {
        if(TextUtils.isEmpty(mCategoryCode.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Category code").show();
            return false;
        } else if(mCategoryCode.getText().length()!=3){
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Category Code should contain only three Letters").show();
            return false;
        }
        else if(TextUtils.isEmpty(mCategoryName.getText())){
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Category code").show();
            return false;
        }

        return true;

    }
}
