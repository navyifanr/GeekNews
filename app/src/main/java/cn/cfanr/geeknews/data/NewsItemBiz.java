package cn.cfanr.geeknews.data;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.cfanr.geeknews.parser.exception.EasySpliderException;
import cn.cfanr.geeknews.parser.utils.ExtractNum;
import cn.cfanr.geeknews.parser.utils.ExtractTime;
import cn.cfanr.geeknews.parser.utils.URLUtil;


public class NewsItemBiz {
    public List<NewsItem> getNewsItems(int newsType, int currentPage)
            throws EasySpliderException {
        String urlStr = URLUtil.generateUrl(newsType, currentPage);

//        String htmlStr = DataUtil.readHtml(urlStr);

        List<NewsItem> newsItems = new ArrayList<>();
        NewsItem newsItem = null;

        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(urlStr).openStream(), "UTF-8", "http://geek.csdn.net/");     //注意设置编码，否则会出现乱码
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements list = doc.getElementsByClass("list").get(0).getElementsByTag("li");  //注意li标签要局限在主体内容中
        for (int i = 0; i < list.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setType(newsType);
            //获取单条信息的内容
            Element item_ele = list.get(i);
			/*
			 * 获取点赞数
			 */
            Element like_ele=item_ele.getElementsByClass("up_down").get(0);
            int likeNum=Integer.parseInt(like_ele.text());
            newsItem.setLikeNum(likeNum);
            //主体内容
            Element content_ele=item_ele.getElementsByClass("content_info").get(0);
			/*
			 * 获取标题、对应链接、主机名
			 * 注意有些news是站内的，不同于其他，要额外处理
			 */
            Element title_ele=content_ele.getElementsByTag("h4").get(0);
            Element title_a_ele=title_ele.child(0);
            String title=title_a_ele.text();
            String link=title_a_ele.attr("href");
            String hostName=title_ele.child(1).text();
            if(TextUtils.isEmpty(title)){
                title_a_ele=title_ele.child(1);
                title=title_a_ele.text();
                link="http://geek.csdn.net"+title_a_ele.attr("href");
                hostName="(geek.csdn.net)";
            }
            newsItem.setTitle(title);
            newsItem.setLink(link);
            newsItem.setHostName(hostName.substring(1, hostName.length()-1));
			/*
			 * 获取发布人名字
			 */
            String userName=content_ele.getElementsByTag("span").get(1).child(0).text();
            newsItem.setUserName(userName);
			/*
			 * 获取发布时间
			 * 时间有两种格式，一种是xxx小时(分钟)前，一种是具体时间
			 */
            String time=content_ele.getElementsByTag("span").get(1).text();
            time=ExtractTime.extractTimeFromStr(time);
            newsItem.setTime(time);
			/*
			 * 获取评论数
			 */
            int commentNum=ExtractNum.extractNumFromStr(content_ele.getElementsByClass("comment").text());
            newsItem.setCommentNum(commentNum);
            //添加到news列表
            newsItems.add(newsItem);
            Log.e("newsItem",newsItem.toString());
        }
        return newsItems;
    }
}