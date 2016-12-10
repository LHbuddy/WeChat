package com.jju.edu.wechat.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.jju.edu.wechat.MainActivity;
import com.jju.edu.wechat.R;
import com.jju.edu.wechat.http.Http_volley_util;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/8.
 */

public class login_activity extends Activity{

    private EditText username,password;
    public static String NAME,PASSWORD,ID,TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Http_volley_util.getRequstQueue(this);
    }
    //登录按钮监听
    public void login(View view){
        String username_str = username.getText().toString();
        String password_str = password.getText().toString();
        if (username_str==null||username_str.equals("")||password_str==null||password_str.equals("")){
            Toast.makeText(login_activity.this,"账号密码不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(login_activity.this,username_str+"****"+password_str,Toast.LENGTH_SHORT).show();
            http_(username_str,password_str);
        }
        username.setText("");
        password.setText("");
    }
    //注册页面跳转
    public void register(View view){
        startActivity(new Intent(login_activity.this,Register_Activity.class));
        username.setText("");
        password.setText("");
    }
    //post方式获取用户json数据
    public void http_(String username,String password){

        Map<String, String> oMap = new HashMap<String, String>();
        oMap.put("name", username);
        oMap.put("password", password);
        Http_volley_util.doPost("LoginServlet", oMap);
        Http_volley_util.setResult_info(new Http_volley_util.Result_info() {
            @Override
            public void result(String info) {
                //Toast.makeText(login_activity.this, info, Toast.LENGTH_SHORT).show();
                json_(info);
                if (!info.equals("登录失败")){
                    startActivity(new Intent(login_activity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void result_bitmap(Bitmap bitmap) {

            }
        });
    }
    //json数据解析
    public void json_(String text){
        try {
            JSONObject object = new JSONObject(text);
            ID = object.getString("userId");
            NAME = object.getString("userName");
            PASSWORD = object.getString("userPsd");
            TOKEN = object.getString("userToken");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
