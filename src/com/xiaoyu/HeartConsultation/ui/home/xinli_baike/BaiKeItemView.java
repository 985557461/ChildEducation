package com.xiaoyu.HeartConsultation.ui.home.xinli_baike;

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
public class BaiKeItemView extends FrameLayout {
    private TextView title;
    private ImageView image;

    public BaikeItemModel model;

    private ImageLoader imageLoader;

    public BaiKeItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public BaiKeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaiKeItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.baike_item_view, this, true);

        title = (TextView) findViewById(R.id.title);
        image = (ImageView) findViewById(R.id.image);
    }

    public void setData(BaikeItemModel model) {
        this.model = model;
        if (model == null) {
            return;
        }
        if (!TextUtils.isEmpty(model.title)) {
            title.setText(model.title);
        }

        if (!TextUtils.isEmpty(model.imageurl)) {
            imageLoader.displayImage(model.imageurl, image, ImageLoaderUtil.Options_Common_Disc_Pic);
        }
    }
}
