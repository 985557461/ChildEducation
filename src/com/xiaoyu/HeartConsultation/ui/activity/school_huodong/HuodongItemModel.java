package com.xiaoyu.HeartConsultation.ui.activity.school_huodong;

import com.meilishuo.gson.annotations.SerializedName;
import com.xiaoyu.HeartConsultation.ui.home.xinli_baike.BaikeItemModel;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class HuodongItemModel {
    @SerializedName("address")
    public String address;
    @SerializedName("content")
    public String content;
    @SerializedName("id")
    public String id;
    @SerializedName("imageurl")
    public String imageurl;
    @SerializedName("readcount")
    public String readcount;
    @SerializedName("time")
    public String time;
    @SerializedName("title")
    public String title;

    public void copy(BaikeItemModel itemModel){
        this.address = itemModel.address;
        this.content = itemModel.content;
        this.id = itemModel.id;
        this.imageurl = itemModel.imageurl;
        this.readcount = itemModel.readcount;
        this.time = itemModel.time;
        this.title = itemModel.title;
    }
}
