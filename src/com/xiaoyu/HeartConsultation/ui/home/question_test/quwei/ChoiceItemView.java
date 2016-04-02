package com.xiaoyu.HeartConsultation.ui.home.question_test.quwei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;

/**
 * Created by xiaoyu on 2015/6/16.
 */
public class ChoiceItemView extends FrameLayout {
    private TextView choiceIndex;
    private TextView choiceContent;
    private LinearLayout containerLL;

    public ChoiceModel choiceModel;

    public ChoiceItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ChoiceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChoiceItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.choice_item_view, this, true);
        containerLL = (LinearLayout) findViewById(R.id.containerLL);
        choiceIndex = (TextView) findViewById(R.id.choiceIndex);
        choiceContent = (TextView) findViewById(R.id.choiceContent);
    }

    public void setData(ChoiceModel choiceModel) {
        this.choiceModel = choiceModel;
        choiceIndex.setText(choiceModel.choiceIndex);
        choiceContent.setText(choiceModel.choiceContent);
    }
}
