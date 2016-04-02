package com.xiaoyu.HeartConsultation.ui.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.chat.chatuidemo.activity.ChatActivity;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;

/**
 * Created by xiaoyu on 2015/7/18.
 */
public class ActivityDoctorDesc extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private TextView ziXun;
    private ImageView avatar;
    private TextView name;
    private TextView doctor_title;
    private TextView hospital;
    private TextView good_at;
    private TextView jianJie;
    private TextView yuYue;
    private ImageLoader imageLoader;

    private static DoctorItemModel destDoctorItemModel;
    private DoctorItemModel doctorItemModel;

    public static void open(Activity activity, DoctorItemModel doctorItemModel) {
        destDoctorItemModel = doctorItemModel;
        Intent intent = new Intent(activity, ActivityDoctorDesc.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doctorItemModel = destDoctorItemModel;
        destDoctorItemModel = null;
        setContentView(R.layout.activity_doctor_desc);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        back = (FrameLayout) findViewById(R.id.back);
        ziXun = (TextView) findViewById(R.id.ziXun);
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        doctor_title = (TextView) findViewById(R.id.doctor_title);
        hospital = (TextView) findViewById(R.id.hospital);
        good_at = (TextView) findViewById(R.id.good_at);
        jianJie = (TextView) findViewById(R.id.jianJie);
        yuYue = (TextView) findViewById(R.id.yuYue);
    }

    @Override
    protected void initViews() {
        if (doctorItemModel != null) {
            if (!TextUtils.isEmpty(doctorItemModel.doctor_avatar)) {
                imageLoader.displayImage(doctorItemModel.doctor_avatar, avatar, ImageLoaderUtil.Options_Common_memory_Pic);
            } else {
                imageLoader.displayImage("", avatar, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
            if (!TextUtils.isEmpty(doctorItemModel.doctor_name)) {
                name.setText(doctorItemModel.doctor_name);
            }
            if (!TextUtils.isEmpty(doctorItemModel.doctor_title)) {
                doctor_title.setText(doctorItemModel.doctor_title);
            }
            if (!TextUtils.isEmpty(doctorItemModel.hospital)) {
                hospital.setText(doctorItemModel.hospital);
            }
            if (!TextUtils.isEmpty(doctorItemModel.good_at)) {
                good_at.setText(doctorItemModel.good_at);
            }
            if (!TextUtils.isEmpty(doctorItemModel.brief)) {
                jianJie.setText(doctorItemModel.brief);
            } else {
                jianJie.setText(getString(R.string.noJianJie));
            }
        }
    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
        ziXun.setOnClickListener(this);
        yuYue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ziXun:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("userId", doctorItemModel.doctorid);
                intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                intent.putExtra("name", doctorItemModel.doctor_name);
                startActivity(intent);
                break;
            case R.id.yuYue:
                ActivityDoctorYuYue.open(this, doctorItemModel);
                break;
        }
    }
}
