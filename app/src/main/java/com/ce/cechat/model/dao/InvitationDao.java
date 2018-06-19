package com.ce.cechat.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ce.cechat.model.bean.Group;
import com.ce.cechat.model.bean.Invitation;
import com.ce.cechat.model.bean.User;
import com.ce.cechat.model.db.DbHelper;

import java.util.List;

/**
 * @author CE Chen
 *
 * 邀请表的操作类
 */
public class InvitationDao extends AbstractDao{

    private DbHelper mDbHelper;

    public InvitationDao(DbHelper mDbHelper) {
        this.mDbHelper = mDbHelper;
    }

    /**
     * 添加邀请信息
     * @param pInvitation Invitation
     * @return 成功返回 true 失败返回 false
     */
    public boolean addInvitation(Invitation pInvitation) {
        ContentValues values = new ContentValues();
        values.put(InvitationTable.STATUS, pInvitation.getInvitationStatus());
        values.put(InvitationTable.REASON, pInvitation.getReason());
        if (pInvitation.getUser() != null) {
            values.put(InvitationTable.USER_HYPHENATE_ID, pInvitation.getUser().getHyphenateId());
            values.put(InvitationTable.USER_NAME, pInvitation.getUser().getName());
        } else {
            values.put(InvitationTable.GROUP_HYPHENATE_ID, pInvitation.getGroup().getGroupId());
            values.put(InvitationTable.GROUP_NAME, pInvitation.getGroup().getGroupName());
            values.put(InvitationTable.USER_HYPHENATE_ID, pInvitation.getGroup().getInviteUser());
        }
        long replace = getDb().replace(InvitationTable.TABLE_NAME, null, values);
        return replace > 0;
    }

    /**
     * 获取所有邀请信息
     * @return 所有邀请信息
     */
    public List<Invitation> getInvitations() {

        String sql = "SELECT * FROM " + InvitationTable.TABLE_NAME + " ORDER BY " + InvitationTable.ID + " DESC";

        return getList(sql, null);
    }

    /**
     * 删除指定 HyphenateId 的数据
     * @param pHyphenateId HyphenateId
     * @return 成功返回true 失败返回false
     */
    public boolean deleteInvitationByHyphenateId(String pHyphenateId) {

        if (TextUtils.isEmpty(pHyphenateId)) {
            return false;
        }

        String condition = InvitationTable.USER_HYPHENATE_ID + " = ?";
        int delete = getDb().delete(InvitationTable.TABLE_NAME, condition, new String[]{pHyphenateId});
        return delete > 0;
    }

    /**
     * 更新邀请状态
     * @param pStatus 邀请状态
     * @param pHyphenateId HyphenateId
     * @return 成功返回true 失败返回false
     */
    public boolean updateInvitationStatus(int pStatus, String pHyphenateId) {

        if (TextUtils.isEmpty(pHyphenateId)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(InvitationTable.STATUS, pStatus);
        String condition = InvitationTable.GROUP_HYPHENATE_ID + " = ?";
        int update = getDb().update(InvitationTable.TABLE_NAME, values, condition, new String[]{pHyphenateId});
        return update > 0;
    }

    private SQLiteDatabase getDb() {
        return mDbHelper.getWritableDatabase();
    }

    @Override
    protected SQLiteDatabase getSQLiteDatabase() {
        return getDb();
    }

    @Override
    protected Invitation getBean(Cursor pCursor) {
        Invitation invitation = new Invitation();
        int status = pCursor.getInt(pCursor.getColumnIndex(InvitationTable.STATUS));
        String reason = pCursor.getString(pCursor.getColumnIndex(InvitationTable.REASON));
        invitation.setInvitationStatus(status);
        invitation.setReason(reason);

        String groupId = pCursor.getString(pCursor.getColumnIndex(InvitationTable.GROUP_HYPHENATE_ID));
        if (groupId == null) {
            //是 User
            String userHyphenateId = pCursor.getString(pCursor.getColumnIndex(InvitationTable.USER_HYPHENATE_ID));
            String username = pCursor.getString(pCursor.getColumnIndex(InvitationTable.USER_NAME));
            User user = new User();
            user.setHyphenateId(userHyphenateId);
            user.setName(username);
            invitation.setUser(user);
        } else {
            //是 Group
            String groupName = pCursor.getString(pCursor.getColumnIndex(InvitationTable.GROUP_NAME));
            String inviteUser = pCursor.getString(pCursor.getColumnIndex(InvitationTable.USER_HYPHENATE_ID));
            Group group = new Group();
            group.setGroupId(groupId);
            group.setGroupName(groupName);
            group.setInviteUser(inviteUser);
            invitation.setGroup(group);
        }
        return invitation;
    }

    @Override
    protected String getTableName() {
        return InvitationTable.TABLE_NAME;
    }
}
