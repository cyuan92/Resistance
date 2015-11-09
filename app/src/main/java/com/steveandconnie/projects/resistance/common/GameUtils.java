package com.steveandconnie.projects.resistance.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

/**
 * Created by connieyuan on 9/7/15.
 */
public class GameUtils {

    public static void displayAlertMessage(Context context, String title, String message, String positiveBtnMessage) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(positiveBtnMessage, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static boolean checkStringListValid(List<String> list) {
        for(String item : list) {
            if (item == null || item.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
