package com.xiaoyu.HeartConsultation.ui.activity.school_huodong;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class SchoolHuoDongView extends FrameLayout implements View.OnClickListener {
    private TextView xiaoNei;
    private TextView xiaoWai;
    private SchoolXiaoNeiHuoDongView xiaoNeiView;
    private SchoolXiaoWaiHuoDongView xiaoWaiView;
    private Activity activity;

    public SchoolHuoDongView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SchoolHuoDongView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchoolHuoDongView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.school_huodong_view_nei_wai, this, true);
        xiaoNei = (TextView) findViewById(R.id.xiaoNei);
        xiaoWai = (TextView) findViewById(R.id.xiaoWai);
        xiaoNeiView = (SchoolXiaoNeiHuoDongView) findViewById(R.id.xiaoNeiView);
        xiaoWaiView = (SchoolXiaoWaiHuoDongView) findViewById(R.id.xiaoWaiView);
        xiaoNei.setSelected(true);
        xiaoNei.setOnClickListener(this);
        xiaoWai.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiaoNei:
                xiaoNei.setSelected(true);
                xiaoWai.setSelected(false);
                xiaoNeiView.setVisibility(View.VISIBLE);
                xiaoWaiView.setVisibility(View.INVISIBLE);
                break;
            case R.id.xiaoWai:
                xiaoNei.setSelected(false);
                xiaoWai.setSelected(true);
                xiaoNeiView.setVisibility(View.INVISIBLE);
                xiaoWaiView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
