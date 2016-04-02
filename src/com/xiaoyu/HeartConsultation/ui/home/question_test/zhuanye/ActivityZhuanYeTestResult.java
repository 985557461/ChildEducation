package com.xiaoyu.HeartConsultation.ui.home.question_test.zhuanye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;

/**
 * Created by xiaoyu on 2015/8/10.
 */
public class ActivityZhuanYeTestResult extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private TextView content;
    private TextView scoreTextView;

    private int score;
    private String result;
    private static final String kScore = "score";
    private static final String kResult = "result";

    public static void open(Activity activity, int score, String result) {
        Intent intent = new Intent(activity, ActivityZhuanYeTestResult.class);
        intent.putExtra(kScore, score);
        intent.putExtra(kResult, result);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        score = getIntent().getIntExtra(kScore, 0);
        result = getIntent().getStringExtra(kResult);
        setContentView(R.layout.activity_zhuanye_test_result);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
        content = (TextView) findViewById(R.id.content);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
    }

    @Override
    protected void initViews() {
        scoreTextView.setText(getString(R.string.youscore) + score);
        if (TextUtils.isEmpty(result)) {
            content.setText("");
        } else {
            content.setText(result);
        }
    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
