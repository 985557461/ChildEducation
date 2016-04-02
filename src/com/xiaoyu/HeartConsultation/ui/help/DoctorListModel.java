package com.xiaoyu.HeartConsultation.ui.help;

import com.meilishuo.gson.annotations.SerializedName;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.BaikeItemModel;

import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class DoctorListModel {
    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public int result;

    @SerializedName("doctorinfo")
    public List<DoctorItemModel> list;
}
