package com.jju.edu.wechat;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by 凌浩 on 2016/10/27.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
    }
}
