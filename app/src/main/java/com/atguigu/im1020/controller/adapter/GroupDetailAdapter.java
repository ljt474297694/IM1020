package com.atguigu.im1020.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/2/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GroupDetailAdapter extends BaseAdapter {

    private final Context mContext;
    private final boolean isModify;
    private ArrayList<UserInfo> userInfos;
    private boolean isDeleteModle;


    public boolean isDeleteModle() {
        return isDeleteModle;
    }

    public void setDeleteModle(boolean deleteModle) {
        isDeleteModle = deleteModle;
        notifyDataSetChanged();
    }

    public GroupDetailAdapter(Context context, boolean isModify) {
        this.isModify = isModify;
        this.mContext = context;
        userInfos = new ArrayList<>();
    }

    public void refresh(List<UserInfo> userinfos) {
        if (userinfos == null || userinfos.size() == 0) {
            return;
        }
        userInfos.clear();
        userInfos.add(0, new UserInfo("remove"));
        userInfos.add(0, new UserInfo("add"));
        userInfos.addAll(0, userinfos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userInfos == null ? 0 : userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_group_members, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (isModify) {

            if (position == userInfos.size() - 1 || position == userInfos.size() - 2) {
                if(isDeleteModle) {
                    convertView.setVisibility(View.GONE);
                }else{
                    convertView.setVisibility(View.VISIBLE);
                    viewHolder.ivMemberDelete.setVisibility(View.GONE);
                    viewHolder.tvMemberName.setVisibility(View.INVISIBLE);
                    if(position == userInfos.size() - 1) {
                        viewHolder.ivMemberPhoto.setImageResource(R.drawable.em_smiley_minus_btn_pressed);
                    }else{
                        viewHolder.ivMemberPhoto.setImageResource(R.drawable.em_smiley_add_btn_pressed);

                    }
                }
            } else {
                viewHolder.tvMemberName.setText(userInfos.get(position).getUsername());
                convertView.setVisibility(View.VISIBLE);
                viewHolder.tvMemberName.setVisibility(View.VISIBLE);
                viewHolder.ivMemberPhoto.setImageResource(R.drawable.em_default_avatar);
                if(isDeleteModle) {
                    viewHolder.ivMemberDelete.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.ivMemberDelete.setVisibility(View.GONE);
                }

            }

            if (position == userInfos.size() - 1) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isDeleteModle) {
                            isDeleteModle = true;
                            notifyDataSetChanged();
                        }
                    }
                });

            } else if (position == userInfos.size() - 2) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnMembersChangeListener!=null) {
                            mOnMembersChangeListener.onAddGroupMember(userInfos.get(position));
                        }
                    }
                });
            } else {
                if(isDeleteModle) {
                    viewHolder.ivMemberDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mOnMembersChangeListener!=null) {
                                mOnMembersChangeListener.onRemoveGroupMember(userInfos.get(position));
                            }
                        }
                    });
                }

            }
        } else {

            if (position == userInfos.size() - 1 || position == userInfos.size() - 2) {
                convertView.setVisibility(View.GONE);
            } else {
                viewHolder.ivMemberDelete.setVisibility(View.GONE);
                viewHolder.tvMemberName.setText(userInfos.get(position).getUsername());
            }
        }


        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_member_photo)
        ImageView ivMemberPhoto;
        @Bind(R.id.tv_member_name)
        TextView tvMemberName;
        @Bind(R.id.iv_member_delete)
        ImageView ivMemberDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnMembersChangeListener {

        void onRemoveGroupMember(UserInfo userInfo);

        void onAddGroupMember(UserInfo userInfo);
    }

    private OnMembersChangeListener mOnMembersChangeListener;

    public void setOnMembersChangeListener(OnMembersChangeListener l) {
        this.mOnMembersChangeListener = l;
    }
}
