package com.xiaoyu.HeartConsultation.ui.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.chat.applib.controller.HXSDKHelper;
import com.xiaoyu.HeartConsultation.ui.account.ActivityLogin;
import com.xiaoyu.HeartConsultation.ui.mycenter.my_chat.ActivityMyChat;
import com.xiaoyu.HeartConsultation.ui.mycenter.order_history.ActivityOrderHistory;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;
import com.xiaoyu.HeartConsultation.widget.HCAlertDlgNoTitle;

/**
 * Created by xiaoyu on 2015/7/1.
 */
public class MyCenterFragment extends Fragment implements View.OnClickListener, EMEventListener {
    private ImageView avatar;
    private TextView phoneNumber;
    private LinearLayout questionHistory;
    private LinearLayout yuYueHistory;
    private LinearLayout ziXunHistory;
    private TextView unread_msg_number;
    private LinearLayout baoMingHuoDongHistory;
    private LinearLayout collectionHistory;
    private LinearLayout giveGood;
    private LinearLayout setting;
    private LinearLayout myInfoSetting;
    private TextView logout;

    private Account account;
    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_center_fragment, container, false);
        initViewAndListener(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(account.avatar)) {
            imageLoader.displayImage(account.avatar, avatar, ImageLoaderUtil.Options_Memory_Rect_Avatar);
        } else {
            imageLoader.displayImage("", avatar, ImageLoaderUtil.Options_Memory_Rect_Avatar);
        }
        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});
    }

    @Override
    public void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        super.onStop();
    }

    private void initViewAndListener(View view) {
        account = HCApplicaton.getInstance().getAccount();
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        avatar = (ImageView) view.findViewById(R.id.avatar);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        questionHistory = (LinearLayout) view.findViewById(R.id.questionHistory);
        yuYueHistory = (LinearLayout) view.findViewById(R.id.yuYueHistory);
        unread_msg_number = (TextView) view.findViewById(R.id.unread_msg_number);
        ziXunHistory = (LinearLayout) view.findViewById(R.id.ziXunHistory);
        baoMingHuoDongHistory = (LinearLayout) view.findViewById(R.id.baoMingHuoDongHistory);
        collectionHistory = (LinearLayout) view.findViewById(R.id.collectionHistory);
        giveGood = (LinearLayout) view.findViewById(R.id.giveGood);
        setting = (LinearLayout) view.findViewById(R.id.setting);
        myInfoSetting = (LinearLayout) view.findViewById(R.id.myInfoSetting);
        logout = (TextView) view.findViewById(R.id.logout);

        questionHistory.setOnClickListener(this);
        yuYueHistory.setOnClickListener(this);
        ziXunHistory.setOnClickListener(this);
        baoMingHuoDongHistory.setOnClickListener(this);
        collectionHistory.setOnClickListener(this);
        giveGood.setOnClickListener(this);
        setting.setOnClickListener(this);
        myInfoSetting.setOnClickListener(this);
        logout.setOnClickListener(this);

        if (!TextUtils.isEmpty(account.avatar)) {
            imageLoader.displayImage(account.avatar, avatar, ImageLoaderUtil.Options_Memory_Rect_Avatar);
        } else {
            imageLoader.displayImage("", avatar, ImageLoaderUtil.Options_Memory_Rect_Avatar);
        }
        if (!TextUtils.isEmpty(account.phoneNumber)) {
            phoneNumber.setText(getActivity().getString(R.string.accountStr) + account.phoneNumber);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.questionHistory:
                ActivityMyPost.open(getActivity());
                break;
            case R.id.yuYueHistory:
                ActivityOrderHistory.open(getActivity());
                break;
            case R.id.ziXunHistory:
                ActivityMyChat.open(getActivity());
                break;
            case R.id.baoMingHuoDongHistory:
                ActivityMyEverBaoMingHuoDong.open(getActivity());
                break;
            case R.id.myInfoSetting:
                ActivityMyInfoSetting.open(getActivity());
                break;
            case R.id.logout:
                tryToLogout();
                break;
        }
    }

    private void tryToLogout() {
        String stre = getString(R.string.areYouSureLogout);
        HCAlertDlgNoTitle.showDlg("", stre, getActivity(), new HCAlertDlgNoTitle.HCAlertDlgClickListener() {
            @Override
            public void onAlertDlgClicked(boolean isConfirm) {
                if (isConfirm) {
                    HCApplicaton.getInstance().logout(new EMCallBack() {

                        @Override
                        public void onSuccess() {
                            account.clearMeInfo();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {

                        }
                    });
                }
            }
        });
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
        getActivity().runOnUiThread(new Runnable() {
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
