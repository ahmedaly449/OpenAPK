package com.javiersantos.mlmanager.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.javiersantos.mlmanager.AppInfo;
import com.javiersantos.mlmanager.R;
import com.javiersantos.mlmanager.utils.UtilsApp;
import com.javiersantos.mlmanager.utils.UtilsDialog;

public class ExtractFileInBackground extends AsyncTask<Void, String, Boolean> {
    private Context context;
    private Activity activity;
    private MaterialDialog dialog;
    private AppInfo appInfo;

    public ExtractFileInBackground(Activity activity, Context context, MaterialDialog dialog, AppInfo appInfo) {
        this.activity = activity;
        this.context = context;
        this.dialog = dialog;
        this.appInfo = appInfo;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean status = UtilsApp.copyFile(appInfo);
        return status;
    }

    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);
        dialog.dismiss();
        if (status) {
            UtilsDialog.showExtractedSnackbar(activity, String.format(context.getResources().getString(R.string.dialog_saved_description), appInfo.getName(), UtilsApp.getAPKFilename(appInfo)), context.getResources().getString(R.string.button_undo), UtilsApp.getOutputFilename(appInfo)).show();
        } else {
            UtilsDialog.showTitleContent(context, context.getResources().getString(R.string.dialog_extract_fail), context.getResources().getString(R.string.dialog_extract_fail_description));
        }
    }
}