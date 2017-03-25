package iss.medipal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sreek on 3/26/2017.
 */

public class AppNotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"Noted clicked",Toast.LENGTH_SHORT).show();
    }
}
