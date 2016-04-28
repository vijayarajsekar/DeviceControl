package com.pathivu.Utils;

import android.content.Context;
import android.widget.Toast;


public class Logger {


    public static void ShowToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void NoInternet(Context ctx) {
        Toast.makeText(ctx, "No Internet", Toast.LENGTH_SHORT).show();
    }

    public static void Print(String tag, String msg) {
        System.out.println("~ ~ ~ ~ ~ " + tag + "  < = = = > " + msg);
    }
}
