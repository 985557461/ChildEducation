package com.xiaoyu.HeartConsultation.ui.activity.school_huodong;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import com.xiaoyu.HeartConsultation.widget.RefreshListView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class SchoolXiaoWaiHuoDongView extends FrameLayout implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private RefreshListView refreshListView;

    private int p = 0;
    private final int SIZE = 50;
    private List<HuodongItemModel> models = new ArrayList<HuodongItemModel>();
    private MyAdapter myAdapter;

    private Activity activity;

    public SchoolXiaoWaiHuoDongView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SchoolXiaoWaiHuoDongView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchoolXiaoWaiHuoDongView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.school_huodong_view, this, true);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        refreshListView.setCanRefresh(true);
        refreshListView.setCanLoadMore(false);
        refreshListView.setOnLoadListener(this);
        refreshListView.setOnRefreshListener(this);
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HuoDongItemView itemView = (HuoDongItemView) view;
                if (itemView.model != null) {
                    ActivitySchoolHuoDongDesc.open(activity,itemView.model);
                }
            }
        });
        myAdapter = new MyAdapter();
        refreshListView.setAdapter(myAdapter);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        p++;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("type", "2"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_ACTIVITIES, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                p--;
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onLoadMoreComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onLoadMoreComplete();
                HuodongListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, HuodongListModel.class);
                if (model != null && model.result == 1) {
                    if (model.list != null && model.list.size() > 0) {
                        if (model.list.size() < SIZE) {//说明没有了
                            refreshListView.setCanLoadMore(false);
                        } else {
                            refreshListView.setCanLoadMore(true);
                        }
                        models.addAll(model.list);
                        myAdapter.notifyDataSetChanged();
                    }
                } else {
                    p--;
                    ToastUtil.makeShortText(activity.getString(R.string.noData));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        p = 0;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("type", "2"));//wai
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_ACTIVITIES, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onRefreshComplete();
                HuodongListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, HuodongListModel.class);
                if (model != null && model.result == 1) {
                    if (model.list != null && model.list.size() > 0) {
                        if (model.list.size() < SIZE) {//说明没有了
                            refreshListView.setCanLoadMore(false);
                        } else {
                            refreshListView.setCanLoadMore(true);
                        }
                        models.clear();
                        models.addAll(model.list);
                        myAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtil.makeShortText(activity.getString(R.string.noData));
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return models.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HuoDongItemView itemView = null;
            if (view == null) {
                itemView = new HuoDongItemView(activity);
            } else {
                itemView = (HuoDongItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
