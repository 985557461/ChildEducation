package com.xiaoyu.HeartConsultation.ui.activity.child_dangan;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.HCApplicaton;
import com.xiaoyu.HeartConsultation.background.config.ServerConfig;
import com.xiaoyu.HeartConsultation.util.Request;
import com.xiaoyu.HeartConsultation.util.ToastUtil;
import com.xiaoyu.HeartConsultation.widget.RefreshListView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/8.
 */
public class ChildDangAnView extends FrameLayout implements RefreshListView.OnRefreshListener {
    private RefreshListView refreshListView;

    private List<DangAn> models = new ArrayList<DangAn>();
    private MyAdapter myAdapter;

    private Activity activity;

    public ChildDangAnView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ChildDangAnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChildDangAnView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.child_dangan_view, this, true);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        refreshListView.setCanRefresh(true);
        refreshListView.setCanLoadMore(false);
        refreshListView.setOnRefreshListener(this);
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChildDangAnItemView itemView = (ChildDangAnItemView) view;
                if (itemView.model != null) {
                    //todo
                }
            }
        });
        myAdapter = new MyAdapter();
        refreshListView.setAdapter(myAdapter);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        Account account = HCApplicaton.getInstance().getAccount();
        if (TextUtils.isEmpty(account.stuNo)) {
            return;
        }
        String finalpasswd = new SimpleHash("SHA-1", account.stuNo, account.stuPassWd).toString();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("stuNo", account.stuNo));
        nameValuePairs.add(new BasicNameValuePair("stuPassWd", finalpasswd));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_DANGAN_LIST, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onRefreshComplete();
                ChildDangAnListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, ChildDangAnListModel.class);
                if (model != null && model.result == 1) {
                    if (model.models != null && model.models.size() > 0) {
                        models.clear();
                        for (int i = 0; i < model.models.size(); i++) {
                            YearEvaluation yearEvaluation = model.models.get(i);
                            if (yearEvaluation.evalSetpds != null && yearEvaluation.evalSetpds.size() > 0) {
                                models.addAll(yearEvaluation.evalSetpds);
                            }
                        }
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
            ChildDangAnItemView itemView = null;
            if (view == null) {
                itemView = new ChildDangAnItemView(activity);
            } else {
                itemView = (ChildDangAnItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
