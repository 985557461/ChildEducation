package com.xiaoyu.HeartConsultation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.chat.applib.controller.HXSDKHelper;
import com.xiaoyu.HeartConsultation.ui.activity.ActivityFragment;
import com.xiaoyu.HeartConsultation.ui.community.CommunityFragment;
import com.xiaoyu.HeartConsultation.ui.help.HelpFragment;
import com.xiaoyu.HeartConsultation.ui.home.HomeFragment;
import com.xiaoyu.HeartConsultation.ui.mycenter.MyCenterFragment;
import com.xiaoyu.HeartConsultation.util.ToastUtil;

public class ActivityMain extends ActivityBase implements View.OnClickListener, EMEventListener {

    /**
     * init views
     * *
     */
    private LinearLayout homeLL;
    private ImageView homeImageView;
    private TextView homeTextView;

    private LinearLayout activityLL;
    private ImageView activityImageView;
    private TextView activityTextView;

    private LinearLayout helpLL;
    private ImageView helpImageView;
    private TextView helpTextView;

    private LinearLayout communityLL;
    private ImageView communityImageView;
    private TextView communityTextView;

    private LinearLayout myCenterLL;
    private ImageView myCenterImageView;
    private TextView myCenterTextView;
    private TextView unread_msg_number;

    /**
     * fragment
     * *
     */
    private Fragment mFragmentCurrent;
    private HomeFragment homeFragment;
    private ActivityFragment activityFragment;
    private HelpFragment helpFragment;
    private CommunityFragment communityFragment;
    private MyCenterFragment myCenterFragment;

    /**
     * double back to finish
     * *
     */
    private static int TIME_LONG = 3 * 1000;
    private long lastTime;

    /**
     * ActivityMain
     * *
     */
    private static ActivityMain activityMain = null;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityMain.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        activityMain = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});
    }

    @Override
    protected void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        super.onStop();
    }

    public static ActivityMain getInstance() {
        return activityMain;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String from = intent.getStringExtra(KeyValue.kFrom);
        if (TextUtils.isEmpty(from)) {
            return;
        }
        if (from.equals(KeyValue.ToDangAn)) {
            if (activityFragment == null) {
                activityFragment = new ActivityFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("tab", 2);
                activityFragment.setArguments(bundle);
            } else {
                activityFragment.showTab(2);
            }
            switchContent(mFragmentCurrent, activityFragment);
        }
    }

    @Override
    protected void getViews() {
        homeLL = (LinearLayout) findViewById(R.id.homeLL);
        homeImageView = (ImageView) findViewById(R.id.homeImageView);
        homeTextView = (TextView) findViewById(R.id.homeTextView);

        activityLL = (LinearLayout) findViewById(R.id.activityLL);
        activityImageView = (ImageView) findViewById(R.id.activityImageView);
        activityTextView = (TextView) findViewById(R.id.activityTextView);

        helpLL = (LinearLayout) findViewById(R.id.helpLL);
        helpImageView = (ImageView) findViewById(R.id.helpImageView);
        helpTextView = (TextView) findViewById(R.id.helpTextView);

        communityLL = (LinearLayout) findViewById(R.id.communityLL);
        communityImageView = (ImageView) findViewById(R.id.communityImageView);
        communityTextView = (TextView) findViewById(R.id.communityTextView);

        myCenterLL = (LinearLayout) findViewById(R.id.myCenterLL);
        myCenterImageView = (ImageView) findViewById(R.id.myCenterImageView);
        myCenterTextView = (TextView) findViewById(R.id.myCenterTextView);
        unread_msg_number = (TextView) findViewById(R.id.unread_msg_number);
    }

    @Override
    protected void initViews() {
        setDefaultFragment();
    }

    @Override
    protected void setListeners() {
        homeLL.setOnClickListener(this);
        activityLL.setOnClickListener(this);
        helpLL.setOnClickListener(this);
        communityLL.setOnClickListener(this);
        myCenterLL.setOnClickListener(this);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.fragmentLayout, homeFragment);
        transaction.commit();
        mFragmentCurrent = homeFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeLL:
                changeTab(0);
                break;
            case R.id.activityLL:
                changeTab(1);
                break;
            case R.id.helpLL:
                changeTab(2);
                break;
            case R.id.communityLL:
                changeTab(3);
                break;
            case R.id.myCenterLL:
                changeTab(4);
                break;
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            mFragmentCurrent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                if (from != null && from.isAdded()) {
                    transaction.hide(from);
                }
                transaction.add(R.id.fragmentLayout, to).commitAllowingStateLoss();
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

    private void changeTab(int index) {
        switch (index) {
            case 0: {
                homeImageView.setImageResource(R.drawable.icon_tab_home_press);
                activityImageView.setImageResource(R.drawable.icon_tab_activity_normal);
                helpImageView.setImageResource(R.drawable.icon_tab_help_normal);
                communityImageView.setImageResource(R.drawable.icon_tab_community_normal);
                myCenterImageView.setImageResource(R.drawable.icon_tab_center_normal);

                homeTextView.setSelected(true);
                activityTextView.setSelected(false);
                helpTextView.setSelected(false);
                communityTextView.setSelected(false);
                myCenterTextView.setSelected(false);

                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                switchContent(mFragmentCurrent, homeFragment);
            }
            break;
            case 1: {
                homeImageView.setImageResource(R.drawable.icon_tab_home_normal);
                activityImageView.setImageResource(R.drawable.icon_tab_activity_press);
                helpImageView.setImageResource(R.drawable.icon_tab_help_normal);
                communityImageView.setImageResource(R.drawable.icon_tab_community_normal);
                myCenterImageView.setImageResource(R.drawable.icon_tab_center_normal);

                homeTextView.setSelected(false);
                activityTextView.setSelected(true);
                helpTextView.setSelected(false);
                communityTextView.setSelected(false);
                myCenterTextView.setSelected(false);

                if (activityFragment == null) {
                    activityFragment = new ActivityFragment();
                }
                switchContent(mFragmentCurrent, activityFragment);
            }
            break;
            case 2: {
                homeImageView.setImageResource(R.drawable.icon_tab_home_normal);
                activityImageView.setImageResource(R.drawable.icon_tab_activity_normal);
                helpImageView.setImageResource(R.drawable.icon_tab_help_press);
                communityImageView.setImageResource(R.drawable.icon_tab_community_normal);
                myCenterImageView.setImageResource(R.drawable.icon_tab_center_normal);

                homeTextView.setSelected(false);
                activityTextView.setSelected(false);
                helpTextView.setSelected(true);
                communityTextView.setSelected(false);
                myCenterTextView.setSelected(false);

                if (helpFragment == null) {
                    helpFragment = new HelpFragment();
                }
                switchContent(mFragmentCurrent, helpFragment);
            }
            break;
            case 3: {
                homeImageView.setImageResource(R.drawable.icon_tab_home_normal);
                activityImageView.setImageResource(R.drawable.icon_tab_activity_normal);
                helpImageView.setImageResource(R.drawable.icon_tab_help_normal);
                communityImageView.setImageResource(R.drawable.icon_tab_community_press);
                myCenterImageView.setImageResource(R.drawable.icon_tab_center_normal);

                homeTextView.setSelected(false);
                activityTextView.setSelected(false);
                helpTextView.setSelected(false);
                communityTextView.setSelected(true);
                myCenterTextView.setSelected(false);

                if (communityFragment == null) {
                    communityFragment = new CommunityFragment();
                }
                switchContent(mFragmentCurrent, communityFragment);
            }
            break;
            case 4: {
                homeImageView.setImageResource(R.drawable.icon_tab_home_normal);
                activityImageView.setImageResource(R.drawable.icon_tab_activity_normal);
                helpImageView.setImageResource(R.drawable.icon_tab_help_normal);
                communityImageView.setImageResource(R.drawable.icon_tab_community_normal);
                myCenterImageView.setImageResource(R.drawable.icon_tab_center_press);

                homeTextView.setSelected(false);
                activityTextView.setSelected(false);
                helpTextView.setSelected(false);
                communityTextView.setSelected(false);
                myCenterTextView.setSelected(true);

                if (myCenterFragment == null) {
                    myCenterFragment = new MyCenterFragment();
                }
                switchContent(mFragmentCurrent, myCenterFragment);
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        killActivity();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - lastTime < TIME_LONG) {
            killActivity();
        } else {
            ToastUtil.makeShortText("再按一次返回键退出");
            lastTime = t;
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onEvent(EMNotifierEvent event) {
        switch (event.getEvent()) {
            case EventNewMessage: {
                EMMessage message = (EMMessage) event.getData();
                HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
                refreshUI();
                break;
            }
            case EventOfflineMessage: {
                refreshUI();
                break;
            }
            case EventConversationListChanged: {
                refreshUI();
                break;
            }
            default:
                break;
        }
    }

    private void refreshUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
            }
        });
    }

    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unread_msg_number.setText(String.valueOf(count));
            unread_msg_number.setVisibility(View.VISIBLE);
        } else {
            unread_msg_number.setVisibility(View.INVISIBLE);
        }
    }

    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }
}
