package com.xiaoyu.HeartConsultation.ui.activity.school_gonggao;

import com.meilishuo.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class GongGaoListModel {
    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public int result;

    @SerializedName("list")
    public List<GongGaoItemModel> models;
}
