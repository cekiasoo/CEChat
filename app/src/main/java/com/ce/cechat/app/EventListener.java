package com.ce.cechat.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ce.cechat.bean.Group;
import com.ce.cechat.bean.Invitation;
import com.ce.cechat.bean.User;
import com.ce.cechat.data.biz.DbBiz;
import com.ce.cechat.event.ContactChangeEvent;
import com.ce.cechat.event.ContactEvent;
import com.ce.cechat.event.GroupEvent;
import com.ce.cechat.utils.ThreadPools;
import com.ce.cechat.utils.SharedPreferencesUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * @author CE Chen
 *
 * 消息的监听类
 */
public class EventListener {

    private volatile static EventListener sEventListener;

    private Context mContext;

    public static final String CONTACT_CHANGED = "CONTACT_CHANGED";

    public static final String CONTACT_INVITE_CHANGED = "CONTACT_INVITE_CHANGED";

    public static final String KEY_IS_NEW_INVITE = "KEY_IS_NEW_INVITE";

    public static final String GROUP_CHANGED = "GROUP_CHANGED";

    private static final String TAG = "EventListener";

    private EventListener(Context pContext) {
        this.mContext = pContext;
        EMClient.getInstance().contactManager().setContactListener(new ChatEmContactListener());
        EMClient.getInstance().groupManager().addGroupChangeListener(new ChatEmGroupChangeListener());
    }

    public static EventListener getInstance(@NonNull Context pContext) {
        if (sEventListener == null) {
            synchronized (EventListener.class) {
                if (sEventListener == null) {
                    sEventListener = new EventListener(pContext);
                }
            }
        }
        return sEventListener;
    }

    public void release() {
        sEventListener = null;
    }

    private class ChatEmGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(final String groupId, final String groupName, final String inviter, final String reason) {
            //当前用户收到加入群组邀请

            Log.v(TAG, "onInvitationReceived groupId = " + groupId
                    + ", groupName = " + groupName + ", inviter = " + inviter + ", reason = " + reason);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库 bean
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(inviter);
                    if (TextUtils.isEmpty(reason)) {
                        invitation.setReason(inviter + " 邀请你加入 " + group.getGroupName() + " 群");
                    } else {
                        invitation.setReason(reason);
                    }
                    invitation.setGroup(group);

                    //新邀请状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.NEW_GROUP_INVITE);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);


                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onRequestToJoinReceived(final String groupId, final String groupName, final String applicant, final String reason) {
            //用户申请加入群

            Log.v(TAG, "onRequestToJoinReceived groupId = " + groupId
                    + ", groupName = " + groupName + ", applicant = " + applicant + ", reason = " + reason);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库的 bean
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(applicant);
                    if (TextUtils.isEmpty(reason)) {
                        invitation.setReason(applicant + " 申请加入 " + group.getGroupName() + " 群");
                    } else {
                        invitation.setReason(reason);
                    }
                    invitation.setGroup(group);

                    //新用户申请加入状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.NEW_GROUP_APPLICATION);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onRequestToJoinAccepted(final String groupId, final String groupName, final String accepter) {
            //加群申请被对方接受

            Log.v(TAG, "onRequestToJoinAccepted groupId = " + groupId + ", groupName = " + groupName + ", accepter = " + accepter);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(accepter);
                    invitation.setReason(groupName + "的管理员同意你加入群");
                    invitation.setGroup(group);

                    //被同意状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_APPLICATION_ACCEPTED);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onRequestToJoinDeclined(final String groupId, final String groupName, final String decliner, String reason) {
            //加群申请被拒绝

            Log.v(TAG, "onRequestToJoinDeclined groupId = " + groupId + ", decliner = " + decliner + ", reason = " + reason);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库的 bean
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(decliner);
                    invitation.setReason(groupName + "的管理员拒绝你加入群");
                    invitation.setGroup(group);

                    //申请被拒绝状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_APPLICATION_DECLINED);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });
        }

        @Override
        public void onInvitationAccepted(final String groupId, final String invitee, String reason) {
            //群组邀请被接受

            Log.v(TAG, "onInvitationAccepted groupId = " + groupId + ", invitee = " + invitee + ", reason = " + reason);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(invitee);
                    invitation.setReason(invitee + " 同意加入 " + groupId + " 群");
                    invitation.setGroup(group);

                    //邀请被同意状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_INVITE_ACCEPTED);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });
        }

        @Override
        public void onInvitationDeclined(final String groupId, final String invitee, String reason) {
            //群组邀请被拒绝

            Log.v(TAG, "onInvitationDeclined groupId = " + groupId + ", invitee = " + invitee + ", reason = " + reason);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    group.setInviteUser(invitee);
                    invitation.setReason("管理员拒绝 " + invitee + " 加入 " + group.getGroupName() + " 群");
                    invitation.setGroup(group);

                    //邀请被拒绝状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_INVITE_DECLINED);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });
        }

        @Override
        public void onUserRemoved(final String groupId, final String groupName) {
            //当前登录用户被管理员移除出群组

            Log.v(TAG, "onUserRemoved groupId = " + groupId + ", groupName = " + groupName );

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    invitation.setReason("管理员已将你移出 " + groupName + " 群");
                    invitation.setGroup(group);

                    //被移出群状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_REMOVE_FROM_GROUP);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onGroupDestroyed(final String groupId, final String groupName) {
            //群组被解散。
            //sdk 会先删除本地的这个群组，之后通过此回调通知应用，此群组被删除了

            Log.v(TAG, "onGroupDestroyed groupId = " + groupId + ", groupName = " + groupName );

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    invitation.setReason(groupName + " 群已被解散");
                    invitation.setGroup(group);

                    //群被解散状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_DESTROY_GROUP);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onAutoAcceptInvitationFromGroup(final String groupId, final String inviter, String inviteMessage) {
            //自动同意加入群组
            //sdk会先加入这个群组，并通过此回调通知应用

            Log.v(TAG, "onAutoAcceptInvitationFromGroup groupId = " + groupId
                    + ", inviter = " + inviter + ", inviteMessage = " + inviteMessage);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);

                    try {
                        EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }
                    group.setInviteUser(inviter);
                    invitation.setReason("你已自动加入了 " + group.getGroupName() + " 群");
                    invitation.setGroup(group);

                    //自动同意加入群状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_AUTO_ACCEPT_INVITE);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onMuteListAdded(String groupId, List<String> mutes, long muteExpire) {
            //有成员被禁言，此处不同于blacklist
            Log.v(TAG, "onMuteListAdded groupId = " + groupId + ", mutes = " + mutes.toArray());

        }

        @Override
        public void onMuteListRemoved(String groupId, List<String> mutes) {
            //有成员从禁言列表中移除，恢复发言权限，此处不同于blacklist
            Log.v(TAG, "onMuteListRemoved groupId = " + groupId + ", mutes = " + mutes.toArray());
        }

        @Override
        public void onAdminAdded(String groupId, String administrator) {
            //添加成员管理员权限
            Log.v(TAG, "onAdminAdded groupId = " + groupId + ", administrator = " + administrator);
            EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
        }

        @Override
        public void onAdminRemoved(String groupId, String administrator) {
            //取消某管理员权限

            Log.v(TAG, "onAdminRemoved groupId = " + groupId + ", administrator = " + administrator);
            EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
        }

        @Override
        public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
            //转移群组所有者权限

            Log.v(TAG, "onOwnerChanged groupId = " + groupId + ", newOwner = " + newOwner + ", oldOwner = " + oldOwner);

        }

        @Override
        public void onMemberJoined(final String groupId, final String member) {
            //群组加入新成员事件

            Log.v(TAG, "onMemberJoined groupId = " + groupId + ", member = " + member);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);
                    EMGroup emGroup = null;
                    try {
                        emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    invitation.setReason(member + " 加入 " + group.getGroupName() + " 群");
                    invitation.setGroup(group);

                    //群成员加入群状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_MEMBER_JOIN);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });

        }

        @Override
        public void onMemberExited(final String groupId, final String member) {
            //群组成员主动退出事件

            Log.v(TAG, "onMemberExited groupId = " + groupId + ", member = " + member);

            ThreadPools.newInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //保存到数据库
                    Invitation invitation = new Invitation();
                    Group group = new Group();
                    group.setGroupId(groupId);
                    EMGroup emGroup;
                    try {
                        emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                        group.setGroupName(emGroup.getGroupName());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        group.setGroupName(groupId);
                    }

                    invitation.setReason(member + " 退出 " + group.getGroupName() + " 群");
                    invitation.setGroup(group);

                    //群成员退出群状态
                    invitation.setInvitationStatus(Invitation.InvitationStatus.GROUP_MEMBER_LEAVE);

                    //保存到数据库
                    DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

                    //保存消息小红点
                    SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

                    EventBus.getDefault().post(new GroupEvent(GROUP_CHANGED));
                }
            });
        }

        @Override
        public void onAnnouncementChanged(String groupId, String announcement) {
            //群公告更改事件

            Log.v(TAG, "onAnnouncementChanged groupId = " + groupId + ", announcement = " + announcement);
        }

        @Override
        public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {
            //群组增加共享文件事件

            Log.v(TAG, "onSharedFileAdded groupId = " + groupId + ", announcement = " + sharedFile.getFileName());
        }

        @Override
        public void onSharedFileDeleted(String groupId, String fileId) {
            //群组删除共享文件事件
            Log.v(TAG, "onSharedFileDeleted groupId = " + groupId + ", fileId = " + fileId);
        }
    }

    private class ChatEmContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            //增加联系人时回调此方法

            Log.v(TAG, "onContactAdded " + username);

            //添加到数据库
            DbBiz.newInstance().getDbManager().getContactDao().saveContact(new User(username), true);

            EventBus.getDefault().post(new ContactEvent(CONTACT_CHANGED));
            EventBus.getDefault().post(new ContactChangeEvent());

        }

        @Override
        public void onContactDeleted(String username) {
            //联系人被删除时回调此方法

            Log.v(TAG, "onContactDeleted " + username);

            //删除在数据库中的信息
            DbBiz.newInstance().getDbManager().getContactDao().deleteContactByHyphenateId(username);
            DbBiz.newInstance().getDbManager().getInvitationDao().deleteInvitationByHyphenateId(username);

            EventBus.getDefault().post(new ContactEvent(CONTACT_CHANGED));
            EventBus.getDefault().post(new ContactChangeEvent());
        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请

            Log.v(TAG, "onContactInvited " + username);

            //添加到数据库
            Invitation invitation = new Invitation();
            invitation.setUser(new User(username));
            invitation.setReason(reason);
            invitation.setInvitationStatus(Invitation.InvitationStatus.NEW_INVITE);
            DbBiz.newInstance().getDbManager().getInvitationDao().addInvitation(invitation);

            //保存消息小红点
            SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);


            EventBus.getDefault().post(new ContactEvent(CONTACT_INVITE_CHANGED));

        }

        @Override
        public void onFriendRequestAccepted(String username) {
            //好友请求被同意

            Log.v(TAG, "onFriendRequestAccepted " + username);

            Invitation invitation = new Invitation();
            invitation.setUser(new User(username));
            invitation.setReason(username + " 同意了你的好友请求");
            invitation.setInvitationStatus(Invitation.InvitationStatus.INVITE_ACCEPT_BY_PEER);

            DbBiz.newInstance()
                    .getDbManager()
                    .getInvitationDao()
                    .addInvitation(invitation);

            //保存消息小红点
            SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

            EventBus.getDefault().post(new ContactEvent(CONTACT_INVITE_CHANGED));
            EventBus.getDefault().post(new ContactChangeEvent());
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            //好友请求被拒绝

            Log.v(TAG, "onFriendRequestDeclined " + username);

            Invitation invitation = new Invitation();
            invitation.setUser(new User(username));
            invitation.setReason(username + " 拒绝了你的好友请求");
            invitation.setInvitationStatus(Invitation.InvitationStatus.INVITE_REFUSE_BY_PEER);

            DbBiz.newInstance()
                    .getDbManager()
                    .getInvitationDao()
                    .addInvitation(invitation);

            //保存消息小红点
            SharedPreferencesUtils.put(mContext, KEY_IS_NEW_INVITE, true);

            EventBus.getDefault().post(new ContactEvent(CONTACT_INVITE_CHANGED));
        }
    }



}
