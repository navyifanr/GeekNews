package cn.cfanr.geeknews.parser.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.cfanr.geeknews.parser.exception.EssaySpiderException;

/**
 * 此类废弃，获取结果会出现部分乱码
 */
public class DataUtil {
    /**
     * 返回该链接地址的html数据
     */
    public static String readHtml(String urlStr) throws EssaySpiderException {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                int len = 0;
                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1) {
                    sb.append(new String(buf, 0, len, "UTF-8"));
                }

                is.close();
            } else {
                throw new EssaySpiderException("访问网络失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}