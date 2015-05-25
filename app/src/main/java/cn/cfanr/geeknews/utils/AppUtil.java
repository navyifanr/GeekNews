package cn.cfanr.geeknews.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil
{
    /**
     * 根据新闻类型获取上次更新的时间
     *
     * @param newType
     * @return
     */
    public static String getRefreashTime(Context context, int newType)
    {
        String timeStr = PreferenceUtil.readString(context, "NEWS_" + newType);
        if (TextUtils.isEmpty(timeStr))
        {
            return "我好笨，忘记了...";
        }
        return timeStr;
    }


    /**
     * 根据新闻类型设置上次更新的时间
     *
     * @param newType
     * @return
     */
    public static void setRefreashTime(Context context, int newType)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PreferenceUtil.write(context, "NEWS_" + newType,df.format(new Date()));
    }
}
