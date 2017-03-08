package iss.medipal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by junaidramis on 8/3/17.
 * Class for managing SharedPreference data.
 * */
public class SharedPreferenceManager {


    private static final String SHARED_PREFERENCE_KEY = "SHARED_PREFERENCE_KEY";
    public static final String APP_LAUNCH_STATUS = "APP_LAUNCH_STATUS";

    /**
     * Save object in shared preference
     *
     */
    public static final void setPreferences(String key, String value,
                                            Context context) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_KEY,
                    0).edit();
            editor.putString(key.toString(), value);
            editor.commit();
        } catch (Exception err) {
            Log.d("ERROR Prefs(set)", err.getMessage());
        }
    }



    /**
     * Get saved object from shared preference
     *
     * @param key
     * @param context
     * @return
     */
    public static final String getPreferences(String key,
                                              Context context) {
        String value;
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    SHARED_PREFERENCE_KEY, 0);
            value = prefs.getString(key.toString(), "");
        } catch (Exception err) {
            Log.d("ERROR Prefs(get)", err.getMessage());
            value = null;
        }
        return value;
    }

    /**
     * Clear the saved data
     *
     * @param key
     * @param context
     */

    public static void clearPreferences(String key, Context context) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(SharedPreferenceManager.SHARED_PREFERENCE_KEY,
                    0).edit();
            editor.remove(key.toString()).commit();
        } catch (Exception err) {
            Log.d("ERROR Prefs", err.getMessage());
        }
    }
    /**
     * Get saved int  from shared preference
     *
     * @param key
     * @param context
     * @return
     */
    public static final int getIntPreferences(String key, Context context) {
        int theObject;
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    SHARED_PREFERENCE_KEY, 0);
            theObject = prefs.getInt(key.toString(), 0);
        } catch (Exception err) {
            Log.d("ERROR Prefs(get)", err.getMessage());
            theObject = 0;
        }
        return theObject;
    }

    /**
     * Save int in shared preference
     *
     * @param key
     * @param obj
     * @param context
     */
    public static final void setIntPreferences(String key, int obj,
                                               Context context) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_KEY,
                    0).edit();
            editor.putInt(key.toString(), obj);
            editor.commit();
        } catch (Exception err) {
            Log.d("ERROR Prefs(set)", err.getMessage());
        }
    }

    /**
     * Set App Launch Status
     *
     * @param status status
     */
    public static void setAppLaunchStatus(Context context, boolean status) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFERENCE_KEY, 0);
        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(APP_LAUNCH_STATUS, status);
            editor.commit();
        }
    }

    /**
     * Get App Launch Status
     *
     * @return appLaunchStatus
     */
    public static boolean isAppInitialLaunch(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFERENCE_KEY, 0);
        boolean appLaunchStatus = true;
        if (prefs != null) {
            appLaunchStatus = prefs.getBoolean(APP_LAUNCH_STATUS, true);
        }
        return appLaunchStatus;
    }

}

