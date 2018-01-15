package com.christopherzhz.placetoliveapp.common.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class PopupUtils {

    public static void showToastMessage(Context c, int msgStringId) {
        Toast.makeText(c, msgStringId, Toast.LENGTH_SHORT).show();
    }

    public static void showAlertDialog(Context c, String title, String msg) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
