package cn.cfanr.geeknews.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil
{
	public static void toast(Context context , String msg)
	{
		Toast.makeText(context, msg, 1).show();
	}
}
