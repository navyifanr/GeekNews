package cn.cfanr.geeknews.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.geeknews.data.NewsItem;

/**
 * Created by ifanr on 2015/5/22.
 */
public class NewsItemDao {
    private DBHelper dbHelper;

    public NewsItemDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void add(NewsItem newsItem) {
        Log.e("add news newstype", newsItem.getType() + "");
        String sql = "insert into tb_newsItem (userName, time, title, likeNum, commentNum, hostName, link, type) values (?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{newsItem.getUserName(), newsItem.getTime(), newsItem.getTitle(), newsItem.getLikeNum(),
                newsItem.getCommentNum(), newsItem.getHostName(), newsItem.getLink(), newsItem.getType()});
        db.close();
    }

    public void deleteAll(int newsType) {
        String sql = "delete from tb_newsItem where type=?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{newsType});
        db.close();
    }

    public void add(List<NewsItem> newsItems) {
        for (NewsItem newsItem : newsItems) {
            add(newsItem);
        }
    }

    public List<NewsItem> list(int newsType, int currentPage) {
        Log.e("newsType", newsType + "");
        Log.e("currentPage", currentPage + "");
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            int offset = 10 * (currentPage - 1);
            String sql = "select userName, time, title, likeNum, commentNum, hostName, link, type from tb_newsItem " +
                    "where type= ? limit ?,?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(sql, new String[]{newsType + "", offset + "", (offset + 10) + ""});

            NewsItem newsItem = null;

            while (c.moveToNext()) {
                newsItem = new NewsItem();
                String userName = c.getString(0);
                String time = c.getString(1);
                String title = c.getString(2);
                int likeNum = c.getInt(3);
                int commentNum = c.getInt(4);
                String hostName = c.getString(5);
                String link = c.getString(6);
                int type = c.getInt(7);

                newsItem.setUserName(userName);
                newsItem.setTime(time);
                newsItem.setTitle(title);
                newsItem.setLikeNum(likeNum);
                newsItem.setCommentNum(commentNum);
                newsItem.setHostName(hostName);
                newsItem.setLink(link);
                newsItem.setType(type);

                newsItems.add(newsItem);
            }
            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsItems;
    }
}
