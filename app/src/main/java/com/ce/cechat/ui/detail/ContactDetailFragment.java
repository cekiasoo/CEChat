package com.ce.cechat.ui.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.app.InjectFragment;
import com.ce.cechat.utils.ErrorCode;
import com.hyphenate.chat.EMContact;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * <p>
 * Use the {@link ContactDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailFragment extends InjectFragment<DetailPresenter>
        implements AdapterView.OnItemClickListener, IDetailContract.IContactDetailView {

    private static final String KEY_USER = "KEY_USER";

    private static final String KEY_USER_ID = "KEY_USER_ID";

    private static final String TAG = "ContactDetailFragment";

    public static final Uri ACTION_BACK = Uri.parse("ContactDetailFragment_ACTION_BACK");

    public static final Uri ACTION_CHAT = Uri.parse("ContactDetailFragment_ACTION_CHAT");

    @BindView(R.id.lay_content)
    ConstraintLayout layContent;
    @BindView(R.id.title_bar)
    EaseTitleBar titleBar;
    @BindView(R.id.iv_head)
    AppCompatImageView ivHead;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.lay_name)
    ConstraintLayout layName;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.btn_send_msg)
    Button btnSendMsg;


    private EMContact mUser;

    private PopupWindow mWindow;

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactDetailFragment.
     */
    public static ContactDetailFragment newInstance() {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                mUser = intent.getParcelableExtra(KEY_USER);
                Log.v(TAG, "getActivity onCreate " + mUser);
            }
        }
    }

    @Override
    protected void initView(View view) {

        if (mUser != null) {
            tvName.setText(mUser.getUsername());
        }

    }

    @Override
    protected void setListener() {
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(ACTION_BACK);
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
    }

    /**
     * 显示菜单列表
     */
    private void showPopupMenu() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_popup_menu, null, false);
        mWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = view.findViewById(R.id.lv_menu);
        listView.setAdapter(new DetailMenuAdapter(getContext()));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mWindow != null) {
            mWindow.dismiss();
        }

        switch (position) {
            case 0:
                //
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                //删除
                if (mUser != null) {
                    showDeleteDialog();
                }
                break;
            default:
        }

    }

    /**
     * 显示确定删除对话框
     */
    private void showDeleteDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete_contact)
                .setMessage(getString(R.string.will_delete_contact, mUser.getUsername()))
                .setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteContact(mUser.getUsername());
                    }
                })
                .setNegativeButton(R.string.cancels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_detail;
    }


    @OnClick(R.id.lay_name)
    public void onLayNameClicked() {

    }

    @OnClick(R.id.tv_more)
    public void onTvMoreClicked() {

    }

    @OnClick(R.id.btn_send_msg)
    public void onBtnSendMsgClicked() {
        Log.v(TAG, "mUser = " + mUser);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_ID, mUser.getUsername());
        onButtonPressed(ACTION_CHAT, bundle);
    }

    @Override
    public void onDeleteSuccess() {
        Log.v(TAG, "onDeleteSuccess");
        if (getContext() != null) {
            Toast.makeText(getContext(), getString(R.string.delete_contact_success, mUser.getUsername()), Toast.LENGTH_SHORT).show();
        }
        onButtonPressed(ACTION_BACK);
    }

    @Override
    public void onDeleteFailed(HyphenateException e) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), e.getErrorCode()), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
