package com.xiaoyu.HeartConsultation.ui.activity.school_huodong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.account.ActivityLogin;
import com.xiaoyu.HeartConsultation.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/9.
 */
public class ActivitySchoolHuoDongDesc extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private ImageView image;
    private TextView title;
    private TextView count;
    private TextView time;
    private TextView address;
    private TextView content;
    private TextView baoMing;

    private static HuodongItemModel destHuodongItemModel;
    private HuodongItemModel huodongItemModel;

    private ImageLoader imageLoader;


    public static void open(Activity activity, HuodongItemModel itemModel) {
        destHuodongItemModel = itemModel;
        Intent intent = new Intent(activity, ActivitySchoolHuoDongDesc.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        huodongItemModel = destHuodongItemModel;
        destHuodongItemModel = null;
        setContentView(R.layout.activity_school_huodong_desc);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        back = (FrameLayout) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        count = (TextView) findViewById(R.id.count);
        time = (TextView) findViewById(R.id.time);
        address = (TextView) findViewById(R.id.address);
        content = (TextView) findViewById(R.id.content);
        baoMing = (TextView) findViewById(R.id.baoMing);
        addOne();
    }

    @Override
    protected void initViews() {
        if (huodongItemModel != null) {
            if (!TextUtils.isEmpty(huodongItemModel.imageurl)) {
                imageLoader.displayImage(huodongItemModel.imageurl, image, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
            if (!TextUtils.isEmpty(huodongItemModel.title)) {
                title.setText(huodongItemModel.title);
            }
            if (!TextUtils.isEmpty(huodongItemModel.address)) {
                address.setText(huodongItemModel.address);
            } else {
                address.setText(getString(R.string.unknow));
            }
            if (!TextUtils.isEmpty(huodongItemModel.time)) {
                time.setText(StringUtil.getDateToString1(huodongItemModel.time));
            }
            if (!TextUtils.isEmpty(huodongItemModel.readcount)) {
                count.setText(getString(R.string.look) + huodongItemModel.readcount + getString(R.string.ci));
            }
            if (!TextUtils.isEmpty(huodongItemModel.content)) {
                content.setText(Html.fromHtml(huodongItemModel.content));
            }
        }
    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
        baoMing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.baoMing:
                tryToBaoMing();
                break;
        }
    }

    private void tryToBaoMing() {
        Account account = HCApplicaton.getInstance().getAccount();
        if (TextUtils.isEmpty(account.userId)) {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", account.userId));
        nameValuePairs.add(new BasicNameValuePair("topicid", huodongItemModel.id));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_BAOMING_HUODONG, Request.POST, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                dismissDialog();
                ToastUtil.makeShortText(getString(R.string.baoMingField));
            }

            @Override
            public void onComplete(String response) {
                dismissDialog();
                CommonModel commonModel = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (commonModel != null && commonModel.result == 1) {
                    ToastUtil.makeShortText(getString(R.string.baoMingSucc));
                    finish();
                } else {
                    ToastUtil.makeShortText(getString(R.string.baoMingField));
                }
            }
        });
    }

    private void addOne() {
        if (TextUtils.isEmpty(huodongItemModel.id)) {
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", huodongItemModel.id));
        nameValuePairs.add(new BasicNameValuePair("start_num", "0"));
        nameValuePairs.add(new BasicNameValuePair("limit", "1"));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_QUERY_ACTIVITY_DETAIL, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                Debug.debug("failed");
            }

            @Override
            public void onComplete(String response) {
                CommonModel commonModel = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (commonModel != null && commonModel.result == 1) {
                    if (!TextUtils.isEmpty(huodongItemModel.readcount)) {
                        int countInt = Integer.valueOf(huodongItemModel.readcount);
                        countInt = countInt + 1;
                        huodongItemModel.readcount = countInt + "";
                        count.setText(getString(R.string.yuedu) + huodongItemModel.readcount);
                    }
                }
            }
        });
    }
}
