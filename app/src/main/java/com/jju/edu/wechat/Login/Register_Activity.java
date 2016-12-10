package com.jju.edu.wechat.Login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jju.edu.wechat.R;
import com.jju.edu.wechat.Utils.CodeUtils;
import com.jju.edu.wechat.Utils.UserUtil;
import com.jju.edu.wechat.http.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/8.
 */

public class Register_Activity extends Activity {

    private String code_text;
    private Bitmap code_img;
    private EditText username, password, img_name;
    private ImageView img;
    private boolean isRegister=false;
    private String token = "";
    private List<UserUtil> list = new ArrayList<UserUtil>();
    private UserUtil util = new UserUtil();
    private String name,psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        img_name = (EditText) findViewById(R.id.img_name);
        img = (ImageView) findViewById(R.id.img);
        get_code();
        img.setImageBitmap(code_img);

    }

    //获取验证码图片及文字
    public void get_code() {
        code_img = CodeUtils.getInstance().createBitmap();
        // code_text = CodeUtils.getInstance().createCode();
        code_text = CodeUtils.code;
        Toast.makeText(Register_Activity.this, "" + code_text, Toast.LENGTH_SHORT).show();
    }

    public void change(View view) {
        get_code();
        img.setImageBitmap(code_img);
    }

    public void dismiss(View view) {
        finish();
    }

    /**
     * 融云连接
     */
    private void rongyun(final String username, final String password) {
        // TODO Auto-generated method stub
        // 获取token
        new Thread() {
            public void run() {

                try {
                    String str = RomGetToken.getTokenStr(username,
                            password, "http://q.qlogo.cn/qqapp/222222/9F8976DFE0079EAF388E82E415D6B3B9/40");
                    if (str != null) {
                        Message message = handler.obtainMessage();
                        message.what = 0x111;
                        message.obj = str;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x111) {
                String info = (String) msg.obj;
                json_(info);
            } else if (msg.what == 0x124) {
                String info = (String) msg.obj;
                if (info.equals("注册失败")||info==null||info.equals("")){
                    Toast.makeText(Register_Activity.this, "注册失败！!", Toast.LENGTH_SHORT).show();
                }else if(info.equals("该用户已存在")){
                    Toast.makeText(Register_Activity.this, "注册失败,该用户已存在", Toast.LENGTH_SHORT).show();
                }else{
                    isRegister=true;
                    if (isRegister){
                        Toast.makeText(Register_Activity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Register_Activity.this, "注册失败！!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        ;
    };

    //服务端连接
    private void http_(final String username, final String password, final String token) {

        new Thread() {
            @Override
            public void run() {
                Map<String, Object> omap = new HashMap<String, Object>();
                omap.put("name", username);
                omap.put("password", password);
                omap.put("token", token);
                String info = HttpUtil.doPost("RegisterServlet", omap);
                Log.e("///////", info);
                Message message = handler.obtainMessage();
                message.obj = info;
                message.what = 0x124;
                handler.sendMessage(message);

            }
        }.start();
    }

    //json解析
    public void json_(String text) {

        try {
            JSONObject object = new JSONObject(text);
            token = object.getString("token");
            http_(name, psd, token);
            util.setToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sure(View view) {
        String username_str = username.getText().toString();
        String password_str = password.getText().toString();
        String img_name_str = img_name.getText().toString();
        if (img_name_str.equals(code_text)) {
            if (username_str == null || username_str.equals("") || password_str == null || password_str.equals("")) {
                Toast.makeText(Register_Activity.this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                name=username_str;
                psd = password_str;
                rongyun(username_str, password_str);

            }
        } else {
            Toast.makeText(Register_Activity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
        }
    }

}
