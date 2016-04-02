package com.xiaoyu.HeartConsultation.ui.mycenter.order_history;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.background.Account;
import com.xiaoyu.HeartConsultation.background.CommonModel;
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
 * Created by xiaoyu on 2015/7/8.
 */
public class OrderCompView extends FrameLayout implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener, OrderCompItemView.OrderCompItemViewListener {
    private RefreshListView refreshListView;

    private int p = 0;
    private final int SIZE = 50;
    private List<OrderItemModel> models = new ArrayList<OrderItemModel>();
    private MyAdapter myAdapter;

    private ActivityBase activity;
    private Account account;

    public OrderCompView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public OrderCompView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderCompView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        account = HCApplicaton.getInstance().getAccount();
        this.activity = (ActivityBase) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.order_comp_view, this, true);
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
        refreshListView.setCanRefresh(true);
        refreshListView.setCanLoadMore(false);
        refreshListView.setOnLoadListener(this);
        refreshListView.setOnRefreshListener(this);
        myAdapter = new MyAdapter();
        refreshListView.setAdapter(myAdapter);
    }

    @Override
    public void onLoadMore() {
        p++;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", account.userId));
        nameValuePairs.add(new BasicNameValuePair("doctorid", ""));
        nameValuePairs.add(new BasicNameValuePair("type", "1"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_ORDERS, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                p--;
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onLoadMoreComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onLoadMoreComplete();
                OrderListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, OrderListModel.class);
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
        nameValuePairs.add(new BasicNameValuePair("userId", account.userId));
        nameValuePairs.add(new BasicNameValuePair("doctorid", ""));
        nameValuePairs.add(new BasicNameValuePair("type", "1"));
        nameValuePairs.add(new BasicNameValuePair("start_num", p + ""));
        nameValuePairs.add(new BasicNameValuePair("limit", SIZE + ""));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_GET_ORDERS, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                ToastUtil.makeShortText(activity.getString(R.string.refreshFiled));
                refreshListView.onRefreshComplete();
            }

            @Override
            public void onComplete(String response) {
                refreshListView.onRefreshComplete();
                OrderListModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, OrderListModel.class);
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

    @Override
    public void onCommentClicked(OrderItemModel model) {
        ActivityCommentOrder.open(activity,model);
    }

    @Override
    public void onDeleteClicked(final OrderItemModel orderItemModel) {
        activity.showDialog();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", orderItemModel.id));
        Request.doRequest(activity, nameValuePairs, ServerConfig.URL_DELETE_ORDER, Request.GET, new Request.RequestListener() {
            @Override
            public void onException(Request.RequestException e) {
                activity.dismissDialog();
                ToastUtil.makeShortText(getContext().getString(R.string.deleteFiled));
            }

            @Override
            public void onComplete(String response) {
                activity.dismissDialog();

                CommonModel model = HCApplicaton.getInstance().getGson().fromJsonWithNoException(response, CommonModel.class);
                if (model != null && model.result == 1) {
                    ToastUtil.makeShortText(getContext().getString(R.string.deleteSucc));
                    models.remove(orderItemModel);
                    myAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.makeShortText(getContext().getString(R.string.deleteFiled));
                }
            }
        });
    }

    @Override
    public void onContentClicked(OrderItemModel model) {
        ActivityOrderDesc.open(activity, model);
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
            OrderCompItemView itemView = null;
            if (view == null) {
                itemView = new OrderCompItemView(activity, OrderCompView.this);
            } else {
                itemView = (OrderCompItemView) view;
            }
            itemView.setData(models.get(i));
            return itemView;
        }
    }
}
