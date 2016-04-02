package com.xiaoyu.HeartConsultation.ui.mycenter.order_history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.CommonModel;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/27.
 */
public class ActivityCommentOrder extends ActivityBase implements View.OnClickListener {
    private FrameLayout back;
    private TextView comment;
    private ImageView avatar;
    private TextView name;
    private EditText content;
    private ImageLoader imageLoader;
    private Account account;

    private static OrderItemModel destOrderItemModel;
    private OrderItemModel orderItemModel;

    public static void open(Activity activity, OrderItemModel orderItemModel) {
        destOrderItemModel = orderItemModel;
        Intent intent = new Intent(activity, ActivityCommentOrder.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orderItemModel = destOrderItemModel;
        destOrderItemModel = null;
        setContentView(R.layout.activity_comment_order);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        account = HCApplicaton.getInstance().getAccount();
        imageLoader = HCApplicaton.getInstance().getImageLoader();
        back = (FrameLayout) findViewById(R.id.back);
        comment = (TextView) findViewById(R.id.comment);
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        content = (EditText) findViewById(R.id.content);
    }

    @Override
    protected void initViews() {
        if (orderItemModel != null) {
            if (!TextUtils.isEmpty(orderItemModel.doctorimg)) {
                imageLoader.displayImage(orderItemModel.doctorimg, avatar, ImageLoaderUtil.Options_Common_memory_Pic);
            } else {
                imageLoader.displayImage("", avatar, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
            if (!TextUtils.isEmpty(orderItemModel.dotorname)) {
                name.setText(orderItemModel.dotorname);
            }
        }
    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comment:
                tryToComment();
                break;
        }
    }

    private void tryToComment() {
        //http://182.92.227.113/xiaoxinli/api//export/appraise.do?userid=862972&doctorid=0&evaluate=111&orderid=1
        String contentStr = content.getText().toString();
        if (TextUtils.isEmpty(contentStr)) {
            ToastUtil.makeShortText(getString(R.string.commentNotNull));
            return;
        }
        showDialog();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", account.userId));
        nameValuePairs.add(new BasicNameValuePair("doctorid", orderItemModel.doctorid));
        nameValuePairs.add(new BasicNameValuePair("orderid", orderItemModel.id));
        nameValuePairs.add(new BasicNameValuePair("evaluate", contentStr));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_COMMENT_ORDER, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                dismissDialog();
                ToastUtil.makeShortText(getResources().getString(R.string.commentFiled));
            }

            @Override
            public void onComplete(String response) {
                dismissDialog();
                CommonModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (model != null && model.result == 1) {
                    ToastUtil.makeShortText(getString(R.string.commentSucc));
                    finish();
                } else {
                    ToastUtil.makeShortText(getResources().getString(R.string.commentFiled));
                }
            }
        });
    }
}
