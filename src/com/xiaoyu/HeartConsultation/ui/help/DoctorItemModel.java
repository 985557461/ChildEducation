package com.xiaoyu.HeartConsultation.ui.help;

import com.meilishuo.gson.annotations.SerializedName;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class DoctorItemModel {
    @SerializedName("doctor_name")
    public String doctor_name;
    @SerializedName("doctorid")
    public String doctorid;
    @SerializedName("doctor_title")
    public String doctor_title;
    @SerializedName("isrecomm")
    public String isrecomm;
    @SerializedName("good_at")
    public String good_at;
    @SerializedName("brief")
    public String brief;//jianjie
    @SerializedName("section")
    public String section;
    @SerializedName("hospital")
    public String hospital;
    @SerializedName("doctor_avatar")
    public String doctor_avatar;
    @SerializedName("info")
    public String info;
}
