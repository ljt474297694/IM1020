package com.atguigu.im1020.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.model.Model;
import com.atguigu.im1020.model.bean.InvitationInfo;
import com.atguigu.im1020.model.bean.UserInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 李金桐 on 2017/2/16.
 * QQ: 474297694
 * 功能: InviteAdapter
 */
public class InviteAdapter extends BaseAdapter {
    private final Context context;
    private List<InvitationInfo> myInvitations;
    private OnInviteListener onInviteListener;


    public InviteAdapter(Context context, OnInviteListener onInviteListener) {
        this.context = context;
        this.onInviteListener = onInviteListener;
        myInvitations = Model.getInstance().getDbManager().getInvitationDAO().getInvitations();
    }

    public void refresh() {
        List<InvitationInfo> invitations = Model.getInstance().getDbManager().getInvitationDAO().getInvitations();
        if (invitations != null) {
            myInvitations = invitations;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return myInvitations == null ? 0 : myInvitations.size();
    }

    @Override
    public InvitationInfo getItem(int position) {
        return myInvitations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_invite_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final InvitationInfo invitationInfo = myInvitations.get(position);

        UserInfo user = invitationInfo.getUserInfo();

        if (user != null) {
            //联系人邀请
            UserInfo userInfo = invitationInfo.getUserInfo();
            viewHolder.tvInviteName.setText(userInfo.getUsername());
            //隐藏button
            viewHolder.btInviteAccept.setVisibility(View.GONE);
            viewHolder.btInviteReject.setVisibility(View.GONE);


            InvitationInfo.InvitationStatus status = invitationInfo.getStatus();


            if (status == InvitationInfo.InvitationStatus.NEW_INVITE) {

                viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                viewHolder.btInviteReject.setVisibility(View.VISIBLE);

                if (invitationInfo.getReason() == null) {
                    viewHolder.tvInviteReason.setText("邀请好友");
                } else {
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }

            } else if (status == InvitationInfo.InvitationStatus.INVITE_ACCEPT) {

                if (invitationInfo.getReason() == null) {
                    viewHolder.tvInviteReason.setText("接受邀请");
                } else {
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }

            } else if (status == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {

                if (invitationInfo.getReason() == null) {
                    viewHolder.tvInviteReason.setText("邀请被接受");
                } else {
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }
            }

            viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onInviteListener != null)
                        onInviteListener.onAccept(invitationInfo);
                }
            });

            viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onInviteListener != null)
                        onInviteListener.onReject(invitationInfo);
                }
            });

        } else {
            //群组邀请

        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_invite_name)
        TextView tvInviteName;
        @Bind(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @Bind(R.id.bt_invite_accept)
        Button btInviteAccept;
        @Bind(R.id.bt_invite_reject)
        Button btInviteReject;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // 回调监听接口
    public interface OnInviteListener {
        // 接受的点击事件处理
        void onAccept(InvitationInfo invitationInfo);

        // 拒绝的点击事件处理
        void onReject(InvitationInfo invitationInfo);
    }

    public void setOnInviteListener(OnInviteListener onInviteListener) {
        this.onInviteListener = onInviteListener;
    }
}

