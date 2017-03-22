package iss.medipal.ui.fragments;

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

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.ui.activities.AddhealthBioActivity;
import iss.medipal.ui.adapters.HealthBioListAdapter;

public class HealthBioFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton addHealthBio;

    private HealthBioListAdapter healthBioListAdapter;
    private List<HealthBio> healthBios;
    HealthBioDao healthBioDao;
    ListView lv;
    List<String> itemname;

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
    public void onResume() {
        super.onResume();
        setList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.activity_health_bio, container, false);


       // deleteHealthBio = (ImageView)fragmentView.findViewById(R.id.deletebutton);
       // deleteHealthBio.setOnClickListener(this);
        return fragmentView;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.healthbioList);
        addHealthBio=(FloatingActionButton) view.findViewById(R.id.addHealthBio);
        addHealthBio.setOnClickListener(this);
        healthBioDao = HealthBioDaoImpl.newInstance(getActivity());

       /* List<HealthBio> healthBioList = healthBioDao.getAllHealthBio();

        if(itemname==null)
        {
            itemname=new ArrayList<>();
        }
        if (!healthBioList.isEmpty()) {
            for (HealthBio hb : healthBioList) {
                itemname.add(hb.getCondition());
            }
        }
        lv.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.healthbio_list_item, R.id.tvNote, itemname));*/

       setList();
        setListeners();

    }

    private void setList(){
        healthBios = MediPalApplication.getPersonStore().getmPersonalBio().getHealthBios();
        if(healthBios==null) {
            healthBioDao = HealthBioDaoImpl.newInstance(getActivity());
            List<HealthBio> healthBioList = healthBioDao.getAllHealthBio();
            healthBios=healthBioList;
        }

        if(healthBioListAdapter==null)
        {
            healthBioListAdapter = new HealthBioListAdapter(getContext(), healthBios);
            lv.setAdapter(healthBioListAdapter);
        }
        else
        {
            healthBioListAdapter.setHealthBio(healthBios);
            lv.setAdapter(healthBioListAdapter);
        }


    }


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_bio);

        addHealthBio=(Button) findViewById(R.id.addHealthBio);
        addHealthBio.setOnClickListener(this);
    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addHealthBio:
                Intent intent = new Intent(getActivity(),AddhealthBioActivity.class);
                startActivity(intent);
                break;

           //case R.id.deletebutton:
              // healthBioDao.deleteHealthBio();

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
}
