package com.jju.edu.wechat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jju.edu.wechat.Login.login_activity;
import com.jju.edu.wechat.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/9.
 */

public class AddFriendActivity extends Activity {
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend_layout);
        username = (EditText) findViewById(R.id.username);
    }

    public void search(View view) {
        String name = username.getText().toString();
        if (name == null || name.equals("")) {
            Toast.makeText(AddFriendActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else {

            http_(name);
        }
        username.setText("");
    }

    public void cancel(View view) {
        finish();
    }

    //服务端连接
    private void http_(final String username) {

        new Thread() {
            @Override
            public void run() {
                Map<String, Object> omap = new HashMap<String, Object>();
                omap.put("name", username);
                String info = HttpUtil.doPost("AddFriend", omap);
                Log.e("///////", info);
                Message message = handler.obtainMessage();
                message.obj = info;
                message.what = 0x111;
                handler.sendMessage(message);

            }
        }.start();
    }

    public void dialog_none(final String name) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFriendActivity.this);
        dialog.setTitle("查找到的用户：");
        dialog.setMessage(name + "\n是否添加为好友？");
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                http_add(login_activity.NAME, name);
                Log.e("^^^^^^^^^^",""+login_activity.NAME);
            }
        });
        dialog.show();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x111) {
                String info = (String) msg.obj;
                if (info.equals("不存在")) {
                    Toast.makeText(AddFriendActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                } else {
                    dialog_none(info);
                }
            } else if (msg.what == 0x112) {
                Toast.makeText(AddFriendActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                if (msg.obj.toString().equals("添加成功")){
                    finish();
                }
            }
        }

        ;
    };

    //服务端连接
    private void http_add(final String username, final String fname) {

        new Thread() {
            @Override
            public void run() {
                Map<String, Object> omap = new HashMap<String, Object>();
                omap.put("name", username);
                omap.put("fname", fname);
                String info = HttpUtil.doPost("AddFriendDo", omap);
                Log.e("///////", info);
                Message message = handler.obtainMessage();
                message.obj = info;
                message.what = 0x112;
                handler.sendMessage(message);
            }
        }.start();
    }
}
