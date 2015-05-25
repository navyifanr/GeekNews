package cn.cfanr.geeknews.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifanr on 2015/5/22.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static  final String DB_NAME="geeknews_app";

    public DBHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table tb_newsItem(_id integer primary key autoincrement ," +
                " userName text , time text , title text , likeNum integer , commentNum integer ," +
                " hostName text , link text , type integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
