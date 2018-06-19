package com.ce.cechat.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;

import com.ce.cechat.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

import androidx.navigation.Navigation;

/**
 * @author CE Chen
 *
 * 消息
 *
 * Use the {@link MsgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MsgFragment extends EaseConversationListFragment implements EMMessageListener,
        EaseConversationListFragment.EaseConversationListItemClickListener {

    public MsgFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MsgFragment.
     */
    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }


    @Override
    protected void initView() {
        super.initView();
        EMClient.getInstance().chatManager().addMessageListener(this);

        setConversationListItemClickListener(this);

    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        EaseUI.getInstance().getNotifier().onNewMesg(messages);
        refresh();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> messages) {

    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {

    }

    @Override
    public void onListItemClicked(EMConversation conversation) {
        EMConversation.EMConversationType type = conversation.getType();
        Intent intent = null;
        if (type == EMConversation.EMConversationType.GroupChat) {
//            intent = ChatActivity.getIntent(getContext(), EaseConstant.CHATTYPE_GROUP, conversation.conversationId());
//            startActivity(intent);
            Bundle bundle = new Bundle();
            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
            bundle.putString(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
            Navigation.findNavController(getView()).navigate(R.id.nav_chat, bundle);
        } else if (type == EMConversation.EMConversationType.Chat) {
//            intent = ChatActivity.getIntent(getActivity(), EaseConstant.CHATTYPE_SINGLE, conversation.conversationId());
//            startActivity(intent);
            Bundle bundle = new Bundle();
            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
            bundle.putString(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
            Navigation.findNavController(getView()).navigate(R.id.nav_chat, bundle);
        } else {

        }
    }
}
