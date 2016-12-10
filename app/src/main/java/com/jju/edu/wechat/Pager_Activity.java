package com.jju.edu.wechat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by 凌浩 on 2016/11/5.
 */

public class Pager_Activity extends FragmentActivity {

    private TextView middle;
    private ImageView right, left;
    private LinearLayout tv_1, tv_2, tv_3, tv_4;
    private TextView weixin_txt, tongxunlu_txt, faxian_txt, wo_txt;
    private ImageView weixin_img, tongxunlu_img, faxian_img, wo_img;
    private ViewPager pager;
    private List<Fragment> oFragments = new ArrayList<Fragment>();
    //private List<Activity> activities = new ArrayList<Activity>();
    private PopupWindow popupWindow;
    private LinearLayout faqiqunliao, tianjiapengyou, saoyisao, youxiang;
    private LinearLayout title;
    /**
     * 会话列表的fragment
     */
    private Fragment mConversationFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);

        init();

        //设置列表页面显示在fragment中
        Fragment fragment = new WeiXin_Activity();
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = ConversationListFragment
                    .getInstance();
            Uri uri = Uri
                    .parse("rong://" + getApplicationInfo().packageName)
                    .buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(
                            Conversation.ConversationType.PRIVATE.getName(),
                            "false") // 设置私聊会话是否聚合显示
                    .appendQueryParameter(
                            Conversation.ConversationType.GROUP.getName(),
                            "true")// 群组
                    .appendQueryParameter(
                            Conversation.ConversationType.DISCUSSION.getName(),
                            "true")
                    // 讨论组
                    .appendQueryParameter(
                            Conversation.ConversationType.PUBLIC_SERVICE
                                    .getName(),
                            "true")// 公共服务号
                    .appendQueryParameter(
                            Conversation.ConversationType.APP_PUBLIC_SERVICE
                                    .getName(),
                            "true")// 订阅号
                    .appendQueryParameter(
                            Conversation.ConversationType.SYSTEM.getName(),
                            "true")// 系统
                    .build();
            listFragment.setUri(uri);
            fragment = listFragment;
        } else {
            fragment = mConversationFragment;
        }

        //添加fragment到集合内
        oFragments.add(fragment);
        oFragments.add(new TongXunLu_Activity());
        oFragments.add(new FaXian_Activity());
        oFragments.add(new Wo_Activity());
        //设置适配器
        pager.setAdapter(new MyFragmentAdapter(this.getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                color_int();
                switch (arg0) {
                    case 0:
                        weixin_img.setImageResource(R.drawable.duihuanew);
                        weixin_txt.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    case 1:
                        tongxunlu_img.setImageResource(R.drawable.lianxirennew);
                        tongxunlu_txt.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    case 2:
                        faxian_img.setImageResource(R.drawable.zhinannew);
                        faxian_txt.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    case 3:
                        wo_img.setImageResource(R.drawable.pengyouquannew);
                        wo_txt.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        pager.setCurrentItem(0);
        weixin_img.setImageResource(R.drawable.duihuanew);
        weixin_txt.setTextColor(getResources().getColor(R.color.textcolor));

        //popupWindow监听事件
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.popup_window_layout, null);

                faqiqunliao = (LinearLayout) view.findViewById(R.id.faqiqunliao);
                tianjiapengyou = (LinearLayout) view.findViewById(R.id.tianjiapengyou);
                saoyisao = (LinearLayout) view.findViewById(R.id.saoyisao);
                youxiang = (LinearLayout) view.findViewById(R.id.youxiang);

                faqiqunliao.setOnClickListener(new myclick());
                tianjiapengyou.setOnClickListener(new myclick());
                saoyisao.setOnClickListener(new myclick());
                youxiang.setOnClickListener(new myclick());

                popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置背景
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                //设置为可点击
                popupWindow.setFocusable(true);
                popupWindow.setContentView(view);
                //设置出现在当前点击控件的正下方
                popupWindow.showAsDropDown(title);

            }
        });


    }


    //popupWindow子项点击监听事件
    class myclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.faqiqunliao:
                    Toast.makeText(Pager_Activity.this, "该功能还在开发中！", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                    break;
                case R.id.tianjiapengyou:
                    startActivity(new Intent(Pager_Activity.this,AddFriendActivity.class));
                    popupWindow.dismiss();
                    break;
                case R.id.saoyisao:
                    Toast.makeText(Pager_Activity.this, "该功能还在开发中！", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                    break;
                case R.id.youxiang:
                    Toast.makeText(Pager_Activity.this, "该功能还在开发中！", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                    break;
            }
        }
    }

    public class MyFragmentAdapter extends FragmentStatePagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return oFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return oFragments.size();
        }
    }

    private View.OnClickListener myclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.weixin:
                    pager.setCurrentItem(0);
                    break;
                case R.id.tongxunlu:
                    pager.setCurrentItem(1);
                    break;
                case R.id.faxian:
                    pager.setCurrentItem(2);
                    break;
                case R.id.wo:
                    pager.setCurrentItem(3);
                    break;
            }

        }
    };

    //控件初始化
    public void init() {
        middle = (TextView) findViewById(R.id.middle);
        right = (ImageView) findViewById(R.id.right);
        tv_1 = (LinearLayout) findViewById(R.id.weixin);
        tv_2 = (LinearLayout) findViewById(R.id.tongxunlu);
        tv_3 = (LinearLayout) findViewById(R.id.faxian);
        tv_4 = (LinearLayout) findViewById(R.id.wo);
        pager = (ViewPager) findViewById(R.id.lin_layout);
        weixin_txt = (TextView) findViewById(R.id.weixin_txt);
        tongxunlu_txt = (TextView) findViewById(R.id.tongxunlu_txt);
        faxian_txt = (TextView) findViewById(R.id.faxian_txt);
        wo_txt = (TextView) findViewById(R.id.wo_txt);
        weixin_img = (ImageView) findViewById(R.id.weixin_img);
        tongxunlu_img = (ImageView) findViewById(R.id.tongxunlu_img);
        faxian_img = (ImageView) findViewById(R.id.faxian_img);
        wo_img = (ImageView) findViewById(R.id.wo_img);
        title = (LinearLayout) findViewById(R.id.title);

        middle.setText("微信");
        right.setImageResource(R.drawable.actionbar_add_icon);

        tv_1.setOnClickListener(myclick);
        tv_2.setOnClickListener(myclick);
        tv_3.setOnClickListener(myclick);
        tv_4.setOnClickListener(myclick);
    }

    //颜色初始化
    public void color_int() {
        weixin_img.setImageResource(R.drawable.duihua);
        weixin_txt.setTextColor(getResources().getColor(R.color.textcolor_s));
        tongxunlu_img.setImageResource(R.drawable.lianxiren);
        tongxunlu_txt.setTextColor(getResources().getColor(R.color.textcolor_s));
        faxian_img.setImageResource(R.drawable.zhinan);
        faxian_txt.setTextColor(getResources().getColor(R.color.textcolor_s));
        wo_img.setImageResource(R.drawable.pengyouquan);
        wo_txt.setTextColor(getResources().getColor(R.color.textcolor_s));

    }
}
