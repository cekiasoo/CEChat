package com.ce.cechat.ui.newmsg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ce.cechat.R;
import com.ce.cechat.app.CommonViewHolder;
import com.ce.cechat.bean.Group;
import com.ce.cechat.bean.Invitation;
import com.ce.cechat.bean.User;

import java.util.LinkedList;
import java.util.List;

import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_ACCEPT_INVITE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_REFUSE_APPLICATION;
import static com.ce.cechat.bean.Invitation.InvitationStatus.GROUP_REFUSE_INVITE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_ACCEPT;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_ACCEPT_BY_PEER;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_REFUSE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.INVITE_REFUSE_BY_PEER;
import static com.ce.cechat.bean.Invitation.InvitationStatus.NEW_GROUP_APPLICATION;
import static com.ce.cechat.bean.Invitation.InvitationStatus.NEW_GROUP_INVITE;
import static com.ce.cechat.bean.Invitation.InvitationStatus.NEW_INVITE;

/**
 * @author CE Chen
 *
 */
public class ContactMsgAdapter extends RecyclerView.Adapter<CommonViewHolder> {


    private static final String TAG = "ContactMsgAdapter";

    private List<Invitation> mInvitations = new LinkedList<>();

    private Context mContext;

    private OnContactMsgListener mContactMsgListener;

    public ContactMsgAdapter(Context pContext) {
        this.mContext = pContext;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CommonViewHolder.newInstance(mContext, R.layout.view_contact_msg, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        holder.hideView(R.id.tv_accept);
        holder.hideView(R.id.tv_refuse);

        final Invitation invitation = mInvitations.get(position);

        User user = invitation.getUser();

        Log.v(TAG, "getInvitationStatus = " + invitation.getInvitationStatus());

        if (user != null) {
            //联系人

            holder.setImage(R.id.iv_head, R.drawable.ic_account_circle_48px);

            holder.setText(R.id.tv_name, user.getHyphenateId());

            int status = invitation.getInvitationStatus();
            switch (status) {
                case NEW_INVITE:
                    //新邀请

                    holder.showView(R.id.tv_accept);
                    holder.showView(R.id.tv_refuse);

                    if (TextUtils.isEmpty(invitation.getReason())) {
                        holder.setText(R.id.tv_reason, R.string.request_you_make_friend);
                    } else {
                        holder.setText(R.id.tv_reason, invitation.getReason());
                    }
                    break;
                case INVITE_ACCEPT:
                    //接受邀请

                    if (TextUtils.isEmpty(invitation.getReason())) {
                        holder.setText(R.id.tv_reason, R.string.accept_request);
                    } else {
                        holder.setText(R.id.tv_reason, invitation.getReason());
                    }
                    break;
                case INVITE_ACCEPT_BY_PEER:
                    //邀请被接受了

                    if (TextUtils.isEmpty(invitation.getReason())) {
                        holder.setText(R.id.tv_reason, R.string.accept_request_by_peer);
                    } else {
                        holder.setText(R.id.tv_reason, invitation.getReason());
                    }
                    break;
                case INVITE_REFUSE:
                    holder.setText(R.id.tv_reason, invitation.getReason());
                    break;
                case INVITE_REFUSE_BY_PEER:
                    holder.setText(R.id.tv_reason, invitation.getReason());
                    break;
                default:
                    break;
            }

        } else {
            //群组
            Group group = invitation.getGroup();
            holder.setImage(R.id.iv_head, R.drawable.ic_group);
            holder.setText(R.id.tv_name, group.getGroupName());
            holder.setText(R.id.tv_reason, invitation.getReason());

            int status = invitation.getInvitationStatus();
            switch (status) {
                case GROUP_ACCEPT_INVITE:
                    holder.setText(R.id.tv_reason,
                            mContext.getString(R.string.accept_invite_in_group, group.getInviteUser(), group.getGroupName()));
                    break;
                case GROUP_APPLICATION_ACCEPTED:
                    holder.setText(R.id.tv_reason,
                            mContext.getString(R.string.accept_application_in_group, group.getInviteUser(), group.getGroupName()));
                    break;
                case GROUP_REFUSE_INVITE:
                    holder.setText(R.id.tv_reason,
                            mContext.getString(R.string.refuse_invite_in_group, group.getInviteUser(), group.getGroupName()));
                    break;
                case GROUP_REFUSE_APPLICATION:
                    holder.setText(R.id.tv_reason,
                            mContext.getString(R.string.refuse_application_in_group, group.getInviteUser(), group.getGroupName()));
                    break;
                case NEW_GROUP_APPLICATION:
                    holder.showView(R.id.tv_accept);
                    holder.showView(R.id.tv_refuse);
                    break;
                case NEW_GROUP_INVITE:
                    holder.showView(R.id.tv_accept);
                    holder.showView(R.id.tv_refuse);
                default:
            }

        }

        //添加接受按钮事件
        holder.setOnClick(R.id.tv_accept, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactMsgListener != null) {
                    mContactMsgListener.onAccept(invitation);
                }
            }
        });

        //添加拒绝按钮事件
        holder.setOnClick(R.id.tv_refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactMsgListener != null) {
                    mContactMsgListener.onRefuse(invitation);
                }
            }
        });

    }

    public void addInvitations(List<Invitation> pInvitations) {

        if (pInvitations == null || pInvitations.isEmpty()) {
            return;
        }

        this.mInvitations.addAll(0, pInvitations);
        notifyItemRangeChanged(0, mInvitations.size());
        notifyItemRangeInserted(0, mInvitations.size());
    }

    public void addInvitation(Invitation pInvitation) {

        if (pInvitation == null) {
            return;
        }

        this.mInvitations.add(0, pInvitation);
        notifyItemChanged(0);
        notifyItemInserted(0);
    }

    public void replaceInvitations(List<Invitation> pInvitations) {
        if (pInvitations == null) {
            return;
        }
        this.mInvitations.clear();
        notifyDataSetChanged();
        this.addInvitations(pInvitations);
    }

    @Override
    public int getItemCount() {
        return mInvitations.size();
    }

    public void setContactMsgListener(OnContactMsgListener pContactMsgListener) {
        this.mContactMsgListener = pContactMsgListener;
    }

    public interface OnContactMsgListener{

        /**
         * 接受按钮被点击
         *
         * @param pInvitation invitation
         */
        void onAccept(Invitation pInvitation);

        /**
         * 拒绝按钮被点击
         *
         * @param pInvitation invitation
         */
        void onRefuse(Invitation pInvitation);
    }
}
