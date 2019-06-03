package com.example.administrator.wangyinewsNetwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView mItem;
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ArrayList<infoBean> newsbean = (ArrayList<infoBean>)msg.obj;
            //判断网络请求是否获取了数据
            if(newsbean!=null&&newsbean.size()>0) {
                mItem.setAdapter(new mAdapter(MainActivity.this, newsbean));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItem = (ListView)findViewById(R.id.lv_item);
        //创建一个new对象
        final News data = new News(this);
        //先去数据库中获取缓存数据 展示页面
        ArrayList<infoBean> dbNews = data.getAllNewsForDatabase();
        if (dbNews!=null&&dbNews.size()>0) {
            mItem.setAdapter(new mAdapter(MainActivity.this, dbNews));
        }
        //开启一个线程去网络上请求数据设置给adapter
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取一个Network新闻对象
                ArrayList<infoBean> al  = data.getAllNews();
                Message ms = Message.obtain();
                ms.obj = al;
                handler.sendMessage(ms);
            }
        }).start();
        mItem.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        infoBean ib = (infoBean) parent.getItemAtPosition(position);
        String url = ib.news_url;
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        it.setData(Uri.parse(url));
        startActivity(it);
    }
}
