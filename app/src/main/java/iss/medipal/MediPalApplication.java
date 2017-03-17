package iss.medipal;

import android.app.Application;
import android.content.Context;

import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;

/**
 * Created by junaidramis on 26/2/17.
 */
public class MediPalApplication extends Application {

    private static Context mContext;
    private static PersonStore mPersonStore;

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

    public static PersonStore getPersonStore() {
        if(mPersonStore != null) {
            return mPersonStore;
        } else {
            mPersonStore = new PersonStore(new PersonalBio(), mContext);
            return mPersonStore;
        }
    }
}