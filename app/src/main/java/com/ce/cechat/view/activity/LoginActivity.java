package com.ce.cechat.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ce.cechat.R;
import com.ce.cechat.view.fragment.login.LoginFragment;
import com.ce.cechat.view.fragment.base.BaseFragment;

import androidx.navigation.Navigation;

/**
 * @author CE Chen
 *
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        if (uri == LoginFragment.ACTION_TO_MAIN_PAGE) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle bundle) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_frag_login).navigateUp();
    }
}

