package iss.medipal.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import iss.medipal.R;


/**
 * Created by junaidramis on 8/3/17.
 */
public class DialogUtility {

    /**
     * Builds a new AlertDialog to display a simple message with OK button.
     *
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static AlertDialog.Builder newMessageDialog(Context context,
                                                       String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.ok_text), null);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    /**
     * Builds a new AlertDialog to display a simple message
     *
     * @param context
     * @param title
     * @param message
     * @param listener
     * @return The new dialog.
     */
    public static AlertDialog.Builder newMessageDialog(
            Context context, String title, String message,
            DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(android.R.string.ok), listener);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }


    /**
     * Builds a new AlertDialog to display a simple message with Yes No Button
     *
     * @param context
     * @param title
     * @param message
     * @param listener
     * @return The new dialog.
     */
    public static AlertDialog.Builder newMessageDialogWIthYesNo(
            Context context, String title, String message,
            DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setNegativeButton(context.getString(android.R.string.no),null);
        builder.setPositiveButton(context.getString(android.R.string.yes), listener);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }





    /**
     * Builds a new AlertDialog to display a simple message and on clicking OK takes back to previous activity
     *
     * @param mActivity
     * @param title
     * @param message
     * @param mActivity
     * @return The new dialog.
     */
    public static AlertDialog.Builder newMessageDialogWithGoBack(
            final AppCompatActivity mActivity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setPositiveButton(mActivity.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mActivity.onBackPressed();
            }
        });
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    /**
     * Builds a new AlertDialog to display a simple message and on clicking OK takes back to previous Fragment
     *
     * @param mActivity
     * @param title
     * @param message
     * @param mActivity
     * @return The new dialog.
     */
    public static AlertDialog.Builder newMessageDialogPopFragment(
            final AppCompatActivity mActivity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setPositiveButton(mActivity.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mActivity.getSupportFragmentManager().popBackStackImmediate();
            }
        });
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    /**
     * Builds a new Yes/No AlertDialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtonMessage
     * @param negativeButtonMessage
     * @param positiveListener
     * @param negativeListener
     * @return The new dialog.
     */
    public static AlertDialog.Builder newYesNoDialog(
            Context context, String title, String message,
            String positiveButtonMessage, String negativeButtonMessage,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setNegativeButton(negativeButtonMessage, negativeListener);
        builder.setPositiveButton(positiveButtonMessage, positiveListener);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
}
