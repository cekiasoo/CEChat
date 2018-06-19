package com.ce.cechat.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ce.cechat.R;
import com.ce.cechat.view.fragment.base.BaseFragment;
import com.ce.cechat.view.fragment.detail.ContactDetailFragment;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EMGroup;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author CE Chen
 */
public class ContactDetailActivity extends AppCompatActivity
        implements BaseFragment.OnFragmentInteractionListener, NavController.OnNavigatedListener {

    private static final String KEY_USER = "KEY_USER";

    private static final String KEY_USER_ID = "KEY_USER_ID";

    private static final String TAG = "ContactDetailActivity";

    private NavHostFragment mNavHostFragment;

    public static Intent getIntent(Context pContext, EMGroup pUser) {
        Intent intent = new Intent(pContext, ContactDetailActivity.class);
        intent.putExtra(KEY_USER, pUser);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_detail);
        NavController navController = mNavHostFragment.getNavController();
        navController.addOnNavigatedListener(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_frag_detail).navigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        if (uri == ContactDetailFragment.ACTION_BACK) {
            finish();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle bundle) {
        if (uri == ContactDetailFragment.ACTION_CHAT) {
            String userId = bundle.getString(KEY_USER_ID);
            /*Intent intent = ChatActivity.getIntent(this, EaseConstant.CHATTYPE_SINGLE, userId);
            startActivity(intent);
            finish();*/
            Bundle argument = new Bundle();
            argument.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
            argument.putString(EaseConstant.EXTRA_USER_ID, userId);
            mNavHostFragment.getNavController().navigate(R.id.nav_chat, argument);
            finish();
        }
    }

    @Override
    public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
        Log.v(TAG, "destination id = " + destination.getId() + ", nav_detail = " + R.id.nav_detail);
        int id = destination.getId();
        switch (id) {
            case R.id.nav_detail:

                break;
            default:

        }
    }
}
