package com.example.administrator.wangyinewsNetwork;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/5.
 */

public class News {
    private Context mContext;
    News(Context c){
        this.mContext = c;
    }
    public  ArrayList<infoBean> getAllNews(){
        ArrayList<infoBean> al = new ArrayList<>();
        //1.网络请求数据
        String mURL = "http://10.13.64.59:8080/itheimaNews/servlet/GetNewsServlet";
        try {
            URL url = new URL(mURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            if(code == 200){
                InputStream is = connection.getInputStream();
                String result = StreamUtils.streamToString(is);
                //2.解析json数据
                JSONObject jsonObject_root = new JSONObject(result);
                JSONArray jsonArray_newss = jsonObject_root.getJSONArray("newss");
                for (int i = 0 ; i < jsonArray_newss.length(); i++){
                    //{是一个JSONObject  [是一个组 需要遍历出结果JSONObject
                    JSONObject news_json = jsonArray_newss.getJSONObject(i); //获取一条新闻的json
                    infoBean NewsBean = new infoBean();   //封装NewsBean
                    NewsBean.id = news_json.getInt("id");
                    NewsBean.type = news_json.getInt("type");
                    NewsBean.comment = news_json.getInt("comment");
                    NewsBean.icon_url = news_json.getString("icon_url");
                    NewsBean.des = news_json.getString("des");
                    NewsBean.news_url = news_json.getString("news_url");
                    NewsBean.title = news_json.getString("title");
                    al.add(NewsBean);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.封装json数据至list
        if(al!=null&&al.size()>0){
            //清空数据库
            new NewsDaoUtil(mContext).deleteNews();
            //保存数据至本地数据库
            new NewsDaoUtil(mContext).saveNews(al);
        }


        return al;
    }
    //获取新闻数据 从数据库中
    public ArrayList<infoBean> getAllNewsForDatabase(){
        return new NewsDaoUtil(mContext).getNews();
    }
}
