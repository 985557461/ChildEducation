package com.xiaoyu.HeartConsultation.ui.home.question_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;

/**
 * Created by xiaoyu on 2015/6/15.
 */
public class ActivityHeartCheck extends ActivityBase implements View.OnClickListener {
    private FrameLayout backFL;
    private RelativeLayout zhuanYeLL;
    private TextView zhuanYeTextView;
    private View zhuanYeLineView;
    private RelativeLayout quWeiLL;
    private TextView quWeiTextView;
    private View quWeiLineView;
    private ZhuanYeCheckView zhuanYeCheckView;
    private QuWeiCheckView quWeiCheckView;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityHeartCheck.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_heart_check);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        backFL = (FrameLayout) findViewById(R.id.backFL);
        zhuanYeLL = (RelativeLayout) findViewById(R.id.zhuanYeRL);
        zhuanYeLineView = findViewById(R.id.zhuanYeLineView);
        quWeiLL = (RelativeLayout) findViewById(R.id.quWeiRL);
        zhuanYeTextView = (TextView) findViewById(R.id.zhuanYeTextView);
        quWeiLineView = findViewById(R.id.quWeiLineView);
        zhuanYeCheckView = (ZhuanYeCheckView) findViewById(R.id.zhuanYeCheckView);
        quWeiTextView = (TextView) findViewById(R.id.quWeiTextView);
        quWeiCheckView = (QuWeiCheckView) findViewById(R.id.quWeiCheckView);
    }

    @Override
    protected void initViews() {
        zhuanYeTextView.setSelected(true);
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
        zhuanYeLL.setOnClickListener(this);
        quWeiLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.zhuanYeRL:
                showZhuanYeView();
                break;
            case R.id.quWeiRL:
                showQuWeiView();
                break;
        }
    }

    private void showZhuanYeView() {
        if (zhuanYeCheckView.getVisibility() == View.VISIBLE) {
            return;
        }
        quWeiCheckView.setVisibility(View.INVISIBLE);
        zhuanYeCheckView.setVisibility(View.VISIBLE);
        zhuanYeTextView.setSelected(true);
        quWeiTextView.setSelected(false);
        zhuanYeLineView.setVisibility(View.VISIBLE);
        quWeiLineView.setVisibility(View.INVISIBLE);
    }

    private void showQuWeiView() {
        if (quWeiCheckView.getVisibility() == View.VISIBLE) {
            return;
        }
        quWeiCheckView.setVisibility(View.VISIBLE);
        zhuanYeCheckView.setVisibility(View.INVISIBLE);
        zhuanYeTextView.setSelected(false);
        quWeiTextView.setSelected(true);
        zhuanYeLineView.setVisibility(View.INVISIBLE);
        quWeiLineView.setVisibility(View.VISIBLE);
    }
}
