package com.xiaoyu.HeartConsultation.ui.home.question_test.quwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.home.QuestionModel;

/**
 * Created by xiaoyu on 2015/6/16.
 */
public class ActivityQuWeiTopic extends ActivityBase implements View.OnClickListener {
    private FrameLayout backFL;
    private TextView topicName;
    private TextView topicContent;
    private LinearLayout choiceContainer;

    private static QuestionModel destQuestionModel;
    private QuestionModel questionModel;

    public static void open(Activity activity, QuestionModel questionModel) {
        destQuestionModel = questionModel;
        Intent intent = new Intent(activity, ActivityQuWeiTopic.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        questionModel = destQuestionModel;
        destQuestionModel = null;
        setContentView(R.layout.activity_quwei_topic);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        backFL = (FrameLayout) findViewById(R.id.backFL);
        topicName = (TextView) findViewById(R.id.topicName);
        topicContent = (TextView) findViewById(R.id.topicContent);
        choiceContainer = (LinearLayout) findViewById(R.id.choiceContainer);
    }

    @Override
    protected void initViews() {
        topicName.setText(questionModel.title);
        topicContent.setText(questionModel.content);
        for (int i = 0; i < questionModel.choiceModels.size(); i++) {
            ChoiceItemView item = new ChoiceItemView(this);
            item.setData(questionModel.choiceModels.get(i));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChoiceItemView itemView = (ChoiceItemView) view;
                    ActivityQuWeiTopicResult.open(ActivityQuWeiTopic.this, questionModel, itemView.choiceModel.choiceIndex);
                }
            });
            choiceContainer.addView(item);
        }
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backFL) {
            finish();
        }
    }
}
