package com.xiaoyu.HeartConsultation.ui.activity.school_gonggao;

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
public class SchoolGongGaoView extends FrameLayout implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private RefreshListView refreshListView;

    private int p = 0;
    private final int SIZE = 50;
    private List<GongGaoItemModel> models = new ArrayList<GongGaoItemModel>();
    private MyAdapter myAdapter;

    private Activity activity;

    public SchoolGongGaoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SchoolGongGaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchoolGongGaoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.school_gonggao_view, this, true);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        refreshListView.setCanRefresh(true);
        refreshListView.setCanLoadMore(false);
        refreshListView.setOnLoadListener(this);
        refreshListView.setOnRefreshListener(this);
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GongGaoItemView itemView = (GongGaoItemView) view;
                if (itemView.model != null) {
                    ActivitySchoolGongGaoDesc.open(activity, itemView.model);
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
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_GONGGAO, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                p--;
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onLoadMoreComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onLoadMoreComplete();
                GongGaoListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, GongGaoListModel.class);
                if (model != null && model.result == 1) {
                    if (model.models != null && model.models.size() > 0) {
                        if (model.models.size() < SIZE) {//说明没有了
                            refreshListView.setCanLoadMore(false);
                        } else {
                            refreshListView.setCanLoadMore(true);
                        }
                        models.addAll(model.models);
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
        nameValuePairs.add(new BasicNameValuePair("type", "2"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_GONGGAO, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onRefreshComplete();
                GongGaoListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, GongGaoListModel.class);
                if (model != null && model.result == 1) {
                    if (model.models != null && model.models.size() > 0) {
                        if (model.models.size() < SIZE) {//说明没有了
                            refreshListView.setCanLoadMore(false);
                        } else {
                            refreshListView.setCanLoadMore(true);
                        }
                        models.clear();
                        models.addAll(model.models);
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
            GongGaoItemView itemView = null;
            if (view == null) {
                itemView = new GongGaoItemView(activity);
            } else {
                itemView = (GongGaoItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
