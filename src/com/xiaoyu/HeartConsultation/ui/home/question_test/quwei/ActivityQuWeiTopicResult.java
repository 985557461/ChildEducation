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
 * Created by xiaoyu on 2015/6/17.
 */
public class ActivityQuWeiTopicResult extends ActivityBase implements View.OnClickListener {
    private FrameLayout backFL;
    private TextView choiceIndex;
    private TextView choiceContent;
    private TextView choiceAnswer;
    private LinearLayout container;

    /**
     * intent data
     * *
     */
    private static QuestionModel destQuestionModel;
    private QuestionModel questionModel;
    private static String kChoiceIndex = "choice_index";
    private String choiceIndexStr;

    public static void open(Activity activity, QuestionModel questionModel, String choiceIndexStr) {
        destQuestionModel = questionModel;
        Intent intent = new Intent(activity, ActivityQuWeiTopicResult.class);
        intent.putExtra(kChoiceIndex, choiceIndexStr);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        questionModel = destQuestionModel;
        destQuestionModel = null;
        choiceIndexStr = getIntent().getStringExtra(kChoiceIndex);
        setContentView(R.layout.activity_quwei_topic_result);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        backFL = (FrameLayout) findViewById(R.id.backFL);
        choiceIndex = (TextView) findViewById(R.id.choiceIndex);
        choiceContent = (TextView) findViewById(R.id.choiceContent);
        choiceAnswer = (TextView) findViewById(R.id.choiceAnswer);
        container = (LinearLayout) findViewById(R.id.container);
    }

    @Override
    protected void initViews() {
        choiceIndex.setText(choiceIndexStr);
        for (int i = 0; i < questionModel.choiceModels.size(); i++) {
            if (questionModel.choiceModels.get(i).choiceIndex.equals(choiceIndexStr)) {
                choiceContent.setText(questionModel.choiceModels.get(i).choiceContent);
                choiceAnswer.setText(questionModel.choiceModels.get(i).choiceAnswer);
            } else {
                ChoiceAnswerItemView itemView = new ChoiceAnswerItemView(this);
                itemView.setData(questionModel.choiceModels.get(i));
                container.addView(itemView);
            }
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
