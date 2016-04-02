package com.xiaoyu.HeartConsultation.ui.home.question_test.quwei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;

/**
 * Created by xiaoyu on 2015/7/14.
 */
public class ChoiceAnswerItemView extends FrameLayout {
    private TextView choiceIndex;
    private TextView choiceContent;
    private TextView choiceAnswer;

    public ChoiceAnswerItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ChoiceAnswerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChoiceAnswerItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.choice_answer_item_view, this, true);
        choiceIndex = (TextView) findViewById(R.id.choiceIndex);
        choiceContent = (TextView) findViewById(R.id.choiceContent);
        choiceAnswer = (TextView) findViewById(R.id.choiceAnswer);
    }

    public void setData(ChoiceModel choiceModel) {
        choiceIndex.setText(choiceModel.choiceIndex);
        choiceContent.setText(choiceModel.choiceContent);
        choiceAnswer.setText(choiceModel.choiceAnswer);
    }
}
