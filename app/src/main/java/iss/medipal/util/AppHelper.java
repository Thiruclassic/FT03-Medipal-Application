package iss.medipal.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iss.medipal.R;

/**
 * Created by junaidramis on 8/3/17.
 */
public class AppHelper {
    /**
     * Add Fragment to main container view.
     * Fragment won't be added to back stack.
     *
     * @param context
     * @param fragment
     */
    public static void addFragment(Context context, Fragment fragment) {
        addFragment(context, fragment, false);
    }

    /**
     * Add Fragment to main container view.
     *
     * @param context
     * @param fragment
     * @param addToBackStack
     */
    public static void addFragment(Context context, Fragment fragment,
                                   boolean addToBackStack) {
        getFragmentTransaction(context, fragment, "", fragment.getArguments(), addToBackStack).commit();
    }

    /**
     * Add Fragment to main container view.
     * Fragment won't be added to back stack.
     */
    public static void addFragment(Context context, Fragment fragment, boolean addToBackStack,String tag) {
        getFragmentTransaction(context, fragment, tag, fragment.getArguments(), addToBackStack).commit();
    }

    /**
     * Add Fragment to main container view.
     * Fragment won't be added to back stack.
     */
    public static void addAboveFragment(Context context, Fragment fragment, boolean addToBackStack,String tag,
                                        int enterAnim, int exitAnime) {
        getAddFragmentTransaction(context, fragment, tag, fragment.getArguments(), addToBackStack,
                enterAnim, exitAnime)
                .commit();
        ;
    }

    /**
     * Returns number of fragments in backstack
     */
    public static int getBackFragmentCount(Context context) {
        return ((FragmentActivity) context).getSupportFragmentManager().getBackStackEntryCount();
    }

    /**
     * Return FragmentTransaction object for common container
     *
     * @param fragment
     * @param context
     * @param tag
     * @param bundle
     * @param addToBackStack
     * @return
     */
    public static FragmentTransaction getFragmentTransaction(Context context, Fragment fragment,
                                                             String tag, Bundle bundle, boolean addToBackStack) {
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container, fragment, tag);
        fragment.setArguments(bundle);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        return ft;
    }

    /**
     * Return FragmentTransaction object for common container
     *
     * @param fragment
     * @param context
     * @param tag
     * @param bundle
     * @param addToBackStack
     * @return
     */
    public static FragmentTransaction getAddFragmentTransaction(Context context, Fragment fragment,
                                                                String tag, Bundle bundle, boolean addToBackStack,
                                                                int enterAnim, int exitAnime) {
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager()
                .beginTransaction();
        ft.setCustomAnimations(enterAnim, exitAnime, enterAnim, exitAnime);
        ft.add(R.id.container, fragment, tag);
        fragment.setArguments(bundle);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        return ft;
    }

    /**
     * Return current app theme primary color
     *
     * @param context
     * @return
     */
    public static int getAppPrimaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }


    /**
     * Return whether list is empty
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
