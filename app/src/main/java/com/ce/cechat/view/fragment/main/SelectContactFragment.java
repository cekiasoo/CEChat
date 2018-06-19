package com.ce.cechat.view.fragment.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.model.bean.User;
import com.ce.cechat.presenter.SelectContactPresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.adapter.SelectContactAdapter;
import com.ce.cechat.view.fragment.base.BaseFragment;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.navigation.Navigation;
import butterknife.BindView;

/**
 * @author CE Chen
 * <p>
 * Use the {@link SelectContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectContactFragment extends BaseFragment implements ISelectContactView{

    private static final String TAG = "SelectContactFragment";
    private static final String ARG_GROUP_NAME = "ARG_GROUP_NAME";
    private static final String ARG_GROUP_DESCRIPTION = "ARG_GROUP_DESCRIPTION";
    private static final String ARG_GROUP_REASON = "ARG_GROUP_REASON";
    private static final String ARG_GROUP_STYLE = "ARG_GROUP_STYLE";
    private static final String ARG_GROUP_MAX_USERS = "ARG_GROUP_MAX_USERS";

    private static final String ARG_TYPE = "ARG_TYPE";
    public static final int TYPE_INVITE_CONTACT = 1;
    public static final int TYPE_CREATE_GROUP = TYPE_INVITE_CONTACT + 1;

    @IntDef({TYPE_CREATE_GROUP, TYPE_INVITE_CONTACT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {

    }

    private static final String ARG_GROUP_ID = "ARG_GROUP_ID";
    private static final String ARG_CONTACT = "ARG_CONTACT";

    @BindView(R.id.title_bar)
    EaseTitleBar titleBar;
    @BindView(R.id.lv_contacts)
    ListView lvContacts;

    protected SelectContactAdapter adapter;
    @BindView(R.id.sidebar)
    EaseSidebar sidebar;

    private Set<String> mUsers = new HashSet<>();

    private String mArgGroupName;
    private String mArgGroupDescription;
    private String mArgGroupReason;
    private int mArgGroupStyle;
    private int mArgGroupMaxUsers;

    private int mArgType;

    private String mArgGroupId;

    private SelectContactPresenter mSelectContactPresenter;

    public SelectContactFragment() {
        // Required empty public constructor
        mSelectContactPresenter = new SelectContactPresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectContactFragment.
     */
    public static SelectContactFragment newInstance() {
        SelectContactFragment fragment = new SelectContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle getFragArguments(String pGroupName, String pGroupDescription,
                                          String pGroupReason, int pStyle, int pMaxUsers,
                                          @Type int pType) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_GROUP_NAME, pGroupName);
        bundle.putString(ARG_GROUP_DESCRIPTION, pGroupDescription);
        bundle.putString(ARG_GROUP_REASON, pGroupReason);
        bundle.putInt(ARG_GROUP_STYLE, pStyle);
        bundle.putInt(ARG_GROUP_MAX_USERS, pMaxUsers);
        bundle.putInt(ARG_TYPE, pType);
        return bundle;
    }

    public static Bundle getFragArguments(String pGroupId, @Type int pType) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_GROUP_ID, pGroupId);
        bundle.putInt(ARG_TYPE, pType);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mArgType = getArguments().getInt(ARG_TYPE);
            if (mArgType == TYPE_CREATE_GROUP) {
                parseCreateGroupParam();
                Log.v(TAG, "mArgGroupName = " + mArgGroupName + ", mArgGroupDescription = " + mArgGroupDescription);
            } else if (mArgType == TYPE_INVITE_CONTACT) {
                parseInviteContactParam();
            }
        }

        if (mArgType == TYPE_CREATE_GROUP) {
            adapter = new SelectContactAdapter(getContext(), lvContacts, 0, new ArrayList<User>());
            lvContacts.setAdapter(adapter);
            sidebar.setListView(lvContacts);
            mSelectContactPresenter.getAllContact();
        } else if (mArgType == TYPE_INVITE_CONTACT) {
            adapter = new SelectContactAdapter(getContext(), lvContacts, 0, new LinkedList<User>());
            lvContacts.setAdapter(adapter);
            sidebar.setListView(lvContacts);
            mSelectContactPresenter.getGroupDetail(mArgGroupId);
        }

    }



    private void parseCreateGroupParam() {
        mArgGroupName = getArguments().getString(ARG_GROUP_NAME);
        mArgGroupDescription = getArguments().getString(ARG_GROUP_DESCRIPTION);
        mArgGroupReason = getArguments().getString(ARG_GROUP_REASON);
        mArgGroupStyle = getArguments().getInt(ARG_GROUP_STYLE);
        mArgGroupMaxUsers = getArguments().getInt(ARG_GROUP_MAX_USERS);
    }

    private void parseInviteContactParam() {
        mArgGroupId = getArguments().getString(ARG_GROUP_ID);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mArgType == TYPE_CREATE_GROUP) {
                    createGroup();
                } else if (mArgType == TYPE_INVITE_CONTACT){
                    inviteContact();
                }
            }
        });
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = adapter.getItem(position);
                if (lvContacts.isItemChecked(position)) {
                    if (user != null) {
                        mUsers.add(user.getName());
                    }
                    Log.v(TAG, "isItemChecked " + user);
                } else {
                    if (user != null) {
                        mUsers.remove(user.getName());
                    }
                    Log.v(TAG, "isItemUnChecked " + user);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void createGroup() {
        EMGroupOptions options = new EMGroupOptions();
        options.maxUsers = mArgGroupMaxUsers;
        options.style = mSelectContactPresenter.getGroupStyle(mArgGroupStyle);
        mSelectContactPresenter.createGroup(
                mArgGroupName,
                mArgGroupDescription,
                mUsers.toArray(new String[]{}),
                mArgGroupReason,
                options);
    }

    private void inviteContact() {
        Log.v(TAG, "inviteContact");
        inviteReason();
    }

    private void inviteReason() {
        if (getContext() != null) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_invite_reason, null, false);
            final EditText etReason = view.findViewById(R.id.et_reason);

            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.invite_reason)
                    .setView(view)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String reason = etReason.getText().toString();
                            Log.v(TAG, "inviteReason reason = " + reason);
                            mSelectContactPresenter
                                    .inviteContactsInGroup(mArgGroupId, new LinkedList<String>(mUsers), reason);
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
    protected int getLayoutId() {
        return R.layout.fragment_select_contact;
    }

    @Override
    public void refresh(List<User> pUsers) {
        Log.v(TAG, "pUsers = " + pUsers.size());
        adapter.refresh(pUsers);
    }

    @Override
    public void createGroupSuccess(EMGroup value) {
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    getString(R.string.create_group_success, value.getGroupName()),
                    Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(lvContacts).popBackStack(R.id.nav_group_list, false);
    }

    @Override
    public void createGroupFailed(int error, String errorMsg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(lvContacts).popBackStack(R.id.nav_group_list, false);
    }

    @Override
    public void inviteContactsSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.send_invite_contact_in_group,
                    Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(lvContacts).navigateUp();
    }

    @Override
    public void inviteContactsFailed(int code, String error) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), code), Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(lvContacts).navigateUp();
    }

    @Override
    public void getGroupDetailSuccess(EMGroup value) {
        LinkedList<User> contact = mSelectContactPresenter.getAllNotInGroupContact(value);
        refresh(contact);
    }

    @Override
    public void getGroupDetailFailed(int error, String errorMsg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
