package com.xiaoyu.HeartConsultation.ui.home.question_test.quwei;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.home.QuestionModel;

/**
 * Created by xiaoyu on 2015/6/16.
 */
public class QuWeiTestItemView extends FrameLayout {
    private TextView indexNum;
    private TextView topic;

    private Activity activity;
    public QuestionModel questionModel;

    public QuWeiTestItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public QuWeiTestItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuWeiTestItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.quwei_test_item_view, this, true);

        indexNum = (TextView) findViewById(R.id.indexNum);
        topic = (TextView) findViewById(R.id.topic);
    }

    public void setData(int index, QuestionModel questionModel) {
        this.questionModel = questionModel;
        indexNum.setText(index + "");
        topic.setText(questionModel.title);
    }
}
