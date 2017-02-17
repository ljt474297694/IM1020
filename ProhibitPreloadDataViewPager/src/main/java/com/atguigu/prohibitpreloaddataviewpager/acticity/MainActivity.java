package com.atguigu.prohibitpreloaddataviewpager.acticity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.atguigu.prohibitpreloaddataviewpager.R;
import com.atguigu.prohibitpreloaddataviewpager.base.BaseFragment;
import com.atguigu.prohibitpreloaddataviewpager.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/2/1.
 * QQ: 474297694
 * 功能: ViewPager与Fragment结合使用时 屏蔽Fragment的预加载并且只有第一次会请求网络
 * 附带下拉刷新效果
 * View层(视图层)
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    String [] datas =  {
            "http://www.atguigu.com/images/logo.gif",
            "http://www.atguigu.com/images/logo.gif",
            "http://www.atguigu.com/images/logo.gif",
            "http://www.atguigu.com/images/logo.gif",
            "http://www.atguigu.com/images/logo.gif"
    };
    private MyAdapter adapter;
    private List<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListener();

    }

    private void initData() {
        fragments = new ArrayList<>();
        for(int i = 0; i <datas.length ; i++) {
            fragments.add(new ImageFragment(datas[i],i));
        }
        adapter =new MyAdapter(getSupportFragmentManager());

        viewpager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initListener() {

    }
    class MyAdapter extends FragmentPagerAdapter{
        @Override
        public CharSequence getPageTitle(int position) {
            return position+1+"页";
        }

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
