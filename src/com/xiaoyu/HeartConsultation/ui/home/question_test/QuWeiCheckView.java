package com.xiaoyu.HeartConsultation.ui.home.question_test;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.home.question_test.quwei.ActivityQuWeiTestList;
import com.xiaoyu.HeartConsultation.widget.SlideShowView;

/**
 * Created by xiaoyu on 2015/6/15.
 */
public class QuWeiCheckView extends FrameLayout implements View.OnClickListener {
    private FrameLayout aiQing;
    private FrameLayout caiFu;
    private FrameLayout sheJiao;
    private FrameLayout xingGe;
    private FrameLayout nengLi;
    private FrameLayout jiaTing;

    private SlideShowView slideShowView;

    private String[] urls = {"http://image.zcool.com.cn/59/54/m_1303967870670.jpg",
            "http://image.zcool.com.cn/47/19/1280115949992.jpg", "http://image.zcool.com.cn/59/11/m_1303967844788.jpg"};

    private Activity activity;

    public QuWeiCheckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public QuWeiCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuWeiCheckView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.quwei_check_view, this, true);
        aiQing = (FrameLayout) findViewById(R.id.aiQing);
        caiFu = (FrameLayout) findViewById(R.id.caiFu);
        sheJiao = (FrameLayout) findViewById(R.id.sheJiao);
        xingGe = (FrameLayout) findViewById(R.id.xingGe);
        nengLi = (FrameLayout) findViewById(R.id.nengLi);
        jiaTing = (FrameLayout) findViewById(R.id.jiaTing);

        slideShowView = (SlideShowView) findViewById(R.id.slideShowView);

        aiQing.setOnClickListener(this);
        caiFu.setOnClickListener(this);
        sheJiao.setOnClickListener(this);
        xingGe.setOnClickListener(this);
        nengLi.setOnClickListener(this);
        jiaTing.setOnClickListener(this);

        slideShowView.initUI(urls, context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aiQing:
                ActivityQuWeiTestList.open(activity, "funny_question_love.xml");
                break;
            case R.id.caiFu:
                ActivityQuWeiTestList.open(activity, "funny_question_wealth.xml");
                break;
            case R.id.sheJiao:
                ActivityQuWeiTestList.open(activity, "funny_question_shejiao.xml");
                break;
            case R.id.xingGe:
                ActivityQuWeiTestList.open(activity, "funny_question_xingge.xml");
                break;
            case R.id.nengLi:
                ActivityQuWeiTestList.open(activity, "funny_question_nengli.xml");
                break;
            case R.id.jiaTing:
                ActivityQuWeiTestList.open(activity, "funny_question_family.xml");
                break;
        }
    }
}
