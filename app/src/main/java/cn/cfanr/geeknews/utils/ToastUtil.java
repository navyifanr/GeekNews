package cn.cfanr.geeknews.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
