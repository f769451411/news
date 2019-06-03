package com.example.administrator.wangyinewsNetwork;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/5.
 */

public class mAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<infoBean> arr;
    mAdapter(Context c, ArrayList<infoBean> arr){
        this.mContext = c;
        this.arr = arr;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null){
            view = convertView;
        }else {
            view = View.inflate(mContext,R.layout.activity_item,null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_res = (TextView) view.findViewById(R.id.tv_res);
            com.fatcat.view.mImageView img = (com.fatcat.view.mImageView) view.findViewById(R.id.img_item);
            infoBean data = arr.get(position);
            tv_title.setText(data.title);
            tv_res.setText(data.des);
            //url地址转换成图片路径  利用开源项目SmartImageUrl 也可以用自己写的自定义view
            img.setImageUrl(data.icon_url);

        }
        return view;
    }
}
