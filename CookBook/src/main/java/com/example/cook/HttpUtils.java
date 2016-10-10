package com.example.cook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guojian on 10/9/16.
 */
public class HttpUtils {



    public static void setPicBitmap(final ImageView ivPic, final String pic_url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(pic_url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivPic.setImageBitmap(bitmap);
                    is.close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }
}
