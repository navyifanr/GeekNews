package cn.cfanr.geeknews.parser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractNum {
    /**
     * 提取字符串中的数字
     * eg: "评论(10)"
     */
    public static int extractNumFromStr(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.parseInt(m.replaceAll("").trim());
    }
}
