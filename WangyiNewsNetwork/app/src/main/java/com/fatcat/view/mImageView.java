package com.fatcat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2019/3/11.
 */

public class mImageView extends android.support.v7.widget.AppCompatImageView {
    public mImageView(Context context) {
        super(context);
    }

    public mImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public mImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap img  = (Bitmap)msg.obj;
            mImageView.this.setImageBitmap(img);
        }
    };

    public void setImageUrl(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection)mUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5*1000);
                    int code = connection.getResponseCode();
                    if (code == 200){
                        InputStream is = connection.getInputStream();
                        Bitmap img = BitmapFactory.decodeStream(is);
                        Message msg = Message.obtain();
                        msg.obj = img;
                        handler.sendMessage(msg);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
