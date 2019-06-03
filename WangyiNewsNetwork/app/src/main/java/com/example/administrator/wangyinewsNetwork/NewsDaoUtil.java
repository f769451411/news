package com.example.administrator.wangyinewsNetwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/10.
 */

public class NewsDaoUtil {
    private myOpenHelp OpenHelp;
    public NewsDaoUtil(Context context){
        OpenHelp = new myOpenHelp(context);
    }
    //清空数据库中的数据
    public void deleteNews(){
        SQLiteDatabase db = OpenHelp.getReadableDatabase();
        db.delete("news",null,null);
        db.close();
    }

    //把获取到的数据保存到数据库本地
    public void saveNews(ArrayList<infoBean> news){
        SQLiteDatabase db = OpenHelp.getReadableDatabase();
        for(infoBean list:news){
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id",list.id);
            contentValues.put("title",list.title);
            contentValues.put("des",list.des);
            contentValues.put("icon_url",list.icon_url);
            contentValues.put("news_url",list.news_url);
            contentValues.put("type",list.type);
            contentValues.put("comment",list.comment);
            db.insert("news",null,contentValues);
        }
        db.close();
    }

    //从数据库里获取新闻数据
    public ArrayList<infoBean> getNews(){
        ArrayList<infoBean> news = new ArrayList<>();
        SQLiteDatabase db = OpenHelp.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news",null);
        if (cursor != null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                infoBean mnew = new infoBean();
                mnew.id = cursor.getInt(0);
                mnew.title = cursor.getString(1);
                mnew.des = cursor.getString(2);
                mnew.icon_url = cursor.getString(3);
                mnew.news_url = cursor.getString(4);
                mnew.type = cursor.getInt(5);
                mnew.comment = cursor.getInt(6);

                news.add(mnew);

            }
        }
        cursor.close();
        db.close();


        return news;
    }

}
