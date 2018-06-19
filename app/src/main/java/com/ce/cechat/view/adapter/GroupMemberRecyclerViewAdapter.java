package com.ce.cechat.view.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ce.cechat.R;
import com.ce.cechat.model.bean.GroupMember;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * {@link RecyclerView.Adapter} that can display a {@link GroupMember} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class GroupMemberRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private static final String TAG = "GroupMemberAdapter";
    private final List<GroupMember> mValues = new LinkedList<>();
    private final OnListFragmentInteractionListener mListener;

    public static final Uri ACTION_DELETE = Uri.parse("GroupMemberRecyclerViewAdapter_ACTION_DELETE");
    public static final Uri ACTION_SET_ADMIN = Uri.parse("GroupMemberRecyclerViewAdapter_ACTION_SET_ADMIN");
    public static final Uri ACTION_CANCEL_ADMIN = Uri.parse("GroupMemberRecyclerViewAdapter_ACTION_CANCEL_ADMIN");

    private boolean isOwner;
    private boolean isAdmin;

    public GroupMemberRecyclerViewAdapter( OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CommonViewHolder.newInstance(parent.getContext(), R.layout.fragment_edit_group, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommonViewHolder holder, final int position) {

        final GroupMember groupMember = mValues.get(position);

        if (groupMember.getIdentity() == GroupMember.OWNER) {
            //是群主
            holder.setImage(R.id.iv_delete, R.drawable.ic_delete_gray_24dp);
            holder.setImage(R.id.iv_head, R.drawable.ic_owner_account_circle_24dp);
        } else if (groupMember.getIdentity() == GroupMember.ADMIN) {
            //是管理员
            holder.setImage(R.id.iv_head, R.drawable.ic_admin_account_circle_24dp);
            if (isOwner) {
                //当前用户是群主可以删除管理员
                holder.setImage(R.id.iv_delete, R.drawable.ic_delete_round_red_24dp);
            } else {
                //管理员不能删除管理员
                holder.setImage(R.id.iv_delete, R.drawable.ic_delete_gray_24dp);
            }
        } else if (groupMember.getIdentity() == GroupMember.MEMBER) {
            //是群员
            holder.setImage(R.id.iv_head, R.drawable.ic_account_circle_48px);
            holder.setImage(R.id.iv_delete, R.drawable.ic_delete_round_red_24dp);
        }

        if (isOwner) {
            //当前用户是群主
            if (groupMember.getIdentity() == GroupMember.ADMIN) {
                //取消管理员
                holder.setText(R.id.tv_admin, R.string.cancel_admin);
                holder.setOnClick(R.id.tv_admin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onListFragmentInteraction(ACTION_CANCEL_ADMIN, groupMember);
                        }
                    }
                });
                holder.setOnClick(R.id.iv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onListFragmentInteraction(ACTION_DELETE, groupMember);
                        }
                    }
                });
            } else if (groupMember.getIdentity() == GroupMember.MEMBER) {
                //设置管理员
                holder.setText(R.id.tv_admin, R.string.set_admin);
                holder.setOnClick(R.id.tv_admin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onListFragmentInteraction(ACTION_SET_ADMIN, groupMember);
                        }
                    }
                });
            } else if (groupMember.getIdentity() == GroupMember.OWNER) {
                holder.hideView(R.id.tv_admin);
            }
        } else {
            holder.hideView(R.id.tv_admin);
        }

        if ((isOwner || isAdmin) && (groupMember.getIdentity() == GroupMember.MEMBER)) {
            holder.setOnClick(R.id.iv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onListFragmentInteraction(ACTION_DELETE, groupMember);
                    }
                }
            });
        }


        holder.setText(R.id.tv_name, groupMember.getName());

    }


    public void replaceAll(Collection<GroupMember> pGroupMembers) {
        mValues.clear();
        notifyDataSetChanged();
        mValues.addAll(pGroupMembers);
        notifyItemRangeInserted(0, mValues.size());
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {

        /**
         * @param uri
         * @param item
         */
        void onListFragmentInteraction(Uri uri, GroupMember item);
    }
}
