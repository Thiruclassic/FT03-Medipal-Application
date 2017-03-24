package iss.medipal.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import iss.medipal.R;
import iss.medipal.ui.fragments.AddContactFragment;

public class Add_ICE extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddContactFragment contactFragment = new AddContactFragment();
        fragmentTransaction.replace(R.id.contact_details_container,contactFragment);
        fragmentTransaction.commit();


    }
}
