package com.xiaoyu.HeartConsultation.ui.home.question_test;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.home.question_test.zhuanye.ActivityZhuanYeText;
import com.xiaoyu.HeartConsultation.widget.SlideShowView;

/**
 * Created by xiaoyu on 2015/6/15.
 */
public class ZhuanYeCheckView extends FrameLayout implements View.OnClickListener {
    private FrameLayout homeStyle;

    private SlideShowView slideShowView;
    private Activity activity;

    private String[] urls = {"http://image.zcool.com.cn/56/35/1303967876491.jpg", "http://image.zcool.com.cn/59/54/m_1303967870670.jpg",
            "http://image.zcool.com.cn/47/19/1280115949992.jpg"};

    public ZhuanYeCheckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ZhuanYeCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZhuanYeCheckView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.zhuanye_check_view, this, true);
        homeStyle = (FrameLayout) findViewById(R.id.homeStyle);
        slideShowView = (SlideShowView) findViewById(R.id.slideShowView);

        homeStyle.setOnClickListener(this);
        slideShowView.initUI(urls, context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeStyle:
                ActivityZhuanYeText.open(activity, "zhuanye_question_family.xml");
                break;
        }
    }
}
