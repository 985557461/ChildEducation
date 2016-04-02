package com.xiaoyu.HeartConsultation.ui.home.xinli_baike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import com.xiaoyu.HeartConsultation.widget.RefreshListView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/18.
 */
public class ActivityXinLiBaike extends ActivityBase implements View.OnClickListener, RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private RefreshListView refreshListView;
    private FrameLayout back;

    private int p = 0;
    private final int SIZE = 50;
    private List<BaikeItemModel> models = new ArrayList<BaikeItemModel>();
    private MyAdapter myAdapter;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityXinLiBaike.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xinli_baike);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        back = (FrameLayout) findViewById(R.id.back);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        refreshListView.setCanRefresh(true);
        refreshListView.setCanLoadMore(false);
        refreshListView.setOnLoadListener(this);
        refreshListView.setOnRefreshListener(this);
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaiKeItemView itemView = (BaiKeItemView) view;
                if (itemView.model != null) {
                    ActivityBaiKeDesc.open(ActivityXinLiBaike.this, itemView.model);
                }
            }
        });
        myAdapter = new MyAdapter();
        refreshListView.setAdapter(myAdapter);
        onRefresh();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }

    @Override
    public void onLoadMore() {
        p++;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("type", "3"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_GET_XINLIBAIKE, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                p--;
                ToastUtil.makeShortText(getString(R.string.refreshFiled));
                refreshListView.onLoadMoreComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onLoadMoreComplete();
                BaikeListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, BaikeListModel.class);
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
                    ToastUtil.makeShortText(getString(R.string.noData));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        p = 0;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("type", "3"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_GET_XINLIBAIKE, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(getString(R.string.refreshFiled));
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onRefreshComplete();
                BaikeListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, BaikeListModel.class);
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
                    ToastUtil.makeShortText(getString(R.string.noData));
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
            BaiKeItemView itemView = null;
            if (view == null) {
                itemView = new BaiKeItemView(ActivityXinLiBaike.this);
            } else {
                itemView = (BaiKeItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
