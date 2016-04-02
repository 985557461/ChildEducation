package com.xiaoyu.HeartConsultation.ui.activity.child_dangan;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class ChildDangAnItemView extends FrameLayout {
    private TextView title;
    public DangAn model;

    public ChildDangAnItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ChildDangAnItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChildDangAnItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.child_dangan_item_view, this, true);

        title = (TextView) findViewById(R.id.title);
    }

    public void setData(DangAn model) {
        this.model = model;
        if (model == null) {
            return;
        }
        if (!TextUtils.isEmpty(model.EVALUATION_NAME)) {
            title.setText(model.EVALUATION_NAME);
        } else {
            title.setText("暂无名字");
        }
    }
}
