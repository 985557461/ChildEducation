package com.xiaoyu.HeartConsultation.ui.home.xinli_baike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.Debug;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.StringUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/18.
 */
public class ActivityBaiKeDesc extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private TextView title;
    private TextView time;
    private TextView readCount;
    private ImageView image;
    private TextView content;

    private static BaikeItemModel destBaikeItemModel;
    private BaikeItemModel baikeItemModel;

    public static void open(Activity activity, BaikeItemModel baikeItemModel) {
        destBaikeItemModel = baikeItemModel;
        Intent intent = new Intent(activity, ActivityBaiKeDesc.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baikeItemModel = destBaikeItemModel;
        destBaikeItemModel = null;
        setContentView(R.layout.activity_baike_desc);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        time = (TextView) findViewById(R.id.time);
        readCount = (TextView) findViewById(R.id.readCount);
        image = (ImageView) findViewById(R.id.image);
        content = (TextView) findViewById(R.id.content);
        addOne();
    }

    @Override
    protected void initViews() {
        if (baikeItemModel != null) {
            if (!TextUtils.isEmpty(baikeItemModel.title)) {
                title.setText(baikeItemModel.title);
            }
            if (!TextUtils.isEmpty(baikeItemModel.time)) {
                time.setText(StringUtil.getDateToString2(baikeItemModel.time));
            }
            if (!TextUtils.isEmpty(baikeItemModel.readcount)) {
                readCount.setText(getString(R.string.yuedu) + baikeItemModel.readcount);
            }
            if (!TextUtils.isEmpty(baikeItemModel.imageurl)) {
                HCApplicaton.getInstance().getImageLoader().displayImage(baikeItemModel.imageurl, image, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
            if (!TextUtils.isEmpty(baikeItemModel.content)) {
                content.setText(Html.fromHtml(baikeItemModel.content));
            }
        }
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

    private void addOne() {
        if (TextUtils.isEmpty(baikeItemModel.id)) {
            return;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", baikeItemModel.id));
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
                    if (!TextUtils.isEmpty(baikeItemModel.readcount)) {
                        int count = Integer.valueOf(baikeItemModel.readcount);
                        count = count + 1;
                        baikeItemModel.readcount = count + "";
                        readCount.setText(getString(R.string.yuedu) + baikeItemModel.readcount);
                    }
                }
            }
        });
    }
}
