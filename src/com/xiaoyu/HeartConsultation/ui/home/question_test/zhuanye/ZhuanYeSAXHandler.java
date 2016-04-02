package com.xiaoyu.HeartConsultation.ui.home.question_test.zhuanye;

import com.xiaoyu.HeartConsultation.ui.home.QuestionModel;
import com.xiaoyu.HeartConsultation.ui.home.question_test.quwei.ChoiceModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/13.
 */
public class ZhuanYeSAXHandler extends DefaultHandler {
    private List<QuestionModel> questionModels = new ArrayList<QuestionModel>();
    private QuestionModel questionModel;
    private ChoiceModel choiceModel;
    private String localName;
    private String result = "";
    private StringBuilder sb = new StringBuilder();

    public List<QuestionModel> getQuestionModels() {
        return questionModels;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.localName = localName;
        sb.setLength(0);
        if ("question".equals(localName)) {
            questionModel = new QuestionModel();
        }
        if ("choice".equals(localName)) {
            choiceModel = new ChoiceModel();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        if ("result".equals(localName)) {
            sb.append(value);
        } else if ("title".equals(localName)) {
            sb.append(value);
        } else if ("content".equals(localName)) {
            sb.append(value);
        } else if ("choiceIndex".equals(localName)) {
            sb.append(value);
        } else if ("choiceContent".equals(localName)) {
            sb.append(value);
        } else if ("choiceAnswer".equals(localName)) {
            sb.append(value);
        } else if ("score".equals(localName)) {
            sb.append(value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("question".equals(localName)) {
            questionModels.add(questionModel);
        }
        if ("choice".equals(localName)) {
            questionModel.choiceModels.add(choiceModel);
        }
        if ("result".equals(localName)) {
            result = sb.toString();
        } else if ("title".equals(localName)) {
            questionModel.title = sb.toString();
        } else if ("content".equals(localName)) {
            questionModel.content = sb.toString();
        } else if ("choiceIndex".equals(localName)) {
            choiceModel.choiceIndex = sb.toString();
        } else if ("choiceContent".equals(localName)) {
            choiceModel.choiceContent = sb.toString();
        } else if ("choiceAnswer".equals(localName)) {
            choiceModel.choiceAnswer = sb.toString();
        } else if ("score".equals(localName)) {
            choiceModel.score = Integer.valueOf(sb.toString());
        }
    }
}
