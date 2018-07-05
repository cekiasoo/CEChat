package com.ce.cechat.ui.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.event.GroupEvent;
import com.ce.cechat.ui.chat.ChatActivity;
import com.ce.cechat.utils.ErrorCode;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseGroupListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author CE Chen
 *
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class GroupListFragment extends EaseGroupListFragment
        implements IGroupListContract.IGroupListView,
        EaseGroupListFragment.EaseGroupListItemClickListener,
        HasSupportFragmentInjector {

    private static final String TAG = "GroupListFragment";

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;


    @Inject
    protected GroupListPresenter mGroupListPresenter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupListFragment() {
    }

    @SuppressWarnings("unused")
    public static GroupListFragment newInstance(int columnCount) {
        GroupListFragment fragment = new GroupListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takeView();
    }

    @Override
    public void onDestroy() {
        dropView();
        super.onDestroy();
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
    public void onGroupEvent(GroupEvent pGroupEvent) {
        mGroupListPresenter.getGroupList();
    }

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.ic_add_white_24dp);
        setGroupListListItemClickListener(this);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_group_list_to_nav_create_group);
            }
        });
        mGroupListPresenter.getGroupList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGroupListPresenter.getGroupList();
    }

    @Override
    public void onGetGroupListSuccess(List<EMGroup> value) {
        mGroupListPresenter.list2GroupMap(value);
    }

    @Override
    public void onGetGroupListFailed(int error, String errorMsg) {
        if (getContext() != null) {
            Log.v(TAG, "error = " + error + ", errorMsg = " + errorMsg);
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateGroupList(Map<String, EMGroup> pGroupMap) {
        setGroupsMap(pGroupMap);
        refresh();
    }

    @Override
    public void onListItemClicked(EMGroup group) {
        Intent intent = ChatActivity.getIntent(getContext(), EaseConstant.CHATTYPE_GROUP, group.getGroupId());
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void takeView() {
        mGroupListPresenter.takeView(this);
    }

    @Override
    public void dropView() {
        mGroupListPresenter.dropView();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }
}
