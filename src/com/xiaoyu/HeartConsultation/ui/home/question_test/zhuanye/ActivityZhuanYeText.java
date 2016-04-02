package com.xiaoyu.HeartConsultation.ui.home.question_test.zhuanye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.home.QuestionModel;
import com.xiaoyu.HeartConsultation.ui.home.question_test.quwei.ChoiceItemView;
import com.xiaoyu.HeartConsultation.ui.home.question_test.quwei.ChoiceModel;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/8/10.
 */
public class ActivityZhuanYeText extends ActivityBase implements View.OnClickListener {
    private FrameLayout backFL;
    private TextView topicName;
    private TextView topicContent;
    private LinearLayout choiceContainer;
    private List<QuestionModel> questionModels = new ArrayList<QuestionModel>();

    /**
     * intent data
     * *
     */
    private static final String kFileName = "file_name";
    private String fileName;

    private ZhuanYeSAXHandler handler = new ZhuanYeSAXHandler();
    private int index = 0;
    private int totalScore = 0;

    public static void open(Activity activity, String fileName) {
        Intent intent = new Intent(activity, ActivityZhuanYeText.class);
        intent.putExtra(kFileName, fileName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileName = getIntent().getStringExtra(kFileName);
        setContentView(R.layout.activity_zhuanye_test);
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
        if (!TextUtils.isEmpty(fileName)) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                reader.setContentHandler(handler);
                try {
                    reader.parse(new InputSource(ActivityZhuanYeText.this.getResources().getAssets().open(fileName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            questionModels = handler.getQuestionModels();
        }
        initTopic();
    }

    private void initTopic() {
        if (index >= questionModels.size()) {
            ActivityZhuanYeTestResult.open(this, totalScore, handler.getResult());
            this.finish();
            return;
        }
        choiceContainer.removeAllViews();
        topicName.setText(questionModels.get(index).title);
        topicContent.setText(questionModels.get(index).content);
        for (int i = 0; i < questionModels.get(index).choiceModels.size(); i++) {
            final ChoiceItemView item = new ChoiceItemView(this);
            item.setData(questionModels.get(index).choiceModels.get(i));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChoiceItemView itemView = (ChoiceItemView) view;
                    ChoiceModel choiceModel = itemView.choiceModel;
                    totalScore += choiceModel.score;
                    index++;
                    initTopic();
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
