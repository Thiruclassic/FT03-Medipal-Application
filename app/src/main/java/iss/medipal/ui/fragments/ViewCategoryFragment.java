package iss.medipal.ui.fragments;


import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.DBConstants;
import iss.medipal.model.Category;
import iss.medipal.model.HealthBio;
import iss.medipal.ui.activities.AddCategoryActivity;
import iss.medipal.ui.activities.AddhealthBioActivity;
import iss.medipal.ui.adapters.CategoryListAdapter;
import iss.medipal.util.AppHelper;

/**
 * Created by Thirumal on 22/3/2017.
 */

public class ViewCategoryFragment extends Fragment {

    private ListView categoryListView;
    private FloatingActionButton addCategoryButton;
    private CategoryListAdapter adapter;

    public static ViewCategoryFragment newInstance() {
        ViewCategoryFragment fragment = new ViewCategoryFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_category_view, container, false);
        return fragmentView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        addCategoryButton=(FloatingActionButton) getActivity().findViewById(R.id.addCategoryButton);
        categoryListView=(ListView)getActivity().findViewById(R.id.categoryList);
        setCategoryListView();
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        setCategoryListView();
    }

    public void setCategoryListView()
    {
        Log.d("Category List", String.valueOf(MediPalApplication.getPersonStore().getCategory()));
        List<Category> categoryList=MediPalApplication.getPersonStore().getCategory();
        if(!AppHelper.isListEmpty(categoryList)) {
            if (adapter == null) {
                adapter = new CategoryListAdapter(getContext(), categoryList);
            } else {
                adapter.setCategories(categoryList);
            }
            categoryListView.setAdapter(adapter);
        }
    }

    public void setListeners()
    {
        ListView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),AddCategoryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable(DBConstants.TABLE_CATEGORY,(Category)adapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        Button.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddCategoryActivity.class);
                startActivity(intent);
            }
        };
        addCategoryButton.setOnClickListener(clickListener);
        categoryListView.setOnItemClickListener(itemClickListener);
    }


}
