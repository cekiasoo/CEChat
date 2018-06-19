package com.ce.cechat.model.biz;

import android.util.Log;

import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.thread.ThreadPools;
import com.ce.cechat.utils.CommonUtils;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * 群资料业务逻辑操作类
 */
public class GroupDetailBiz implements IGroupDetailBiz {

    private static final String TAG = "GroupDetailBiz";

    @Override
    public String getGroupName(final String pGroupId) {

        EMGroup group = EMClient.getInstance().groupManager().getGroup(pGroupId);
        if (group != null) {
            return group.getGroupName();
        }
        return pGroupId;
    }

    @Override
    public boolean isGroupOwner(String pOwnerId) {
        String user = EMClient.getInstance().getCurrentUser();
        return pOwnerId.equals(user);
    }

    @Override
    public boolean isGroupOwner(EMGroup pGroup) {
        GroupMember owner = getOwner(pGroup);
        return isGroupOwner(owner.getHyphenateId());
    }

    @Override
    public void destroyGroup(final String pGroupId, final IDestroyGroupListener pDestroyGroupListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().destroyGroup(pGroupId);
                    pDestroyGroupListener.destroyGroupSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pDestroyGroupListener.destroyGroupFailed(e);
                }
            }
        });
    }

    @Override
    public void leaveGroup(final String pGroupId, final ILeaveGroupListener pLeaveGroupListener) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().leaveGroup(pGroupId);
                    pLeaveGroupListener.leaveGroupSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    pLeaveGroupListener.leaveGroupFailed(e);
                }
            }
        });
    }

    @Override
    public void getGroupDetail(final String pGroupId, final EMValueCallBack<EMGroup> callback) {
        ThreadPools.newInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(pGroupId, true);
                    callback.onSuccess(emGroup);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    callback.onError(e.getErrorCode(), e.getDescription());
                }
            }
        });
    }


    @Override
    public List<GroupMember> getGroupAllMembers(EMGroup pGroup) {
        List<GroupMember> members = stringList2GroupMember(pGroup.getMembers(), GroupMember.MEMBER);
        List<GroupMember> admin = stringList2GroupMember(pGroup.getAdminList(), GroupMember.ADMIN);
        List<GroupMember> result = new LinkedList<>();
        sort(members);
        sort(admin);
        result.addAll(members);
        result.addAll(0, admin);
        result.add(0, new GroupMember(pGroup.getOwner(), GroupMember.OWNER));
        return result;
    }

    @Override
    public List<GroupMember> getGroupAllAdmins(EMGroup pGroup) {
        List<GroupMember> admin = stringList2GroupMember(pGroup.getAdminList(), GroupMember.ADMIN);
        sort(admin);
        return admin;
    }

    @Override
    public GroupMember getOwner(EMGroup pGroup) {
        return new GroupMember(pGroup.getOwner(), GroupMember.OWNER);
    }


    /**
     * 排序
     * @param pUsers Users
     */
    private void sort(List<? extends User> pUsers) {
        // sorting
        Collections.sort(pUsers, new Comparator<User>() {

            @Override
            public int compare(User lhs, User rhs) {
                if(CommonUtils.getInitialLetter(lhs).equals(CommonUtils.getInitialLetter(rhs))){
                    return lhs.getName().compareTo(rhs.getName());
                }else{
                    if("#".equals(CommonUtils.getInitialLetter(lhs))){
                        return 1;
                    }else if("#".equals(CommonUtils.getInitialLetter(rhs))){
                        return -1;
                    }
                    return CommonUtils.getInitialLetter(lhs).compareTo(CommonUtils.getInitialLetter(rhs));
                }

            }
        });
    }

    @Override
    public List<GroupMember> stringList2GroupMember(List<String> pUserList, int pIdentity) {
        List<GroupMember> groupMembers = new LinkedList<>();
        for (String user : pUserList) {
            Log.v(TAG, "user = " + user);
            groupMembers.add(new GroupMember(user, pIdentity));
        }
        return groupMembers;
    }

    @Override
    public boolean isOwnerOrAdmin(EMGroup pGroup) {

        String currentUser = EMClient.getInstance().getCurrentUser();
        String owner = pGroup.getOwner();
        if (owner.equals(currentUser)) {
            return true;
        }

        return isGroupAdmin(pGroup);
    }

    @Override
    public boolean isGroupAdmin(EMGroup pGroup) {
        String currentUser = EMClient.getInstance().getCurrentUser();
        List<String> adminList = pGroup.getAdminList();
        for (String admin : adminList) {
            if (admin.equals(currentUser)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public LinkedList<User> getAllNotInGroupContact(EMGroup pGroup) {

        List<GroupMember> allMembers = getGroupAllMembers(pGroup);
        List<User> contacts = DbBiz.newInstance().getDbManager().getContactDao().getContacts();
        sort(contacts);
        LinkedList<User> result = new LinkedList<>();
        for (User user :
                contacts) {
            boolean isIn = false;
            for (GroupMember member :
                 allMembers) {
                if (user.getHyphenateId().equals(member.getHyphenateId())) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                result.add(user);
            }
        }

        for (User s : result) {
            Log.v(TAG, "result = " + s);
        }

        return result;
    }


}
