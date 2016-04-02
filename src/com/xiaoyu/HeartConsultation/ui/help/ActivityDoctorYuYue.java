package com.xiaoyu.HeartConsultation.ui.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.chat.chatuidemo.utils.CommonUtils;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.CommonUtil;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import com.xiaoyu.HeartConsultation.widget.HCDatePickDialog;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/23.
 */
public class ActivityDoctorYuYue extends ActivityBase implements View.OnClickListener, HCDatePickDialog.HCDatePickDialogListener {
    private FrameLayout back;
    private EditText name;
    private EditText phoneNumber;
    private EditText address;
    private TextView yuYueTime;
    private TextView sure;

    private String time;

    private static DoctorItemModel destDoctorItemModel;
    private DoctorItemModel doctorItemModel;

    public static void open(Activity activity, DoctorItemModel doctorItemModel) {
        destDoctorItemModel = doctorItemModel;
        Intent intent = new Intent(activity, ActivityDoctorYuYue.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doctorItemModel = destDoctorItemModel;
        destDoctorItemModel = null;
        setContentView(R.layout.activity_doctor_yuyue);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
        name = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);
        yuYueTime = (TextView) findViewById(R.id.yuYueTime);
        sure = (TextView) findViewById(R.id.sure);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
        yuYueTime.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yuYueTime:
                HCDatePickDialog.showDlg(this, this);
                break;
            case R.id.sure:
                tryToYuYue();
                break;
        }
    }

    private void tryToYuYue() {
        if (!CommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String nameStr = name.getText().toString();
        if (TextUtils.isEmpty(nameStr)) {
            ToastUtil.makeShortText(getString(R.string.nameNotNull));
            return;
        }
        final String phoneNumberStr = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumberStr)) {
            ToastUtil.makeShortText(getString(R.string.phoneNotNul));
            return;
        }
        if (!CommonUtil.isPhoneNumberValid(phoneNumberStr)) {
            ToastUtil.makeShortText(getString(R.string.phoneNorRight));
            return;
        }
        String addressStr = address.getText().toString();
        if (TextUtils.isEmpty(addressStr)) {
            ToastUtil.makeShortText(getString(R.string.addressNotNull));
            return;
        }
        if (TextUtils.isEmpty(time)) {
            ToastUtil.makeShortText(getString(R.string.timeNotNull));
            return;
        }
        showDialog();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doctorId", doctorItemModel.doctorid));
        nameValuePairs.add(new BasicNameValuePair("userId", HCApplicaton.getInstance().getAccount().userId));
        nameValuePairs.add(new BasicNameValuePair("username", nameStr));
        nameValuePairs.add(new BasicNameValuePair("apptime", time));
        nameValuePairs.add(new BasicNameValuePair("userphone", phoneNumberStr));
        nameValuePairs.add(new BasicNameValuePair("address", addressStr));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_YUYUE_DOCTOR, Request.POST, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                dismissDialog();
                ToastUtil.makeShortText(getString(R.string.yuyueFiled));
            }

            @Override
            public void onComplete(String response) {
                CommonModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (model != null && model.result == 1) {
                    ToastUtil.makeShortText(getString(R.string.yuyueSuccPleaseWait));
                    finish();
                } else {
                    dismissDialog();
                    if (model == null || TextUtils.isEmpty(model.message)) {
                        ToastUtil.makeShortText(getString(R.string.yuyueFiled));
                    } else {
                        ToastUtil.makeShortText(model.message);
                    }
                }
            }
        });
    }

    @Override
    public void onDataPicked(String date) {
        time = date;
        yuYueTime.setText(time);
    }
}
