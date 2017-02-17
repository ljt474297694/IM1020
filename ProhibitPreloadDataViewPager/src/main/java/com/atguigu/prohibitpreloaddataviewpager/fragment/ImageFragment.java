package com.atguigu.prohibitpreloaddataviewpager.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.atguigu.prohibitpreloaddataviewpager.R;
import com.atguigu.prohibitpreloaddataviewpager.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by 李金桐 on 2017/2/17.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ImageFragment extends BaseFragment{
    private String key = "IMAGE";
    private  int position;
    private  String url;
    private ImageView imageview;
    private SwipeRefreshLayout swiperefreshlayout;
    private Bitmap imageBitmap;

    public ImageFragment(String data, int position) {
        super();
        this.position = position+1;
        this.url= data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            imageBitmap = savedInstanceState.getParcelable(key);
        }
    }

    @Override
    protected View initView() {
        Log.e("TAG", "ImageFragment initView()第"+position+"页");
        View view = View.inflate(mContext, R.layout.fragment_image,null);
        imageview = (ImageView) view.findViewById(R.id.imageview);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        return view;
    }

    @Override
    protected void initData() {
        if(imageBitmap!=null) {
            imageview.setImageBitmap(imageBitmap);
        }else{
            loadImageView();
        }
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImageView();
            }
        });
    }

    private void loadImageView() {
        Log.e("TAG", "ImageFragment initData()第"+position+"页");
        Glide.with(this).load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageview.setImageBitmap(resource);
                        imageBitmap=resource;
                        swiperefreshlayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(imageBitmap!=null) {
            outState.putParcelable(key,imageBitmap);
        }
    }
}
