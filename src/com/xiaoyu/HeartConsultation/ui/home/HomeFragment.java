package com.xiaoyu.HeartConsultation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.KeyValue;
import com.xiaoyu.HeartConsultation.ui.activity.ActivityGuanLianAboutSchool;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.ActivitySchoolHuoDongDesc;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.HuodongItemModel;
import com.xiaoyu.HeartConsultation.ui.home.question_test.ActivityHeartCheck;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.ActivityBaiKeDesc;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.ActivityXinLiBaike;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.BaikeItemModel;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.BaikeListModel;
import com.xiaoyu.HeartConsultation.util.ImageLoaderUtil;
import com.xiaoyu.HeartConsultation.util.Request;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/1.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout xinLiCePing;
    private LinearLayout xinLiBaiKe;
    private LinearLayout xinLiFuDao;
    private LinearLayout guanLianXueXiao;
    private LinearLayout bkhdLL;
    private TextView newBaike;
    private LinearLayout baikeLL;
    private TextView baikeTitle;
    private ImageView baikeImage;
    private TextView newHuodong;
    private LinearLayout huoDongLL;
    private TextView huodongTitle;
    private ImageView huodongImage;

    private BaikeListModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        xinLiCePing = (LinearLayout) view.findViewById(R.id.xinLiCePing);
        xinLiBaiKe = (LinearLayout) view.findViewById(R.id.xinLiBaiKe);
        xinLiFuDao = (LinearLayout) view.findViewById(R.id.xinLiFuDao);
        guanLianXueXiao = (LinearLayout) view.findViewById(R.id.guanLianXueXiao);
        newBaike = (TextView) view.findViewById(R.id.newBaike);
        bkhdLL = (LinearLayout) view.findViewById(R.id.bkhdLL);
        baikeLL = (LinearLayout) view.findViewById(R.id.baikeLL);
        baikeTitle = (TextView) view.findViewById(R.id.baikeTitle);
        baikeImage = (ImageView) view.findViewById(R.id.baikeImage);
        newHuodong = (TextView) view.findViewById(R.id.newHuodong);
        huoDongLL = (LinearLayout) view.findViewById(R.id.huoDongLL);
        huodongTitle = (TextView) view.findViewById(R.id.huodongTitle);
        huodongImage = (ImageView) view.findViewById(R.id.huodongImage);

        xinLiCePing.setOnClickListener(this);
        xinLiBaiKe.setOnClickListener(this);
        xinLiFuDao.setOnClickListener(this);
        guanLianXueXiao.setOnClickListener(this);
        baikeLL.setOnClickListener(this);
        huoDongLL.setOnClickListener(this);

        queryBaikeAndHuoDong();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xinLiCePing:
                ActivityHeartCheck.open(getActivity());
                break;
            case R.id.xinLiBaiKe:
                ActivityXinLiBaike.open(getActivity());
                break;
            case R.id.xinLiFuDao:
                ActivityDoctorList.open(getActivity());
                break;
            case R.id.guanLianXueXiao:
                Account account = HCApplicaton.getInstance().getAccount();
                if (!TextUtils.isEmpty(account.stuNo)) {
                    Intent intent = new Intent(getActivity(), ActivityGuanLianAboutSchool.class);
                    intent.putExtra(KeyValue.kFrom, KeyValue.ToDangAn);
                    getActivity().startActivity(intent);
                } else {
                    ActivityGuanLianXueXiao.open(getActivity());
                }
                break;
            case R.id.baikeLL: {
                BaikeItemModel itemModel = model.models.get(0);
                ActivityBaiKeDesc.open(getActivity(), itemModel);
            }
            break;
            case R.id.huoDongLL: {
                BaikeItemModel itemModel = model.models.get(1);
                HuodongItemModel huodongItemModel = new HuodongItemModel();
                huodongItemModel.copy(itemModel);
                ActivitySchoolHuoDongDesc.open(getActivity(), huodongItemModel);
            }
            break;
        }
    }

    private void queryBaikeAndHuoDong() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Request.doRequest(getActivity(), nameValuePairs, ServerConfig.URL_QUERY_ACTIVITIES, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
            }

            @Override
            public void onComplete(String response) {
                model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, BaikeListModel.class);
                if (model != null && model.result == 1) {
                    if (model.models != null && model.models.size() > 0) {
                        updateViews();
                    }
                } else {
                    hideViews();
                }
            }
        });
    }

    private void updateViews() {
        bkhdLL.setVisibility(View.VISIBLE);
        //only baike
        if (model.models.size() == 1) {
            newBaike.setVisibility(View.VISIBLE);
            baikeLL.setVisibility(View.VISIBLE);
            baikeTitle.setVisibility(View.VISIBLE);
            baikeImage.setVisibility(View.VISIBLE);
            newHuodong.setVisibility(View.GONE);
            huoDongLL.setVisibility(View.GONE);

            BaikeItemModel itemModel = model.models.get(0);
            if (!TextUtils.isEmpty(itemModel.title)) {
                baikeTitle.setText(itemModel.title);
            }
            if (!TextUtils.isEmpty(itemModel.imageurl)) {
                HCApplicaton.getInstance().getImageLoader().displayImage(itemModel.imageurl, baikeImage, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
        } else if (model.models.size() == 2) {
            newBaike.setVisibility(View.VISIBLE);
            baikeLL.setVisibility(View.VISIBLE);
            baikeTitle.setVisibility(View.VISIBLE);
            baikeImage.setVisibility(View.VISIBLE);
            newHuodong.setVisibility(View.VISIBLE);
            huoDongLL.setVisibility(View.VISIBLE);
            huodongTitle.setVisibility(View.VISIBLE);
            huodongImage.setVisibility(View.VISIBLE);

            BaikeItemModel itemModel = model.models.get(0);
            if (!TextUtils.isEmpty(itemModel.title)) {
                baikeTitle.setText(itemModel.title);
            }
            if (!TextUtils.isEmpty(itemModel.imageurl)) {
                HCApplicaton.getInstance().getImageLoader().displayImage(itemModel.imageurl, baikeImage, ImageLoaderUtil.Options_Common_Disc_Pic);
            }

            BaikeItemModel itemModel1 = model.models.get(1);
            if (!TextUtils.isEmpty(itemModel1.title)) {
                huodongTitle.setText(itemModel1.title);
            }
            if (!TextUtils.isEmpty(itemModel1.imageurl)) {
                HCApplicaton.getInstance().getImageLoader().displayImage(itemModel1.imageurl, huodongImage, ImageLoaderUtil.Options_Common_Disc_Pic);
            }
        }
    }

    private void hideViews() {
        bkhdLL.setVisibility(View.GONE);
    }
}
