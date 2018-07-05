package com.ce.cechat.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import com.ce.cechat.R;
import com.ce.cechat.app.BaseFragment;
import com.ce.cechat.ui.groupdetail.GroupDetailFragment;
import com.hyphenate.easeui.EaseConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author CE Chen
 *
 *
 */
public class ChatActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    private static final String TAG = "ChatActivity";

    @Override
    public void onFragmentInteraction(Uri uri) {
        if (GroupDetailFragment.ACTION_FINISH.equals(uri)) {
            finish();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle bundle) {

    }

    @IntDef({EaseConstant.CHATTYPE_SINGLE, EaseConstant.CHATTYPE_GROUP, EaseConstant.CHATTYPE_CHATROOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChatType {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        int chatType = intent.getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        String userId = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frag_chat);
        NavGraph inflate = navHostFragment.getNavController().getNavInflater().inflate(R.navigation.nav_chat);
        NavDestination node = inflate.findNode(R.id.nav_chat);

        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        bundle.putString(EaseConstant.EXTRA_USER_ID, userId);
        node.setDefaultArguments(bundle);
        navHostFragment.getNavController().setGraph(inflate);
    }

    public static Intent getIntent(Context pContext, @ChatType int pChatType, String pUserId) {
        Intent intent = new Intent(pContext, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, pChatType);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, pUserId);
        return intent;
    }

}
