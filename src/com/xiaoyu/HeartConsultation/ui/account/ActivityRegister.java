package com.xiaoyu.HeartConsultation.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.CommonUtil;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyuPC on 2015/6/7.
 */
public class ActivityRegister extends ActivityBase implements View.OnClickListener {

    private Account account;
    /**
     * init views
     * *
     */
    private FrameLayout backFL;
    private TextView commit;
    private EditText phoneNumber;
    private TextView getVerCode;
    private EditText verificationCode;
    private EditText password;
    private TextView readXieYi;

    private static final int cuteDownTime = 60;//60s
    private int tempCuteDownTime = cuteDownTime;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 1) {
                if (msg.arg1 >= 0) {
                    getVerCode.setText(msg.arg1 + "s");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = --tempCuteDownTime;
                    myHandler.sendMessageDelayed(message, 1000);
                } else {
                    getVerCode.setBackgroundResource(R.drawable.orange_round_shape);
                    getVerCode.setClickable(true);
                    getVerCode.setText(getString(R.string.getVerficationCode));
                }
            }
        }
    };


    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityRegister.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_hc);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        account = HCApplicaton.getInstance().getAccount();
        backFL = (FrameLayout) findViewById(R.id.backFL);
        commit = (TextView) findViewById(R.id.commit);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        getVerCode = (TextView) findViewById(R.id.getVerCode);
        verificationCode = (EditText) findViewById(R.id.verificationCode);
        password = (EditText) findViewById(R.id.password);
        readXieYi = (TextView) findViewById(R.id.readXieYi);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
        commit.setOnClickListener(this);
        getVerCode.setOnClickListener(this);
        readXieYi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.commit:
                tryToRegister();
                break;
            case R.id.getVerCode:
                tryToGetVerCode();
                break;
            case R.id.readXieYi:
                ActivityXieYi.open(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        myHandler.removeMessages(1);
        super.onDestroy();
    }

    private void tryToRegister() {
        final String phoneNumberStr = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumberStr)) {
            ToastUtil.makeShortText(getString(R.string.phoneNotNul));
            return;
        }
        if (!CommonUtil.isPhoneNumberValid(phoneNumberStr)) {
            ToastUtil.makeShortText(getString(R.string.phoneNorRight));
            return;
        }

        String verCodeStr = verificationCode.getText().toString();
        if (TextUtils.isEmpty(verCodeStr)) {
            ToastUtil.makeShortText(getString(R.string.verificationCodeNotNull));
            return;
        }
        final String pwdStr = password.getText().toString();
        if (TextUtils.isEmpty(pwdStr)) {
            ToastUtil.makeShortText(getString(R.string.passwordNotNull));
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phoneNumber", phoneNumberStr));
        nameValuePairs.add(new BasicNameValuePair("password", pwdStr));
        nameValuePairs.add(new BasicNameValuePair("activationNumber", verCodeStr));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_REGISTER, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.showErrorMessage(e, "");
            }

            @Override
            public void onComplete(String response) {
                RegisterModel registerModel = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, RegisterModel.class);
                if (registerModel != null && registerModel.result == 1) {
                    account.phoneNumber = phoneNumberStr;
                    account.password = pwdStr;
                    account.userId = registerModel.userId;
                    account.saveMeInfoToPreference();
                    tryToRegisterChatServer();
                } else {
                    if (registerModel == null || TextUtils.isEmpty(registerModel.message)) {
                        ToastUtil.makeShortText(getString(R.string.registerFiled));
                    } else {
                        ToastUtil.makeShortText(registerModel.message);
                    }
                }
            }
        });
    }

    private void tryToRegisterChatServer() {
        final String st7 = getResources().getString(R.string.network_anomalies);
        final String st8 = getResources().getString(R.string.User_already_exists);
        final String st9 = getResources().getString(R.string.registration_failed_without_permission);
        final String st10 = getResources().getString(R.string.Registration_failed);
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMChatManager.getInstance().createAccountOnServer(account.userId, account.password);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            HCApplicaton.getInstance().setUserName(account.userId);
                            ToastUtil.makeShortText(getString(R.string.registerSuccessful));
                            finish();
                        }
                    });
                } catch (final EaseMobException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NONETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), st7, Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXISTS) {
                                Toast.makeText(getApplicationContext(), st8, Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.UNAUTHORIZED) {
                                Toast.makeText(getApplicationContext(), st9, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), st10 + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void tryToGetVerCode() {
        String phoneNumberStr = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumberStr)) {
            ToastUtil.makeShortText(R.string.phoneNotNul);
            return;
        }
        if (!CommonUtil.isPhoneNumberValid(phoneNumberStr)) {
            ToastUtil.makeShortText(R.string.phoneNorRight);
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("phoneNumber", phoneNumberStr));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_GET_VER_CODE, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(getString(R.string.verificationFiled));
            }

            @Override
            public void onComplete(String response) {
                CommonModel commonModel = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (commonModel != null && commonModel.result == 1) {
                    ToastUtil.makeShortText(getString(R.string.verificationSend));
                    getVerCode.setClickable(false);
                    getVerCode.setBackgroundResource(R.drawable.gray_round_shape);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = tempCuteDownTime;
                    myHandler.sendMessage(message);
                } else {
                    ToastUtil.makeShortText(getString(R.string.verificationFiled));
                }
            }
        });
    }

    class RegisterModel {
        public String message;
        public int result;
        public String userId;
    }
}
