package com.ce.cechat.ui.chat;

import android.os.Bundle;
import android.view.View;

import com.ce.cechat.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import androidx.navigation.Navigation;

/**
 * @author CE Chen
 *
 *
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setChatFragmentHelper(this);
    }


    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            Bundle bundle = new Bundle();
            bundle.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
            Navigation.findNavController(getView()).navigate(R.id.action_nav_chat_to_nav_group_detail, bundle);
        }
    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}
