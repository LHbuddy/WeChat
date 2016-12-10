package com.jju.edu.wechat;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jju.edu.wechat.Login.login_activity;
import com.jju.edu.wechat.Utils.FriendUtil;
import com.jju.edu.wechat.adapter.FriendAdapter;
import com.jju.edu.wechat.http.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by 凌浩 on 2016/10/19.
 */

public class TongXunLu_Activity extends Fragment {

    public static List<FriendUtil> list = new ArrayList<FriendUtil>();
    private FriendUtil util;
    private ListView listView;
    public static FriendAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tongxunlu_activity,null);
        listView = (ListView) view.findViewById(R.id.listview);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        util = null;
        list = new ArrayList<FriendUtil>();
        http_(login_activity.NAME);
    }



    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x111) {
                String info = (String) msg.obj;
                json_(info);
            }
        }


    };

    //服务端连接
    private void http_(final String username) {

        new Thread() {
            @Override
            public void run() {
                Map<String, Object> omap = new HashMap<String, Object>();
                omap.put("name", username);
                String info = HttpUtil.doPost("AllFriend", omap);
                Log.e("///////", info);
                Message message = handler.obtainMessage();
                message.obj = info;
                message.what = 0x111;
                handler.sendMessage(message);

            }
        }.start();
    }

    public void json_(String text){
        try {
            JSONArray array = new JSONArray(text);
            for (int i=0;i<array.length();i++){
                util = new FriendUtil();
                //list.add(array.getString(i));
                util.setName(array.getString(i));
                Log.e("***********",""+array.getString(i));
                list.add(util);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new FriendAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        myonclick();
    }

    public void myonclick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RongIM.getInstance().startPrivateChat(getActivity(), list.get(position).getName(), list.get(position).getName());
            }
        });

    }
}
