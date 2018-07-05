package com.ce.cechat.ui.login;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ce.cechat.R;
import com.ce.cechat.app.InjectFragment;
import com.ce.cechat.utils.ErrorCode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * <p>
 * 用于注册
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends InjectFragment<SignUpPresenter> implements ISignUpContract.ISignUpView {

    public static final String TAG = "SignUpFragment";

    public static final Uri ACTION_BACK = Uri.parse("SignUpFragment_back");

    @BindView(R.id.pb_login_progress)
    ProgressBar pbLoginProgress;
    @BindView(R.id.actv_name)
    AutoCompleteTextView actvName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.login_form)
    ScrollView loginForm;


    public SignUpFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignUpFragment.
     */
    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        actvName.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        if (mPresenter.isEmptyUserId()) {
            return;
        }

        if (!mPresenter.isUserIdValid()) {
            return;
        }

        if (mPresenter.isEmptyPassword()) {
            return;
        }

        if (!mPresenter.isPasswordVail()) {
            return;
        }

        if (mPresenter.isConfirmPasswordEmpty()) {
            return;
        }

        if (!mPresenter.isConfirmPasswordVail()) {
            return;
        }

        showProgressBar();
        mPresenter.signUp();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {
        attemptLogin();
    }

    @Override
    public String getUserId() {
        return actvName.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return etConfirmPassword.getText().toString();
    }

    @Override
    public void showProgressBar() {
        pbLoginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbLoginProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {
        hideProgressBar();
        Snackbar.make(loginForm, R.string.sign_up_success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(int code, String error) {
        hideProgressBar();
        if (getContext() != null) {
            Snackbar.make(loginForm, ErrorCode.errorCodeToString(getContext(), code), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void emptyUserId() {
        View focusView = null;
        actvName.setError(getString(R.string.error_empty_user_id_required));
        focusView = actvName;
        focusView.requestFocus();
    }

    @Override
    public void emptyPassword() {
        View focusView = null;
        etPassword.setError(getString(R.string.error_empty_password));
        focusView = etPassword;
        focusView.requestFocus();
    }

    @Override
    public void invalidUserId() {
        View focusView = null;
        actvName.setError(getString(R.string.error_invalid_user_id));
        focusView = actvName;
        focusView.requestFocus();
    }

    @Override
    public void emptyConfirmPassword() {
        View focusView = null;
        etConfirmPassword.setError(getString(R.string.error_empty_confirm_password));
        focusView = etConfirmPassword;
        focusView.requestFocus();
    }

    @Override
    public void invalidPassword() {
        View focusView = null;
        etPassword.setError(getString(R.string.error_invalid_password));
        focusView = etPassword;
        focusView.requestFocus();
    }

    @Override
    public void invalidConfirmPassword() {
        View focusView = null;
        etConfirmPassword.setError(getString(R.string.error_invalid_confirm_password));
        focusView = etConfirmPassword;
        focusView.requestFocus();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
