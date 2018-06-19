package com.ce.cechat.model.bean;

/**
 * @author CE Chen
 *
 * 群组
 */
public class Group {

    private String groupName;

    private String groupId;

    private String inviteUser;

    public Group() {
    }

    public Group(String groupName, String groupId, String inviteUser) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.inviteUser = inviteUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", inviteUser='" + inviteUser + '\'' +
                '}';
    }
}
