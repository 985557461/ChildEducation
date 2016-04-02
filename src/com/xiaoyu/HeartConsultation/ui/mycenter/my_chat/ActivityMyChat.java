package com.xiaoyu.HeartConsultation.ui.mycenter.my_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;

/**
 * Created by xiaoyu on 2015/7/19.
 */
public class ActivityMyChat extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    public MyChatFragment myChatFragment;

    /**
     * 账号是否被别人登陆
     * *
     */
    public static boolean isConflict = false;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityMyChat.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_chat);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (myChatFragment != null) {
            myChatFragment.onNewIntent(intent);
        }
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
    }

    @Override
    protected void initViews() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        myChatFragment = new MyChatFragment();
        transaction.add(R.id.fragmentLayout, myChatFragment);
        transaction.commit();
    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
