package com.xiaoyu.HeartConsultation.ui.help;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class DoctorItemView extends FrameLayout {
    private ImageView avatar;
    private TextView name;
    private TextView doctor_title;
    private TextView hospital;
    private TextView good_at;

    private ImageLoader imageLoader;

    public DoctorItemModel model;

    public DoctorItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DoctorItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoctorItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.doctor_item_view, this, true);

        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        doctor_title = (TextView) findViewById(R.id.doctor_title);
        hospital = (TextView) findViewById(R.id.hospital);
        good_at = (TextView) findViewById(R.id.good_at);
    }

    public void setData(DoctorItemModel model) {
        this.model = model;
        if (model == null) {
            return;
        }
        if (!TextUtils.isEmpty(model.doctor_avatar)) {
            imageLoader.displayImage(model.doctor_avatar, avatar, ImageLoaderUtil.Options_Common_memory_Pic);
        } else {
            imageLoader.displayImage("", avatar, ImageLoaderUtil.Options_Common_Disc_Pic);
        }
        if (!TextUtils.isEmpty(model.doctor_name)) {
            name.setText(model.doctor_name);
        }
        if (!TextUtils.isEmpty(model.doctor_title)) {
            doctor_title.setText(model.doctor_title);
        }
        if (!TextUtils.isEmpty(model.hospital)) {
            hospital.setText(model.hospital);
        }
        if (!TextUtils.isEmpty(model.good_at)) {
            good_at.setText(model.good_at);
        }
    }
}
