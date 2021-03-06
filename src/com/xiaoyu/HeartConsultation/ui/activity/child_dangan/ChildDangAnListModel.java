package com.xiaoyu.HeartConsultation.ui.activity.child_dangan;

import com.meilishuo.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class ChildDangAnListModel {
    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public int result;

    @SerializedName("stuUserInfo")
    public StuUserInfo stuUserInfo;

    @SerializedName("yearEvaluations")
    public List<YearEvaluation> models;
}
