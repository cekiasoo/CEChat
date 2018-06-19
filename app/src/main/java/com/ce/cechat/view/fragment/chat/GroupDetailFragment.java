package com.ce.cechat.view.fragment.chat;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.event.GroupEvent;
import com.ce.cechat.presenter.GroupDetailPresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.adapter.GroupDetailMenuAdapter;
import com.ce.cechat.view.adapter.GroupMemberAdapter;
import com.ce.cechat.view.fragment.base.BaseFragment;
import com.ce.cechat.view.fragment.main.SelectContactFragment;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * 群资料
 * Use the {@link GroupDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupDetailFragment extends BaseFragment implements IGroupDetailView, AdapterView.OnItemClickListener, GroupMemberAdapter.OnAddClickListener, GroupMemberAdapter.OnMemberClickListener {

    private static final String TAG = "GroupDetailFragment";

    public static final Uri ACTION_FINISH = Uri.parse("GroupDetailFragment_ACTION_FINISH");

    @BindView(R.id.lay_swipe_refresh)
    SwipeRefreshLayout laySwipeRefresh;
    @BindView(R.id.title_bar)
    EaseTitleBar titleBar;
    @BindView(R.id.tv_group_id)
    TextView tvGroupId;
    @BindView(R.id.tv_group_name)
    TextView tvGroupName;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    public boolean isLoading;

    private EMGroup mGroupDetail;

    /**
     * 是否是群主
     */
    private boolean isOwner;

    /**
     * 群 Id
     */
    private String mGroupId;

    private GroupDetailPresenter mGroupDetailPresenter;
    private PopupWindow mWindow;

    private GroupMemberAdapter mGroupMemberAdapter;

    public GroupDetailFragment() {
        // Required empty public constructor
        mGroupDetailPresenter = new GroupDetailPresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pGroupId Group Id.
     * @return A new instance of fragment GroupDetailFragment.
     */
    public static GroupDetailFragment newInstance(String pGroupId) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putString(EaseConstant.EXTRA_USER_ID, pGroupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGroupId = arguments.getString(EaseConstant.EXTRA_USER_ID);
            Log.v(TAG, "mGroupId = " + mGroupId);
        }
        isLoading = true;
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

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Subscribe
    public void onGroupEvent(GroupEvent pGroupEvent) {
        mGroupDetailPresenter.getGroupDetail(mGroupId);
        showProgress();
        isLoading = true;
    }

    @Override
    protected void initView(View view) {
        tvGroupId.setText(mGroupId);
        mGroupDetailPresenter.getGroupName(mGroupId);
        mGroupMemberAdapter = new GroupMemberAdapter(getContext());
        mGroupMemberAdapter.setOnAddClickListener(this);
        mGroupMemberAdapter.setOnMemberClickListener(this);
        rvMember.setItemAnimator(new DefaultItemAnimator());
        rvMember.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvMember.setAdapter(mGroupMemberAdapter);
        mGroupDetailPresenter.getGroupDetail(mGroupId);
        showProgress();
        isLoading = true;
    }

    @Override
    protected void setListener() {
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        laySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupDetailPresenter.getGroupDetail(mGroupId);
                isLoading = true;
            }
        });
    }

    /**
     * 显示菜单列表
     */
    private void showPopupMenu() {
        if (isLoading) {
            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_popup_menu, null, false);
        mWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = view.findViewById(R.id.lv_menu);
        listView.setAdapter(new GroupDetailMenuAdapter(getContext(), isOwner));

        listView.setOnItemClickListener(this);
        mWindow.setOutsideTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackground(1);
            }
        });
        mWindow.showAtLocation(titleBar.getRightLayout(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,
                0,
                0);
        setBackground(0.5f);
    }

    private void setBackground(float alpha) {
        if (getActivity() != null) {
            WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
            attributes.alpha = alpha;
            getActivity().getWindow().setAttributes(attributes);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_detail;
    }

    @OnClick(R.id.tv_edit)
    public void onViewClicked() {
        Bundle arguments = EditGroupFragment.getFragArguments(mGroupId);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_group_detail_to_nav_edit_group_member, arguments);
    }

    @Override
    public void setGroupName(String pGroupName) {
        tvGroupName.setText(pGroupName);
    }

    @Override
    public void destroyGroupSuccess() {
        onButtonPressed(ACTION_FINISH);
    }

    @Override
    public void destroyGroupFailed(HyphenateException e) {
        Log.v(TAG, "destroyGroupFailed getDescription = " + e.getDescription() + ", getErrorCode = " + e.getErrorCode());
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    ErrorCode.errorCodeToString(getContext(), e.getErrorCode()),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void leaveGroupSuccess() {
        onButtonPressed(ACTION_FINISH);
    }

    @Override
    public void leaveGroupFailed(HyphenateException e) {
        Log.v(TAG, "leaveGroupFailed getDescription = " + e.getDescription() + ", getErrorCode = " + e.getErrorCode());
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    ErrorCode.errorCodeToString(getContext(), e.getErrorCode()),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getGroupDetailSuccess(EMGroup pGroup) {
        mGroupDetail = pGroup;
        List<GroupMember> allMember = mGroupDetailPresenter.getGroupAllMember(pGroup);
        List<GroupMember> allAdmin = mGroupDetailPresenter.getGroupAllAdmin(pGroup);
        GroupMember owner = mGroupDetailPresenter.getOwner(pGroup);
        setGroupName(pGroup.getGroupName());
        mGroupDetailPresenter.isOwnerOrAdmin(pGroup);
        mGroupMemberAdapter.setOwner(owner);
        mGroupMemberAdapter.setAdmin(allAdmin);
        mGroupMemberAdapter.setMemberAllowToInvite(pGroup.isMemberAllowToInvite());
        mGroupMemberAdapter.replace(allMember);
        hideProgress();
        isOwner = mGroupDetailPresenter.isGroupOwner(owner.getHyphenateId());
        isLoading = false;
        stopRefreshing();
    }

    @Override
    public void getGroupDetailFailed(int error, String errorMsg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
        hideProgress();
        stopRefreshing();
    }

    @Override
    public void showEdit() {
        tvEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEdit() {
        tvEdit.setVisibility(View.GONE);
    }

    private void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    private void stopRefreshing() {
        if (laySwipeRefresh.isRefreshing()) {
            laySwipeRefresh.setRefreshing(false);
        }
    }

    private void dismissMenu() {
        if (mWindow != null) {
            mWindow.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v(TAG, "position = " + position);
        switch (position) {

            case 0:
            case 1:
                break;
            case 2:
                //退出群聊 或 解散群
                dismissMenu();
                if (isOwner) {
                    destroyGroupDialog();
                } else {
                    leaveGroupDialog();
                }

                break;
            case 3:
                //取消
                if (mWindow != null) {
                    mWindow.dismiss();
                }
                break;

            default:
        }
    }

    private void destroyGroupDialog() {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.destroy_group)
                    .setMessage(R.string.confirm_destroy_group)
                    .setPositiveButton(R.string.confirm_destroy, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mGroupDetailPresenter.destroyGroup(mGroupId);
                        }
                    })
                    .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    private void leaveGroupDialog() {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.leave_group)
                    .setMessage(R.string.confirm_leave_group)
                    .setPositiveButton(R.string.confirm_leave, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mGroupDetailPresenter.leaveGroup(mGroupId);
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
    public void onAddClick() {
        Bundle bundle = SelectContactFragment.getFragArguments(mGroupId, SelectContactFragment.TYPE_INVITE_CONTACT);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_group_detail_to_nav_select_contact, bundle);
        Toast.makeText(getContext(), "add", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMemberClick(GroupMember pGroupMember) {
        Toast.makeText(getContext(), "" + pGroupMember.getName(), Toast.LENGTH_SHORT).show();
    }
}
