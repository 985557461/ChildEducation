package com.xiaoyu.HeartConsultation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.ActivityBase;
import com.xiaoyu.HeartConsultation.ui.KeyValue;
import com.xiaoyu.HeartConsultation.ui.activity.child_dangan.ChildDangAnView;
import com.xiaoyu.HeartConsultation.ui.activity.school_gonggao.SchoolGongGaoView;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.SchoolXiaoNeiHuoDongView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/8/19.
 */
public class ActivityGuanLianAboutSchool extends ActivityBase implements View.OnClickListener {
    private TextView schoolGongGao;
    private TextView schoolHuoDong;
    private TextView childDangAn;
    private ViewPager viewPager;

    /**
     * viewpager about
     * *
     */
    private MyPagerAdapter myPagerAdapter;
    private List<View> views = new ArrayList<View>();
    private SchoolGongGaoView schoolGongGaoView;
    private SchoolXiaoNeiHuoDongView schoolHuoDongView;
    private ChildDangAnView childDangAnView;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityGuanLianAboutSchool.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guanlian_about_school);
        super.onCreate(savedInstanceState);
        String tab = getIntent().getStringExtra(KeyValue.kFrom);
        if (!TextUtils.isEmpty(tab) && tab.equals(KeyValue.ToDangAn)) {
            showTab(2);
        }
    }

    @Override
    protected void getViews() {
        schoolGongGao = (TextView) findViewById(R.id.schoolGongGao);
        schoolHuoDong = (TextView) findViewById(R.id.schoolHuoDong);
        childDangAn = (TextView) findViewById(R.id.childDangAn);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initViews() {
        schoolGongGao.setSelected(true);

        schoolGongGaoView = new SchoolGongGaoView(this);
        schoolHuoDongView = new SchoolXiaoNeiHuoDongView(this);
        childDangAnView = new ChildDangAnView(this);

        views.add(schoolGongGaoView);
        views.add(schoolHuoDongView);
        views.add(childDangAnView);

        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    protected void setListeners() {
        schoolGongGao.setOnClickListener(this);
        schoolHuoDong.setOnClickListener(this);
        childDangAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schoolGongGao:
                schoolGongGao.setSelected(true);
                schoolHuoDong.setSelected(false);
                childDangAn.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.schoolHuoDong:
                schoolGongGao.setSelected(false);
                schoolHuoDong.setSelected(true);
                childDangAn.setSelected(false);
                viewPager.setCurrentItem(1);
                break;
            case R.id.childDangAn:
                schoolGongGao.setSelected(false);
                schoolHuoDong.setSelected(false);
                childDangAn.setSelected(true);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    public void showTab(int tab) {
        if (tab == 0) {
            schoolGongGao.setSelected(true);
            schoolHuoDong.setSelected(false);
            childDangAn.setSelected(false);
            viewPager.setCurrentItem(0);
        } else if (tab == 1) {
            schoolGongGao.setSelected(false);
            schoolHuoDong.setSelected(true);
            childDangAn.setSelected(false);
            viewPager.setCurrentItem(1);
        } else if (tab == 2) {
            schoolGongGao.setSelected(false);
            schoolHuoDong.setSelected(false);
            childDangAn.setSelected(true);
            viewPager.setCurrentItem(2);
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    schoolGongGao.setSelected(true);
                    schoolHuoDong.setSelected(false);
                    childDangAn.setSelected(false);
                    break;
                case 1:
                    schoolGongGao.setSelected(false);
                    schoolHuoDong.setSelected(true);
                    childDangAn.setSelected(false);
                    break;
                case 2:
                    schoolGongGao.setSelected(false);
                    schoolHuoDong.setSelected(false);
                    childDangAn.setSelected(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
