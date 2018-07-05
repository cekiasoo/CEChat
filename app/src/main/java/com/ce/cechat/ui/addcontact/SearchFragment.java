package com.ce.cechat.ui.addcontact;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.cechat.R;
import com.ce.cechat.app.InjectFragment;
import com.ce.cechat.bean.User;
import com.ce.cechat.utils.ErrorCode;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * 搜索添加好友
 * <p>
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends InjectFragment<SearchPresenter> implements ISearchContract.ISearchView {

    @BindView(R.id.lay_content)
    ConstraintLayout layContent;
    @BindView(R.id.tv_search)
    AppCompatTextView tvSearch;
    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_head)
    AppCompatImageView ivHead;
    @BindView(R.id.tv_nickname)
    AppCompatTextView tvNickname;
    @BindView(R.id.tv_add)
    AppCompatTextView tvAdd;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 查找用户
     */
    private void search() {
        hideUser();
        etSearch.setError(null);
        if (mPresenter.isUserIdEmpty(etSearch.getText().toString())) {
            return;
        }
        mPresenter.search();
    }

    private void addNewFriend() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_add_new_friend_reason, null, false);
        final EditText etReason = view.findViewById(R.id.et_reason);

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_friend)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = etReason.getText().toString();
                        mPresenter.addNewFriend(reason);
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
        return R.layout.fragment_search;
    }

    @OnClick({R.id.tv_search, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                search();
                break;
            case R.id.tv_add:
                addNewFriend();
                break;
            default:
                break;
        }
    }

    private void hideUser() {
        ivHead.setVisibility(View.GONE);
        tvNickname.setVisibility(View.GONE);
        tvAdd.setVisibility(View.GONE);
    }

    private void showUser() {
        ivHead.setVisibility(View.VISIBLE);
        tvNickname.setVisibility(View.VISIBLE);
        tvAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public String getUserId() {
        return etSearch.getText().toString();
    }

    @Override
    public String getAddUserId() {
        return tvNickname.getText().toString();
    }

    @Override
    public void searchSuccess(User pUser) {
        tvNickname.setText(pUser.getName());
        showUser();
    }

    @Override
    public void searchFailed() {
        hideUser();
        Snackbar.make(etSearch, R.string.user_not_found, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void emptyUserId() {
        View focusView = null;
        etSearch.setError(getString(R.string.user_id_cannot_null));
        focusView = etSearch;
        focusView.requestFocus();
    }

    @Override
    public void addSuccess() {
        Toast.makeText(getContext(), getString(R.string.add_new_friend_success, getAddUserId()), Toast.LENGTH_SHORT).show();
        Navigation.findNavController(layContent).navigateUp();
    }

    @Override
    public void addFailed(int code, String error) {
        if (getContext() != null) {
            Snackbar.make(layContent, ErrorCode.errorCodeToString(getContext(), code), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearSearch() {
        etSearch.getEditableText().clear();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
