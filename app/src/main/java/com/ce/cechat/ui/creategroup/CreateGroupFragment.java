package com.ce.cechat.ui.creategroup;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.app.InjectFragment;
import com.ce.cechat.ui.selectcontact.SelectContactFragment;
import com.ce.cechat.utils.ErrorCode;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * Use the {@link CreateGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupFragment extends InjectFragment<CreateGroupPresenter> implements ICreateGroupContract.ICreateGroupView {

    private static final int GROUP_MAX_USERS = 500;

    @BindView(R.id.et_group_name)
    AppCompatEditText etGroupName;
    @BindView(R.id.et_group_description)
    AppCompatEditText etGroupDescription;
    @BindView(R.id.et_group_reason)
    AppCompatEditText etGroupReason;
    @BindView(R.id.rg_option)
    RadioGroup rgOption;
    @BindView(R.id.rg_check)
    RadioGroup rgCheck;
    @BindView(R.id.btn_select_contact)
    AppCompatButton btnSelectContact;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.rb_unpublic)
    RadioButton rbUnpublic;
    @BindView(R.id.rb_public)
    RadioButton rbPublic;
    @BindView(R.id.rb_dont_check)
    RadioButton rbDontCheck;
    @BindView(R.id.rb_check)
    RadioButton rbCheck;

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateGroupFragment.
     */
    public static CreateGroupFragment newInstance() {
        CreateGroupFragment fragment = new CreateGroupFragment();
        return fragment;
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_group;
    }

    @Override
    public void createGroupSuccess(EMGroup value) {
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    getString(R.string.create_group_success, value.getGroupName()),
                    Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(btnSubmit).navigateUp();
    }

    @Override
    public void createGroupFailed(int error, String errorMsg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), ErrorCode.errorCodeToString(getContext(), error), Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(btnSubmit).navigateUp();
    }

    @Override
    public void groupNameEmpty() {
        View focusView = null;
        etGroupName.setError(getString(R.string.error_empty_group_name));
        focusView = etGroupName;
        focusView.requestFocus();
    }

    @Override
    public void groupDescriptionEmpty() {
        View focusView = null;
        etGroupDescription.setError(getString(R.string.error_empty_group_description));
        focusView = etGroupDescription;
        focusView.requestFocus();
    }

    @Override
    public void groupReasonEmpty() {
        View focusView = null;
        etGroupReason.setError(getString(R.string.error_empty_group_reason));
        focusView = etGroupReason;
        focusView.requestFocus();
    }

    @Override
    public String getGroupName() {
        return etGroupName.getText().toString();
    }

    @Override
    public String getGroupDescription() {
        return etGroupDescription.getText().toString();
    }

    @Override
    public String getGroupReason() {
        return etGroupReason.getText().toString();
    }

    @Override
    public boolean getPublic() {
        return rbPublic.isChecked();
    }

    @Override
    public boolean getCheck() {
        return rbCheck.isChecked();
    }


    @OnClick(R.id.btn_select_contact)
    public void onSelectContactClicked() {
        if (mPresenter.isGroupNameEmpty()) {
            return;
        }
        if (mPresenter.isGroupDescriptionEmpty()) {
            return;
        }
        if (mPresenter.isGroupReasonEmpty()) {
            return;
        }
        EMGroupManager.EMGroupStyle style = mPresenter.getGroupStyle();
        Bundle arguments = SelectContactFragment
                .getFragArguments(getGroupName(),
                        getGroupDescription(),
                        getGroupReason(),
                        style.ordinal(),
                        GROUP_MAX_USERS,
                        SelectContactFragment.TYPE_CREATE_GROUP);

        Navigation.findNavController(btnSelectContact).navigate(R.id.action_nav_create_group_to_nav_select_contact, arguments);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        submit();
    }

    private void submit() {
        if (mPresenter.isGroupNameEmpty()) {
            return;
        }
        if (mPresenter.isGroupDescriptionEmpty()) {
            return;
        }
        if (mPresenter.isGroupReasonEmpty()) {
            return;
        }

        EMGroupOptions options = new EMGroupOptions();
        options.maxUsers = GROUP_MAX_USERS;

        options.style = mPresenter.getGroupStyle();

        mPresenter.createGroup(getGroupName(), getGroupDescription(), new String[]{}, getGroupReason(), options);

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
