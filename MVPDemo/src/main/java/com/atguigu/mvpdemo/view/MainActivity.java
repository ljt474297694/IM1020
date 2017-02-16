package com.atguigu.mvpdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mvpdemo.R;
import com.atguigu.mvpdemo.bean.Bean;
import com.atguigu.mvpdemo.presenter.Presenter;
import com.atguigu.mvpdemo.presenter.PresenterInterface;
import com.atguigu.mvpdemo.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: MVP设计模式
 * View层(视图层)
 */
public class MainActivity extends AppCompatActivity implements ViewInterface {

    @Bind(R.id.iv_main)
    ImageView ivMain;
    @Bind(R.id.pb_main)
    ProgressBar pbMain;
    @Bind(R.id.tv_main)
    TextView tvMain;
    /**
     * 操作层实例 用于调用其联网方法
     */
    private PresenterInterface mPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 初始化视图 加载数据
         */
        initView();
        initData();
    }
    //初始化视图
    private void initView() {
        ButterKnife.bind(this);
    }
    //准备工作完成 得到操作层实例 调用联网请求
    private void initData() {
        /**
         * 得到操作层的引用 传入View层接口实例
         */
        mPresenterInterface = new Presenter(this);

        mPresenterInterface.getDataFromNet();

    }

    /**
     * @param json 请求成功 绑定数据
     */
    @Override
    public void onSuccess(String json) {

        Bean bean = new Gson().fromJson(json, Bean.class);

        Glide.with(this).load(Constant.BASE_URL + bean.getData().getTopnews().get(0).getTopimage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivMain);
    }

    // 请求失败
    @Override
    public void onError(String error) {
        hideLoading();

        tvMain.setVisibility(View.VISIBLE);
        tvMain.setText(error);

        Toast.makeText(MainActivity.this, "请求失败" + error, Toast.LENGTH_SHORT).show();
    }

    //显示加载页面
    @Override
    public void showLoading() {
        pbMain.setVisibility(View.VISIBLE);
    }

    //隐藏加载页面
    @Override
    public void hideLoading() {
        pbMain.setVisibility(View.GONE);
    }
}
