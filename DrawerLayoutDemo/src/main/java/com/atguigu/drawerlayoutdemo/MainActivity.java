package com.atguigu.drawerlayoutdemo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.content_frame)
    FrameLayout contentFrame;
    @Bind(R.id.left_drawer)
    ListView leftDrawer;
    @Bind(R.id.right_drawer)
    RelativeLayout rightDrawer;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    String[] datas = {"菜单1","菜单2","菜单3","菜单4","菜单5"};
    private int position;
    private MyAdapter adapter;
    private ArrayList<TextFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position = position;
                adapter.notifyDataSetChanged();
                switchFragment(position);
                drawerLayout.closeDrawers();
            }
        });
    }

    private void switchFragment(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragments.get(position)).commit();
    }

    private void initData() {
        adapter = new MyAdapter();
        leftDrawer.setAdapter(adapter);
        fragments = new ArrayList<>();
        for(int i = 0; i <5 ; i++) {
            TextFragment textFragment = new TextFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text","页面" + (i+1));
            textFragment.setArguments(bundle);
          fragments.add(textFragment);
        }
        switchFragment(0);
    }

    class  MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                convertView = View.inflate(MainActivity.this,R.layout.list_item,null);

            TextView textView = (TextView) convertView.findViewById(R.id.tv_item);

            if(position==MainActivity.this.position) {
                textView.setBackgroundResource(android.R.color.holo_blue_light);
            }
            textView.setText(datas[position]);
            return convertView;
        }
    }
}
