package com.xiaoyu.HeartConsultation.ui.home.question_test.quwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.home.QuestionModel;
import com.xiaoyu.HeartConsultation.widget.RefreshListView;
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
 * Created by xiaoyu on 2015/6/16.
 */
public class ActivityQuWeiTestList extends ActivityBase implements View.OnClickListener {
    private FrameLayout backFL;
    private RefreshListView refreshListView;
    private QuWeiTestAdapter quWeiTestAdapter;
    private List<QuestionModel> questionModels = new ArrayList<QuestionModel>();

    /**
     * intent data
     * *
     */
    private static final String kFileName = "file_name";
    private String fileName;

    private QuWeiSAXHandler handler = new QuWeiSAXHandler();

    public static void open(Activity activity, String fileName) {
        Intent intent = new Intent(activity, ActivityQuWeiTestList.class);
        intent.putExtra(kFileName, fileName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileName = getIntent().getStringExtra(kFileName);
        setContentView(R.layout.activity_quwei_test_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        if (!TextUtils.isEmpty(fileName)) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                reader.setContentHandler(handler);
                try {
                    reader.parse(new InputSource(ActivityQuWeiTestList.this.getResources().getAssets().open(fileName)));
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
        backFL = (FrameLayout) findViewById(R.id.backFL);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
    }

    @Override
    protected void initViews() {
        refreshListView.setCanLoadMore(false);
        refreshListView.setCanRefresh(false);
        quWeiTestAdapter = new QuWeiTestAdapter();
        refreshListView.setAdapter(quWeiTestAdapter);
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuWeiTestItemView itemView = (QuWeiTestItemView) view;
                QuestionModel model = itemView.questionModel;
                ActivityQuWeiTopic.open(ActivityQuWeiTestList.this, model);
            }
        });
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


    class QuWeiTestAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return questionModels.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            QuWeiTestItemView itemView = null;
            if (view == null) {
                itemView = new QuWeiTestItemView(ActivityQuWeiTestList.this);
            } else {
                itemView = (QuWeiTestItemView) view;
            }
            itemView.setData(i + 1, questionModels.get(i));
            return itemView;
        }
    }
}
