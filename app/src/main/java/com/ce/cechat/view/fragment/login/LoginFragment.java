package com.ce.cechat.view.fragment.login;

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
import com.ce.cechat.presenter.LoginPresenter;
import com.ce.cechat.utils.ErrorCode;
import com.ce.cechat.view.fragment.base.BaseFragment;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CE Chen
 * <p>
 * 用于登录
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment implements ILoginView {

    public static final String TAG = "LoginFragment";

    public static final Uri ACTION_TO_SIGN_UP = Uri.parse("LoginFragment_ACTION_TO_SIGN_UP");
    public static final Uri ACTION_TO_MAIN_PAGE = Uri.parse("LoginFragment_ACTION_TO_MAIN_PAGE");

    @BindView(R.id.pb_login_progress)
    ProgressBar pbLoginProgress;
    @BindView(R.id.actv_email)
    AutoCompleteTextView actvName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_new_user_sign_up)
    TextView tvNewUserSignUp;
    @BindView(R.id.btn_login_button)
    Button btnLoginButton;
    @BindView(R.id.login_form)
    ScrollView loginForm;

    private LoginPresenter mLoginPresenter;

    public LoginFragment() {
        // Required empty public constructor

        mLoginPresenter = new LoginPresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        return new LoginFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Reset errors.
        actvName.setError(null);
        etPassword.setError(null);
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @OnClick(R.id.tv_new_user_sign_up)
    public void onTvNewUserSignUpClicked() {
        Navigation.findNavController(tvNewUserSignUp).navigate(R.id.action_nav_login_to_nav_sign_up);
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

        if (mLoginPresenter.isEmptyUserId()) {
            return;
        }

        if (mLoginPresenter.isEmptyPassword()) {
            return;
        }

        if (!mLoginPresenter.isUserIdValid()) {
            return;
        }

        showProgressBar();
        mLoginPresenter.login();

    }


    @OnClick(R.id.btn_login_button)
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
        onButtonPressed(ACTION_TO_MAIN_PAGE);
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
    public boolean isActive() {
        return isAdded();
    }

}
