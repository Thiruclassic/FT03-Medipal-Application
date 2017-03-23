package iss.medipal.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import iss.medipal.R;
import iss.medipal.ui.fragments.CategoryFragment;
import iss.medipal.ui.fragments.PriorityFragment;

public class IceActivity extends BaseActivity {

    private int requestCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showCategories();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IceActivity.this, Add_ICE.class);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private static void removeAllFragments(FragmentManager fragmentManager) {
        try {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCategories()
    {
        //AppHelper.addFragment(this, new CategoryFragment());
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.contacts_container)).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //removeAllFragments(fragmentManager);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CategoryFragment category = new CategoryFragment();
        fragmentTransaction.replace(R.id.contacts_container,category);
        fragmentTransaction.commit();
    }

    public void showPrioritySequence()
    {
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.contacts_container)).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //removeAllFragments(fragmentManager);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PriorityFragment priority = new PriorityFragment();
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.contacts_container)).commit();
        fragmentTransaction.replace(R.id.contacts_container,priority);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        switch (itemClicked){
            case R.id.action_category:
                showCategories();
                return true;
            case R.id.action_priority:
                showPrioritySequence();
                return true;
            default:
                showCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}
