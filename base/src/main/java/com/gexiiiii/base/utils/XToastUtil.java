package com.gexiiiii.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:01
 * description :
 */
public class XToastUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showShort(Context context, final String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, final String msg) {
        showMessage(context, msg, Toast.LENGTH_LONG);
    }

    private static void showMessage(final Context context, final String msg, final int len) {
        if (msg != null && !msg.equals("")) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, len).show();
                }
            });
        }
    }
}
