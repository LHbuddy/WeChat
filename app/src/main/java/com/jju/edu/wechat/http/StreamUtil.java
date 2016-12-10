package com.jju.edu.wechat.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by 凌浩 on 2016/11/1.
 */

public class StreamUtil {
    public static String getbyteString(InputStream is) {
        String info = null;
        byte[] buffer = new byte[1024];
        int readcount = -1;
        StringBuilder builder = new StringBuilder();
        try {
            while ((readcount = is.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, readcount));
            }
            info = builder.toString();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getcharString(InputStream is) {
        String info = null;
        StringBuilder builder = new StringBuilder();
        String line = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader in = new InputStreamReader(bis);
        BufferedReader reader = new BufferedReader(in);
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            info = builder.toString();
            reader.close();
            in.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static int BITMAP = 0;
    public static int STRING = 1;
    public static int FILE = 2;

    public static void setoutput_values(int type, InputStream is,
                                        OutputStream os) {
        switch (type) {
            case 0:
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                break;
            case 1:
            case 2:
                input_output(is, os);
                break;
        }
    }

    public static void input_output(InputStream is, OutputStream os) {
        byte[] buffer = new byte[1024];
        int count = -1;
        try {
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
