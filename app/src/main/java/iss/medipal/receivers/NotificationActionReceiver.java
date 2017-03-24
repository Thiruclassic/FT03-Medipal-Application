package iss.medipal.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Thirumal on 23/3/2017.
 */

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"I took it clicked",Toast.LENGTH_SHORT).show();
    }
}
