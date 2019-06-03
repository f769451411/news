package com.example.administrator.wangyinewsNetwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/3/10.
 */

public class myOpenHelp extends SQLiteOpenHelper {
    public myOpenHelp(Context context) {
        super(context, "heimaNews", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //如果不设置id为主键 那么缓存数据前必须清空数据库！！否则数据会累加！
        db.execSQL("create table news (_id integer  ,title varchar(200),des varchar(300),icon_url varchar(200),news_url varchar(200)," +
                " type integer , comment integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
