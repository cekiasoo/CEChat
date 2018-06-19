package com.ce.cechat.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ce.cechat.R;
import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.bean.User;
import com.hyphenate.chat.EMClient;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 : 群成员 Adapter
 */
public class GroupMemberAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private static final String TAG = "GroupMemberAdapter";
    private Context mContext;

    private GroupMember mOwner;

    private List<GroupMember> mAdmin = new LinkedList<>();

    private List<GroupMember> mUsers = new LinkedList<>();

    private boolean isMemberAllowToInvite;

    private OnAddClickListener mOnAddClickListener;

    private OnMemberClickListener mOnMemberClickListener;

    public GroupMemberAdapter(Context pContext) {
        this.mContext = pContext;
    }

    private void addAdd() {
        mUsers.add(0, new GroupMember("ADD"));
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CommonViewHolder.newInstance(mContext, R.layout.view_group_member_item, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        if (position == 0) {
            if (isAddAdd()) {
                holder.setText(R.id.tv_name, mContext.getString(R.string.add));
                holder.setImage(R.id.iv_head, R.drawable.ic_add_black_24dp);
                if (mOnAddClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnAddClickListener.onAddClick();
                        }
                    });
                }
            } else {
                setView(holder, position);
            }
        } else {
            setView(holder, position);
        }
    }

    private void setView(@NonNull CommonViewHolder holder, int position) {
        final GroupMember user = mUsers.get(position);
        holder.setText(R.id.tv_name, user.getName());
        if (user.getIdentity() == GroupMember.OWNER) {
            holder.setImage(R.id.iv_head, R.drawable.ic_owner_account_circle_24dp);
        } else if (user.getIdentity() == GroupMember.ADMIN) {
            holder.setImage(R.id.iv_head, R.drawable.ic_admin_account_circle_24dp);
        } else {
            Log.v(TAG, "user = " + user);
            holder.setImage(R.id.iv_head, R.drawable.ic_account_circle_48px);
        }

        if (mOnMemberClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMemberClickListener.onMemberClick(user);
                }
            });
        }
    }

    public void addAll(Collection<GroupMember> pUsers) {
        int positionStart = mUsers.size();
        mUsers.addAll(pUsers);
        notifyItemRangeInserted(positionStart, pUsers.size());
    }

    public void replace(Collection<GroupMember> pUsers) {
        mUsers.clear();
        notifyDataSetChanged();
        mUsers.addAll(pUsers);
        if (isAddAdd()) {
            addAdd();
        }
        notifyItemRangeInserted(0, mUsers.size());
    }

    /**
     * 是否可以邀请成员
     * @return 是返回true 否返回false
     */
    private boolean isAddAdd() {
        if (isMemberAllowToInvite
                || mOwner.getHyphenateId().equals(EMClient.getInstance().getCurrentUser())
                || isAdmin(EMClient.getInstance().getCurrentUser())) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(String pHyphenateId) {
        for (User user : mAdmin) {
            if (user.getHyphenateId().equals(pHyphenateId)) {
                return true;
            }
        }
        return false;
    }

    public void setOwner(GroupMember pOwner) {
        this.mOwner = pOwner;
    }

    public void setAdmin(List<GroupMember> pAdmin) {
        this.mAdmin = pAdmin;
    }

    public void setMemberAllowToInvite(boolean memberAllowToInvite) {
        isMemberAllowToInvite = memberAllowToInvite;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setOnAddClickListener(OnAddClickListener pOnAddClickListener) {
        this.mOnAddClickListener = pOnAddClickListener;
    }

    public void setOnMemberClickListener(OnMemberClickListener pOnMemberClickListener) {
        this.mOnMemberClickListener = pOnMemberClickListener;
    }

    /**
     * 点击添加的事件监听器
     */
    public interface OnAddClickListener {
        /**
         * 点击添加
         */
        void onAddClick();
    }

    /**
     * 点击成员的事件监听器
     */
    public interface OnMemberClickListener {
        /**
         * 点击成员
         * @param pGroupMember 点击的成员
         */
        void onMemberClick(GroupMember pGroupMember);
    }

}
