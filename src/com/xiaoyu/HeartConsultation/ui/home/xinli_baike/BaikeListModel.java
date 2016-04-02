package com.xiaoyu.HeartConsultation.ui.home.xinli_baike;

import com.meilishuo.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class BaikeListModel {
    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public int result;

    @SerializedName("list")
    public List<BaikeItemModel> models;
}
