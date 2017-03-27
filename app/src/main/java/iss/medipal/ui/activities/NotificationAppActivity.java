package iss.medipal.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import iss.medipal.R;


/**
 * Created by sreekumar on 3/27/2017.
 */

public class NotificationAppActivity extends BaseActivity{
    public static final String TITLE_EXTRA = "title extra";
    public static final String TEXT_VALUES_EXTRA = "text values extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_app);

        Intent createIntent = getIntent();
        String title = createIntent.getStringExtra(TITLE_EXTRA);
        ArrayList<String> textValues=
                createIntent.getStringArrayListExtra(TEXT_VALUES_EXTRA);

        if(title != null)
            setTitle(title);
        if(textValues != null)
            ((ListView)findViewById(R.id.listView)).setAdapter(
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                            textValues));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_list1, menu);
        return true;
    }
}
