package com.ce.cechat.ui.editgroup;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.app.InjectFragment;
import com.ce.cechat.bean.GroupMember;
import com.ce.cechat.event.GroupEvent;
import com.ce.cechat.utils.ErrorCode;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.widget.EaseTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * @author CE Chen
 * <p>
 * 编辑群成员
 * A fragment representing a list of Items.
 */
public class EditGroupFragment extends InjectFragment<EditGroupMemberPresenter>
        implements GroupMemberRecyclerViewAdapter.OnListFragmentInteractionListener,
        IEditGroupContract.IEditGroupMemberView {

    private static final String ARG_GROUP_ID = "ARG_GROUP_ID";
    private static final String TAG = "EditGroupFragment";
    @BindView(R.id.title_bar)
    EaseTitleBar titleBar;
    @BindView(R.id.rv_group_member_list)
    RecyclerView rvGroupMemberList;
    private String mGroupId;

    private GroupMemberRecyclerViewAdapter mGroupMemberAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EditGroupFragment() {
    }

    @SuppressWarnings("unused")
    public static EditGroupFragment newInstance(String pGroupId) {
        EditGroupFragment fragment = new EditGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, pGroupId);
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle getFragArguments(String pGroupId) {
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, pGroupId);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mGroupId = getArguments().getString(ARG_GROUP_ID);
        }
        Log.v(TAG, "mGroupId = " + mGroupId);
    }


    @Override
    protected void initView(View view) {
        rvGroupMemberList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGroupMemberList.setItemAnimator(new DefaultItemAnimator());
        rvGroupMemberList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mGroupMemberAdapter = new GroupMemberRecyclerViewAdapter(this);
        rvGroupMemberList.setAdapter(mGroupMemberAdapter);
        mPresenter.getGroupDetail(mGroupId);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_item_list;
    }


    @Override
    public void onListFragmentInteraction(Uri uri, GroupMember item) {

        if (uri == GroupMemberRecyclerViewAdapter.ACTION_DELETE) {
            deleteMemberDialog(item);
        } else if (uri == GroupMemberRecyclerViewAdapter.ACTION_CANCEL_ADMIN) {
            cancelAdminDialog(item);
        } else if (uri == GroupMemberRecyclerViewAdapter.ACTION_SET_ADMIN) {
            setAdminDialog(item);
        }
        Log.v(TAG, item.getHyphenateId() + " " + uri);
    }

    private void deleteMemberDialog(final GroupMember item) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.delete_group_member)
                    .setMessage(getString(R.string.confirm_delete_member, item.getName()))
                    .setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.deleteGroupMembers(mGroupId, item.getHyphenateId());
                        }
                    })
                    .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    private void cancelAdminDialog(final GroupMember item) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.cancel_admin)
                    .setMessage(getString(R.string.confirm_cancel_admin, item.getName()))
                    .setPositiveButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.cancelGroupAdmin(mGroupId, item.getHyphenateId());
                        }
                    })
                    .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    private void setAdminDialog(final GroupMember item) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.set_admin)
                    .setMessage(getString(R.string.confirm_set_admin, item.getName()))
                    .setPositiveButton(R.string.confirm_set, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.setGroupAdmin(mGroupId, item.getHyphenateId());
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
    public void onDeleteGroupMemberSuccess() {
        Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
        mPresenter.getGroupDetail(mGroupId);
    }

    @Override
    public void onDeleteGroupMemberFailed(int code, String error) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), code), Toast.LENGTH_SHORT).show();
        }
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
        mPresenter.getGroupDetail(mGroupId);
    }

    @Override
    public void onGetGroupDetailSuccess(EMGroup pGroup) {
        Log.v(TAG, "onGetGroupDetailSuccess");
        updateAdapter(pGroup);
    }

    private void updateAdapter(EMGroup pGroup) {
        List<GroupMember> allMembers = mPresenter.getGroupAllMembers(pGroup);
        boolean owner = mPresenter.isGroupOwner(pGroup);
        boolean admin = mPresenter.isGroupAdmin(pGroup);
        mGroupMemberAdapter.setOwner(owner);
        mGroupMemberAdapter.setAdmin(admin);
        mGroupMemberAdapter.replaceAll(allMembers);
    }

    @Override
    public void onGetGroupDetailFailed(int error, String errorMsg) {
        Log.v(TAG, "onGetGroupDetailFailed");
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelGroupAdminSuccess(EMGroup value) {
        Log.v(TAG, "onCancelGroupAdminSuccess");
        updateAdapter(value);
    }

    @Override
    public void onCancelGroupAdminFailed(int error, String errorMsg) {
        Log.v(TAG, "onCancelGroupAdminFailed");
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetGroupAdminSuccess(EMGroup value) {
        Log.v(TAG, "onSetGroupAdminSuccess");
        updateAdapter(value);
    }

    @Override
    public void onSetGroupAdminFailed(int error, String errorMsg) {
        Log.v(TAG, "onSetGroupAdminFailed");
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
