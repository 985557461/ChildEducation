package com.xiaoyu.HeartConsultation.ui.mycenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.account.ActivityLogin;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.ActivitySchoolHuoDongDesc;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.HuoDongItemView;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.HuodongItemModel;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.HuodongListModel;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import com.xiaoyu.HeartConsultation.widget.RefreshListView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/19.
 */
public class ActivityMyEverBaoMingHuoDong extends ActivityBase implements View.OnClickListener, RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private FrameLayout back;
    private RefreshListView refreshListView;

    private int p = 0;
    private final int SIZE = 50;
    private List<HuodongItemModel> models = new ArrayList<HuodongItemModel>();
    private MyAdapter myAdapter;
    private Account account;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityMyEverBaoMingHuoDong.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_ever_baoming_huodong);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getViews() {
        account = HCApplicaton.getInstance().getAccount();
        back = (FrameLayout) findViewById(R.id.back);
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
                    ActivitySchoolHuoDongDesc.open(ActivityMyEverBaoMingHuoDong.this, itemView.model);
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
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }

    @Override
    public void onLoadMore() {
        p++;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", account.userId));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_QUERY_MY_ACTIVITIES, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                p--;
                ToastUtil.makeShortText(getString(R.string.refreshFiled));
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
                    ToastUtil.makeShortText(getString(R.string.noData));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        if (TextUtils.isEmpty(account.userId)) {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            return;
        }
        p = 0;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", account.userId));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(this, nameValuePairs, ServerConfig.URL_QUERY_MY_ACTIVITIES, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(getString(R.string.refreshFiled));
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
            HuoDongItemView itemView = null;
            if (view == null) {
                itemView = new HuoDongItemView(ActivityMyEverBaoMingHuoDong.this);
            } else {
                itemView = (HuoDongItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
