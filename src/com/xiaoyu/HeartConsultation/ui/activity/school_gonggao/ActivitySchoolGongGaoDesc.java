package com.xiaoyu.HeartConsultation.ui.activity.school_gonggao;

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
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;
import com.xiaoyu.HeartConsultation.util.StringUtil;

/**
 * Created by xiaoyu on 2015/7/18.
 */
public class ActivitySchoolGongGaoDesc extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private TextView title;
    private TextView time;
    private TextView readCount;
    private ImageView image;
    private TextView content;

    private static GongGaoItemModel destGongGaoItemModel;
    private GongGaoItemModel gongGaoItemModel;

    public static void open(Activity activity, GongGaoItemModel gongGaoItemModel) {
        destGongGaoItemModel = gongGaoItemModel;
        Intent intent = new Intent(activity, ActivitySchoolGongGaoDesc.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gongGaoItemModel = destGongGaoItemModel;
        destGongGaoItemModel = null;
        setContentView(R.layout.activity_school_gonggao_desc);
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
    }

    @Override
    protected void initViews() {
        if (gongGaoItemModel != null) {
            if (!TextUtils.isEmpty(gongGaoItemModel.title)) {
                title.setText(gongGaoItemModel.title);
            }
            if (!TextUtils.isEmpty(gongGaoItemModel.time)) {
                time.setText(StringUtil.getDateToString2(gongGaoItemModel.time));
            }
            if (!TextUtils.isEmpty(gongGaoItemModel.readcount)) {
                readCount.setText(getString(R.string.yuedu) + gongGaoItemModel.readcount);
            }
            if (!TextUtils.isEmpty(gongGaoItemModel.imageurl)) {
                HCApplicaton.getInstance().getImageLoader().displayImage(gongGaoItemModel.imageurl, image, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
            if (!TextUtils.isEmpty(gongGaoItemModel.content)) {
                content.setText(Html.fromHtml(gongGaoItemModel.content));
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
}
