package com.ce.cechat.bean;

/**
 * @author CE Chen
 * <p>
 * 作用 : 群成员
 */
public class GroupMember extends User{

    /**
     * 身份 0 群主 1 管理员 2 群员
     * 默认为群员
     */
    private int identity = 1;

    /**
     * 群主
     */
    public static final int OWNER = 0;

    /**
     * 管理员
     */
    public static final int ADMIN = 1;

    /**
     * 群员
     */
    public static final int MEMBER = 2;

    public GroupMember(String name) {
        super(name);
    }

    public GroupMember(String pHyphenateId, int identity) {
        super(pHyphenateId);
        this.identity = identity;
    }

    public GroupMember() {

    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}
