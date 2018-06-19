package com.ce.cechat.view.fragment.main;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.ce.cechat.R;
import com.ce.cechat.model.bean.Invitation;
import com.ce.cechat.model.event.ContactEvent;
import com.ce.cechat.model.event.GroupEvent;
import com.ce.cechat.presenter.InvitePresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.adapter.ContactMsgAdapter;
import com.ce.cechat.view.fragment.base.BaseFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

import static com.ce.cechat.model.bean.Invitation.InvitationStatus.NEW_GROUP_APPLICATION;
import static com.ce.cechat.model.bean.Invitation.InvitationStatus.NEW_GROUP_INVITE;
import static com.ce.cechat.model.bean.Invitation.InvitationStatus.NEW_INVITE;

/**
 * @author CE Chen
 * <p>
 * 邀请消息
 * <p>
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends BaseFragment implements ContactMsgAdapter.OnContactMsgListener, IInviteView{

    @BindView(R.id.lay_content)
    ConstraintLayout layContent;

    @BindView(R.id.rv_contact_msg)
    RecyclerView rvContactMsg;

    private ContactMsgAdapter mContactMsgAdapter;

    private InvitePresenter mInvitePresenter;


    public InviteFragment() {
        // Required empty public constructor
        mInvitePresenter = new InvitePresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InviteFragment.
     */
    public static InviteFragment newInstance() {
        InviteFragment fragment = new InviteFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mContactMsgAdapter = new ContactMsgAdapter(getContext());
        mContactMsgAdapter.setContactMsgListener(this);
        mInvitePresenter.getInvitations();
        rvContactMsg.setItemAnimator(new DefaultItemAnimator());
        rvContactMsg.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        rvContactMsg.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContactMsg.setAdapter(mContactMsgAdapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onContactEvent(ContactEvent pContactEvent) {
        mInvitePresenter.clearInviteRedDot();
        mInvitePresenter.getInvitations();
    }

    @Subscribe
    public void onGroupEvent(GroupEvent pGroupEvent) {
        mInvitePresenter.clearInviteRedDot();
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onAccept(Invitation pInvitation) {
        int status = pInvitation.getInvitationStatus();
        switch (status) {
            case NEW_INVITE:
                mInvitePresenter.acceptInviteContact(pInvitation);
                break;
            case NEW_GROUP_INVITE:
                mInvitePresenter.acceptInviteGroup(pInvitation);
                break;
            case NEW_GROUP_APPLICATION:
                mInvitePresenter.acceptApplicationGroup(pInvitation);
                break;
            default:
        }
    }

    @Override
    public void onRefuse(Invitation pInvitation) {
        int status = pInvitation.getInvitationStatus();
        switch (status) {
            case NEW_INVITE:
                mInvitePresenter.refuseInviteContact(pInvitation);
                break;
            case NEW_GROUP_INVITE:
            case NEW_GROUP_APPLICATION:
                refuseReason(pInvitation);
                break;
            default:
        }

    }

    private void refuseReason(final Invitation pInvitation) {
        if (getContext() != null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_refuse_reason, null, false);
            final EditText etReason = view.findViewById(R.id.et_reason);

            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.refuse_reason)
                    .setView(view)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String reason = etReason.getText().toString();
                            pInvitation.setReason(reason);
                            int status = pInvitation.getInvitationStatus();
                            switch (status) {
                                case NEW_GROUP_INVITE:
                                    mInvitePresenter.refuseInviteGroup(pInvitation);
                                    break;
                                case NEW_GROUP_APPLICATION:
                                    mInvitePresenter.refuseApplicationGroup(pInvitation);
                                    break;
                                default:
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    @Override
    public void setInvitations(List<Invitation> pInvitations) {
        mContactMsgAdapter.replaceInvitations(pInvitations);
    }

    @Override
    public void onAcceptInviteContactSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onAcceptInviteContactFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAcceptInviteGroupSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onAcceptInviteGroupFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAcceptApplicationGroupSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onAcceptApplicationGroupFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefuseInviteContactSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onRefuseInviteContactFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefuseInviteGroupSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onRefuseInviteGroupFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefuseApplicationGroupSuccess() {
        mInvitePresenter.getInvitations();
    }

    @Override
    public void onRefuseApplicationGroupFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
