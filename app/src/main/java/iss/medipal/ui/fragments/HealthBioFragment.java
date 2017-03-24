package iss.medipal.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.ui.activities.AddhealthBioActivity;
import iss.medipal.ui.activities.HealthBioActivity;
import iss.medipal.ui.adapters.HealthBioListAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by Mridul on 19-03-2017.
 */

public class HealthBioFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton addHealthBio;
    private TextView mEmptyText;

    private HealthBioListAdapter healthBioListAdapter;
    private List<HealthBio> healthBios;
    private ListView lv;
    private List<String> itemname;
    private onBioChangeListener mCallback;


    public HealthBioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public static HealthBioFragment newInstance() {
        HealthBioFragment fragment = new HealthBioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.activity_health_bio, container, false);
        return fragmentView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.healthbioList);
        addHealthBio= (FloatingActionButton) view.findViewById(R.id.addHealthBio);
        mEmptyText = (TextView) view.findViewById(R.id.tv_empty_bios);
        addHealthBio.setOnClickListener(this);
        setList();
        setListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = ((HealthBioActivity)context);
    }

    private void setList(){
        healthBios = MediPalApplication.getPersonStore().getmPersonalBio().getHealthBios();
        if(!AppHelper.isListEmpty(healthBios)) {
            if (healthBioListAdapter == null) {
                healthBioListAdapter = new HealthBioListAdapter(getContext(), healthBios, this);
                lv.setAdapter(healthBioListAdapter);
            } else {
                healthBioListAdapter.setHealthBio(healthBios);
                lv.setAdapter(healthBioListAdapter);
            }
            if (healthBioListAdapter != null)
                callCallback();
        }
    }

    public void callCallback(){
        if (mCallback != null) {
            mCallback.onBioChangeListener(healthBios.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
        if(healthBioListAdapter != null)
            mEmptyText.setVisibility(healthBioListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addHealthBio:

                Intent intent = new Intent(getActivity(),AddhealthBioActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void setListeners()
    {
        ListView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),AddhealthBioActivity.class);

                Bundle bundle=new Bundle();
                bundle.putParcelable(DBConstants.TABLE_HEALTH_BIO,healthBios.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        lv.setOnItemClickListener(itemClickListener);
    }

    public interface onBioChangeListener{
        void onBioChangeListener(int count);
    }
}
