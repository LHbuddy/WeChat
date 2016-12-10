package com.jju.edu.wechat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import io.rong.imkit.RongIM;

/**
 * Created by 凌浩 on 2016/10/27.
 */

public class ConversationActivity extends ActionBarActivity {

    private static final String TAG = ConversationActivity.class.getSimpleName();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        getSupportActionBar().setTitle("聊天界面");
        getSupportActionBar().setLogo(R.drawable.rc_ac_audio_file_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.rc_ac_audio_file_icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        return super.onOptionsItemSelected(item);
    }

}
