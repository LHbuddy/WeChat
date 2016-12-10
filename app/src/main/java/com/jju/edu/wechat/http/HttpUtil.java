package com.jju.edu.wechat.http;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/1.
 */

public class HttpUtil {
    //private static String path = "http://192.168.1.104:8080/BookBox/servlet/";
    //private static String path = "http://169.254.98.74:8080/BookBox/servlet/";
    private static String path = "http://10.107.251.85:8080/WeChat/servlet/";

    public static String doGet(String servletName, Map<String, Object> oMap) {
        String info = null;
        try {
            StringBuilder builder = new StringBuilder(path);
            builder.append(servletName);
            builder.append("?");
            for (Map.Entry<String, Object> entry : oMap.entrySet()) {
                String key = entry.getKey();
                Object values = entry.getValue();
                // String str = URLEncoder.encode(String.valueOf(values),
                // "UTF-8");
                Log.e("$$$$$", String.valueOf(values));
                String str = new String(String.valueOf(values));
                builder.append(key).append("=").append(str).append("&");
            }
            builder.deleteCharAt(builder.length() - 1);

            URL url = new URL(builder.toString());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.setReadTimeout(5000);
            huc.setConnectTimeout(5000);
            huc.connect();
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                info = StreamUtil.getbyteString(is);
            }
            huc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String doPost(String servletName, Map<String, Object> oMap) {
        String info = null;
        StringBuilder builder = new StringBuilder();
        String str = "";
        builder.append(path).append(servletName);
        for (Map.Entry<String, Object> entry : oMap.entrySet()) {
            String key = entry.getKey();
            Object values = entry.getValue();
            // String str = URLEncoder.encode(String.valueOf(values),
            // "UTF-8");
            Log.e("$$$$$", String.valueOf(values));

            str += key + "=" + String.valueOf(values) + "&";
        }
        String str_ = str.substring(0, str.length() - 1);

        try {
            URL url = new URL(builder.toString());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.setReadTimeout(5000);
            huc.setConnectTimeout(5000);
            OutputStream os = huc.getOutputStream();
            os.write(str_.getBytes());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                info = StreamUtil.getcharString(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }
}
