package cn.cfanr.geeknews.parser.utils;

public class URLUtil {
    public static final String NEWS_LIST_URL_HOTTEST= "http://geek.csdn.net/hotest";
    public static final String NEWS_LIST_URL_NEWEST = "http://geek.csdn.net/newest";
    public static final String NEWS_LIST_URL_ANDROID="http://geek.csdn.net/forum/65";
    public static final String NEWS_LIST_URL_IOS="http://geek.csdn.net/forum/66";
    public static final String NEWS_LIST_URL_FRONT_END="http://geek.csdn.net/forum/47";

    /**
     * 根据文章类型，和当前页码生成url
     *
     * @param newsType
     * @param currentPage
     * @return
     */
    public static String generateUrl(int newsType, int currentPage) {
        currentPage = currentPage > 0 ? currentPage : 1;
        String urlStr = "";
        switch (newsType) {
            case Constants.NEWS_TYPE_HOTTEST:
                urlStr = NEWS_LIST_URL_HOTTEST;
                break;
            case Constants.NEWS_TYPE_NEWEST:
                urlStr = NEWS_LIST_URL_NEWEST;
                break;
            case Constants.NEWS_TYPE_ANDROID:
                urlStr=NEWS_LIST_URL_ANDROID;
                break;
            case Constants.NEWS_TYPE_IOS:
                urlStr=NEWS_LIST_URL_IOS;
                break;
            case Constants.NEWS_TYPE_FRONT_END:
                urlStr=NEWS_LIST_URL_FRONT_END;
                break;
            default:
                urlStr = NEWS_LIST_URL_HOTTEST;
                break;
        }
//        urlStr += "/" + currentPage;
        return urlStr;
    }

}