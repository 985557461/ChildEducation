package com.xiaoyu.HeartConsultation.ui.activity.child_dangan;

import com.meilishuo.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2015/7/31.
 */
public class YearEvaluation {
    @SerializedName("year")
    public String year;
    @SerializedName("evalSetpds")
    public List<DangAn> evalSetpds;
}
