package com.jju.edu.wechat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.jju.edu.wechat.Login.login_activity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //String Token = "96JXIsbzShBCCTsgozJKgxeRyr55SXiU87pxVgemovLgSHiiPVOPDIZQ+eGbS3woWx7MbhuvKSGG8zaI21wtfw==";//test
        String Token = login_activity.TOKEN;//test
        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
            }
            @Override
            public void onSuccess(String userId) {
                Log.e("MainActivity", "——onSuccess—-" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "——onError—-" + errorCode);
            }
        });

       // RongIM.getInstance().startPrivateChat(MainActivity.this, "001", "000");

        startActivity(new Intent(MainActivity.this,Pager_Activity.class));
        finish();
        //启动会话列表界面
        //if (RongIM.getInstance() != null)
          // RongIM.getInstance().startConversationList(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(MainActivity.this, "001", "000");
        }
        return super.onOptionsItemSelected(item);
    }
}

