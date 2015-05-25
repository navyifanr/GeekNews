package cn.cfanr.geeknews.parser.utils;

public class URLUtil {
    public static final String NEWS_LIST_URL_HOTTEST= "http://geek.csdn.net/hot";
    public static final String NEWS_LIST_URL_NEWEST = "http://geek.csdn.net/new";

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
            default:
                urlStr = NEWS_LIST_URL_HOTTEST;
                break;
        }
        urlStr += "/" + currentPage;
        return urlStr;
    }

}