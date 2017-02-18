package com.atguigu.im1020.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/2/18.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GroupListAdapter extends BaseAdapter {


    private List<EMGroup> groups;
    private final Context mContext;


    public GroupListAdapter(Context context) {
        this.mContext = context;
        groups = new ArrayList<>();
    }


    public void refresh() {

        List<EMGroup> allGroups = EMClient.getInstance().groupManager().getAllGroups();

        if (allGroups != null) {
            groups = allGroups;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return groups == null ? 0 : groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_group_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.groupTvGroupname.setText(groups.get(position).getGroupName());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.group_tv_groupname)
        TextView groupTvGroupname;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
