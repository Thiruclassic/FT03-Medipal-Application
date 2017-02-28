package iss.medipal;

import android.app.Application;
import android.content.Context;

/**
 * Created by junaidramis on 26/2/17.
 */
public class MediPalApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * Return App context
     *
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

}