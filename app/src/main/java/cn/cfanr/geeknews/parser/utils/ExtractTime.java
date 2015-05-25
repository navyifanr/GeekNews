package cn.cfanr.geeknews.parser.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTime {
    /*
     * 提取字符串中的时间，字符串同时满足以下格式：
     * "文章由xxx 于2015-05-01 21:35分享"
        "文章由xxx 于16小时前分享"
        "文章由xxx 于15分钟前分享"
        或者简单粗暴的方式：(缺点:不能处理"前"字)
        int start=str.lastIndexOf("于");
        int end=str.lastIndexOf("分享");
        String result=str.substring(start+1,end);
     */
    public static String extractTimeFromStr(String str){
        String result="";
        String regEx="(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})|(\\d{1,2}小时)|(\\d{1,2}分钟)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if(m.find()){
            result=m.group();
        }
        //修改时间格式    5 hours ago   30 min ago
        if(result.lastIndexOf("小时")!=-1){
            int h=ExtractNum.extractNumFromStr(result);
            result=h+" hours ago";
            if(h==1) result=h+" hour ago";
        }else if(result.lastIndexOf("分钟")!=-1){
            result=ExtractNum.extractNumFromStr(result)+" min ago";
        }else{
            int day=differDays(result.split(" ")[0]);
            result=day+" days ago";
            if(day==1) result=day+" day ago";
        }
        return result;
    }
    /*
     * 比较两个日期相差天数
     */
    public static int differDays(String str){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today=dateFormat.format(date)+" 00:00:00";
        String past=str+" 00:00:00";
        Date date1 = getDateFromString(today);
        Date date2 = getDateFromString(past);
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
        return days;
    }
    /*
     * 将字符串 "yyyy-MM-dd HH:mm:ss" 转化为Date
     */
    public static Date getDateFromString(String s) {
        Date returnDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            returnDate = sdf.parse(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
