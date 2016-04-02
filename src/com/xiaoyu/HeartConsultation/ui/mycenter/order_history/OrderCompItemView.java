package com.xiaoyu.HeartConsultation.ui.mycenter.order_history;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
public class OrderCompItemView extends FrameLayout implements View.OnClickListener {
    private ImageView avatar;
    private TextView title;
    private TextView name;
    private TextView comment;
    private TextView delete;
    private ImageLoader imageLoader;
    private OrderCompItemViewListener listener;

    public OrderItemModel model;

    public OrderCompItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public OrderCompItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderCompItemView(Context context) {
        super(context);
        init(context);
    }

    public OrderCompItemView(Context context, OrderCompItemViewListener listener) {
        super(context);
        this.listener = listener;
        init(context);
    }

    public interface OrderCompItemViewListener {
        public void onCommentClicked(OrderItemModel model);

        public void onDeleteClicked(OrderItemModel model);

        public void onContentClicked(OrderItemModel model);
    }

    private void init(Context context) {
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.order_comp_item_view, this, true);

        avatar = (ImageView) findViewById(R.id.avatar);
        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        comment = (TextView) findViewById(R.id.comment);
        delete = (TextView) findViewById(R.id.delete);
        this.setOnClickListener(this);
        comment.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    public void setData(OrderItemModel model) {
        this.model = model;
        if (model == null) {
            return;
        }
        if (!TextUtils.isEmpty(model.doctorimg)) {
            imageLoader.displayImage(model.doctorimg, avatar, ImageLoaderUtil.Options_Common_Disc_Pic);
        }
        if (!TextUtils.isEmpty(model.apptime)) {
            title.setText(model.apptime);
        } else {
            title.setText(getContext().getString(R.string.order));
        }
        if (!TextUtils.isEmpty(model.dotorname)) {
            name.setText(model.dotorname);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment:
                if (listener != null) {
                    listener.onCommentClicked(model);
                }
                break;
            case R.id.delete:
                if (listener != null) {
                    listener.onDeleteClicked(model);
                }
                break;
            default:
                if (listener != null) {
                    listener.onContentClicked(model);
                }
                break;
        }
    }
}
