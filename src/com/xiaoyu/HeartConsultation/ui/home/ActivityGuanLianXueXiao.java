package com.xiaoyu.HeartConsultation.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.ActivityMain;
import com.xiaoyu.HeartConsultation.ui.KeyValue;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/30.
 */
public class ActivityGuanLianXueXiao extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private EditText sheng;
    private EditText shi;
    private EditText xian;
    private EditText school;
    private EditText xuehao;
    private EditText mima;
    private TextView guanLian;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityGuanLianXueXiao.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guanlian_xuexiao);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
        sheng = (EditText) findViewById(R.id.sheng);
        shi = (EditText) findViewById(R.id.shi);
        xian = (EditText) findViewById(R.id.xian);
        school = (EditText) findViewById(R.id.school);
        xuehao = (EditText) findViewById(R.id.xuehao);
        mima = (EditText) findViewById(R.id.mima);
        guanLian = (TextView) findViewById(R.id.guanLian);
    }

    @Override
    protected void initViews() {
        back.setOnClickListener(this);
        guanLian.setOnClickListener(this);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.guanLian:
                tryToGuanLianSchool();
                break;
        }
    }

    private void tryToGuanLianSchool() {
//        String shengStr = sheng.getText().toString();
//        if (TextUtils.isEmpty(shengStr)) {
//            ToastUtil.makeShortText(getString(R.string.inputsheng));
//            return;
//        }
//        String shiStr = shi.getText().toString();
//        if (TextUtils.isEmpty(shiStr)) {
//            ToastUtil.makeShortText(getString(R.string.inputshi));
//            return;
//        }
//        String xianStr = xian.getText().toString();
//        if (TextUtils.isEmpty(xianStr)) {
//            ToastUtil.makeShortText(getString(R.string.inputxian));
//            return;
//        }
        String schoolStr = school.getText().toString();
        if (TextUtils.isEmpty(schoolStr)) {
            ToastUtil.makeShortText(getString(R.string.inputschool));
            return;
        }
        final String xuehaoStr = xuehao.getText().toString();
        if (TextUtils.isEmpty(xuehaoStr)) {
            ToastUtil.makeShortText(getString(R.string.inputxuehao));
            return;
        }
        final String mimaStr = mima.getText().toString();
        if (TextUtils.isEmpty(mimaStr)) {
            ToastUtil.makeShortText(getString(R.string.inputmima));
            return;
        }
        String finalpasswd = new SimpleHash("SHA-1", xuehaoStr, mimaStr).toString();

        showDialog();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("schoolName", schoolStr));
        nameValuePairs.add(new BasicNameValuePair("stuNo", xuehaoStr));
        nameValuePairs.add(new BasicNameValuePair("stuPassWd", finalpasswd));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_GUANLIAN_SCHOOL, Request.POST, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                dismissDialog();
                ToastUtil.makeShortText(getString(R.string.guanlianFiled));
            }

            @Override
            public void onComplete(String response) {
                dismissDialog();
                CommonModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (model != null && model.result == 1) {
                    Account account = HCApplicaton.getInstance().getAccount();
                    account.stuNo = xuehaoStr;
                    account.stuPassWd = mimaStr;
                    account.saveMeInfoToPreference();
                    ToastUtil.makeShortText(getString(R.string.guanlianSucc));
                    Intent intent = new Intent(ActivityGuanLianXueXiao.this, ActivityMain.class);
                    intent.putExtra(KeyValue.kFrom, KeyValue.ToDangAn);
                    ActivityGuanLianXueXiao.this.startActivity(intent);
                    finish();
                } else {
                    ToastUtil.makeShortText(getString(R.string.guanlianFiled));
                }
            }
        });
    }
}
