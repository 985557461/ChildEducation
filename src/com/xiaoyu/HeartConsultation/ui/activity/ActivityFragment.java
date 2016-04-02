package com.xiaoyu.HeartConsultation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaoyu.HeartConsultation.R;
import com.xiaoyu.HeartConsultation.ui.activity.child_dangan.ChildDangAnView;
import com.xiaoyu.HeartConsultation.ui.activity.school_gonggao.SchoolGongGaoView;
import com.xiaoyu.HeartConsultation.ui.activity.school_huodong.SchoolXiaoWaiHuoDongView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2015/7/1.
 */
public class ActivityFragment extends Fragment implements View.OnClickListener {
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
    private SchoolXiaoWaiHuoDongView schoolHuoDongView;
    private ChildDangAnView childDangAnView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment, container, false);
        initViewsAndListeners(view, savedInstanceState);
        return view;
    }

    private void initViewsAndListeners(View view, Bundle savedInstanceState) {
        schoolGongGao = (TextView) view.findViewById(R.id.schoolGongGao);
        schoolHuoDong = (TextView) view.findViewById(R.id.schoolHuoDong);
        childDangAn = (TextView) view.findViewById(R.id.childDangAn);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        schoolGongGao.setOnClickListener(this);
        schoolHuoDong.setOnClickListener(this);
        childDangAn.setOnClickListener(this);
        schoolGongGao.setSelected(true);

//        schoolGongGaoView = new SchoolGongGaoView(getActivity());
        schoolHuoDongView = new SchoolXiaoWaiHuoDongView(getActivity());
//        childDangAnView = new ChildDangAnView(getActivity());

//        views.add(schoolGongGaoView);
        views.add(schoolHuoDongView);
//        views.add(childDangAnView);

        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
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
