package com.xiaoyu.HeartConsultation.ui.activity.school_huodong;

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
import com.xiaoyu.HeartConsultation.util.StringUtil;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class HuoDongItemView extends FrameLayout {
    private ImageView image;
    private TextView title;
    private TextView address;
    private TextView time;
    private TextView free;
    private TextView count;

    public HuodongItemModel model;
    private ImageLoader imageLoader;

    public HuoDongItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HuoDongItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HuoDongItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.huodong_item_view, this, true);

        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        address = (TextView) findViewById(R.id.address);
        time = (TextView) findViewById(R.id.time);
        free = (TextView) findViewById(R.id.free);
        count = (TextView) findViewById(R.id.count);
    }

    public void setData(HuodongItemModel model) {
        this.model = model;
        if (model == null) {
            return;
        }
        if (!TextUtils.isEmpty(model.imageurl)) {
            imageLoader.displayImage(model.imageurl, image, ImageLoaderUtil.Options_Common_Disc_Pic);
        }
        if (!TextUtils.isEmpty(model.title)) {
            title.setText(model.title);
        }
        if (!TextUtils.isEmpty(model.address)) {
            address.setText(model.address);
        }else{
            address.setText(getContext().getString(R.string.unknow));
        }
        if (!TextUtils.isEmpty(model.time)) {
            time.setText(StringUtil.getDateToString1(model.time));
        }
        if (!TextUtils.isEmpty(model.readcount)) {
            count.setText(model.readcount);
        }
    }
}
