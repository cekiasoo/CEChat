package com.ce.cechat.model.biz;

import com.ce.cechat.model.bean.GroupMember;
import com.ce.cechat.model.bean.User;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * 群资料业务逻辑操作接口
 */
public interface IGroupDetailBiz {

    /**
     * 根据群 Id 获取群名称
     * @param pGroupId 群 id
     * @return 群名称
     */
    String getGroupName(String pGroupId);

    /**
     * 是否是群主
     * @param pGroupId GroupId
     * @return 是返回true 否返回false
     */
    boolean isGroupOwner(String pGroupId);

    /**
     * 当前用户判断是否是群主
     * @param pGroup EMGroup
     * @return 是返回true 否返回false
     */
    boolean isGroupOwner(EMGroup pGroup);

    /**
     * 解散群
     * @param pGroupId GroupId
     * @param pDestroyGroupListener Destroy Group Listener
     */
    void destroyGroup(String pGroupId, IDestroyGroupListener pDestroyGroupListener);

    /**
     * 退出群
     * @param pGroupId GroupId
     * @param pILeaveGroupListener Leave Group Listener
     */
    void leaveGroup(String pGroupId, ILeaveGroupListener pILeaveGroupListener);


    /**
     * 获取指定 GroupId 所有信息
     * @param pGroupId GroupId
     * @param callback EMValueCallBack
     */
    void getGroupDetail(String pGroupId, EMValueCallBack<EMGroup> callback);

    /**
     * 获取群所有成员
     * @param pGroup Group
     * @return 群所有成员
     */
    List<GroupMember> getGroupAllMembers(EMGroup pGroup);

    /**
     * 获取群所有管理员
     * @param pGroup Group
     * @return 群所有管理员
     */
    List<GroupMember> getGroupAllAdmins(EMGroup pGroup);

    /**
     * 获取群主
     * @param pGroup Group
     * @return 群主
     */
    GroupMember getOwner(EMGroup pGroup);

    /**
     * String List 转 GroupMember List
     * @param pUserList String List
     * @param pIdentity 身份
     * @return GroupMember List
     */
    List<GroupMember> stringList2GroupMember(List<String> pUserList, int pIdentity);

    /**
     * 判断当前用户是否是群主或管理员
     * @param pGroup Group
     * @return 是返回true 否返回false
     */
    boolean isOwnerOrAdmin(EMGroup pGroup);

    /**
     * 判断当前用户是否是管理员
     * @param pGroup Group
     * @return 是返回true 否返回false
     */
    boolean isGroupAdmin(EMGroup pGroup);

    /**
     * 获取所有不在群里的联系人
     * @param pGroup Group
     * @return 所有不在群里的联系人
     */
    LinkedList<User> getAllNotInGroupContact(EMGroup pGroup);
}
