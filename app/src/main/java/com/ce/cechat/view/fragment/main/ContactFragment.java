package com.ce.cechat.view.fragment.main;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ce.cechat.R;
import com.ce.cechat.model.event.ContactChangeEvent;
import com.ce.cechat.model.event.ContactEvent;
import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.event.GroupEvent;
import com.ce.cechat.presenter.ContactPresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.activity.ContactDetailActivity;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import androidx.navigation.Navigation;

/**
 * @author CE Chen
 *
 * 联系人
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends EaseContactListFragment
        implements IContactView,
        EaseContactListFragment.EaseContactListItemClickListener,
        EaseContactListFragment.EaseContactListItemLongClickListener, EaseContactListFragment.SwipeRefreshListener {

    private static final String TAG = "ContactFragment";
    private ImageView mInviteRedDot;

    private ContactPresenter mContactPresenter;

    public ContactFragment() {
        // Required empty public constructor

        mContactPresenter = new ContactPresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactFragment.
     */
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }


    @Subscribe
    public void onContactEvent(ContactEvent pContactEvent) {
        mContactPresenter.showInviteRedDot();
    }

    @Subscribe
    public void onGroupEvent(GroupEvent pGroupEvent) {
        mContactPresenter.showInviteRedDot();
    }

    @Subscribe
    public void onContactAddEvent(ContactChangeEvent pContactAddEvent) {
        mContactPresenter.getContactsMap(true);
    }

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.ic_add_white_24dp);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_contact_head, null, false);
        layContentContainer.addView(headView, 0);
        setContactListItemClickListener(this);
        setContactListItemLongClickListener(this);
        setSwipeRefreshListener(this);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_contact_to_nav_search);
            }
        });

        mInviteRedDot = layContentContainer.findViewById(R.id.iv_invite_red_dot);

        layContentContainer.findViewById(R.id.lay_add_new_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactPresenter.hideInviteRedDot();
                toInvitePage();
            }
        });

        layContentContainer.findViewById(R.id.lay_group_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGroupListPage();
            }
        });

        mContactPresenter.haveInviteRedDot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart isActive = " + isActive());
        mContactPresenter.getContactsMap(false);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop isActive = " + isActive());
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mContactPresenter.haveInviteRedDot();
        Log.v(TAG, "onResume isActive = " + isActive());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void toInvitePage() {
        Navigation.findNavController(layContentContainer).navigate(R.id.action_nav_contact_to_nav_invite);
    }

    private void toGroupListPage() {
        Navigation.findNavController(layContentContainer).navigate(R.id.action_nav_contact_to_nav_group_list);
    }

    @Override
    public void showInviteRedDot() {
        mInviteRedDot.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInviteRedDot() {
        mInviteRedDot.setVisibility(View.GONE);
    }

    @Override
    public void onGetAllContactSuccess(List<User> pAllContact) {
        mContactPresenter.list2UserMap(pAllContact);
        stopRefresh();
    }

    @Override
    public void onGetAllContactFailed(HyphenateException e) {
        stopRefresh();
        Log.v(TAG, "getErrorCode = " + e.getErrorCode() + ", getDescription = " + e.getDescription());
        if(getContext() != null) {
            Snackbar.make(layContentContainer,
                    ErrorCode.errorCodeToString(getContext(), e.getErrorCode()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateContactList(Map<String, EMGroup> pUserMap) {
        setContactsMap(pUserMap);
        refresh();
    }

    @Override
    public boolean onListItemLongClicked(View view, EMGroup user) {
        Log.v(TAG, "onListItemLongClicked " + user);
        return true;
    }

    @Override
    public void onListItemClicked(EMGroup user) {
        Log.v(TAG, "onListItemClicked " + user);
        toDetailPage(user);
    }

    private void toDetailPage(EMGroup user) {
        if (getActivity() != null) {
            Intent intent = ContactDetailActivity.getIntent(getActivity(), user);
            startActivity(intent);
        }
    }

    @Override
    public void onSwipeRefresh() {
        refreshData();
    }

    private void refreshData() {
        mContactPresenter.getContactsMap(true);
    }
}
