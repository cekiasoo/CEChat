package com.ce.cechat.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.db.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 *
 * 联系人数据库操作类
 */
public class ContactDao extends AbstractDao{

    private DbHelper mContactDb;

    public ContactDao(DbHelper pContactDb) {
        this.mContactDb = pContactDb;
    }

    /**
     * 获取所有的联系人
     */
    public List<User> getContacts() {

        String sql = "SELECT * FROM " + ContactTable.TABLE_NAME + " WHERE " + ContactTable.IS_CONTACT + "=1;";

        //查询
        return getList(sql, null);
    }

    /**
     * 获取指定 HyphenateId 的联系人
     * @param pHyphenateId HyphenateId
     * @return 指定 HyphenateId 的联系人
     */
    public User getContactByHyphenateId(String pHyphenateId) {

        //hyphenateId 是空的
        if (TextUtils.isEmpty(pHyphenateId)) {
            return null;
        }

        String sql = "SELECT * FROM " + ContactTable.TABLE_NAME
                + " WHERE " + ContactTable.HYPHENATE_ID + " = ?;";

        //查询
        Cursor cursor = getDb().rawQuery(sql, new String[]{pHyphenateId});

        User user = null;
        while (cursor.moveToNext()) {
            user = getBean(cursor);
        }

        //关闭
        cursor.close();
        return user;
    }

    /**
     * 获取一组指定 hyphenateId 的联系人
     * @param pHyphenateIds 一组指定 hyphenateId
     * @return 一组指定 hyphenateId 的联系人
     */
    public List<User> getContactByHyphenateId(List<String> pHyphenateIds) {

        //hyphenateId 组是空的
        if (pHyphenateIds == null || pHyphenateIds.isEmpty()) {
            return null;
        }

        List<User> users = new LinkedList<>();

        for (String hyphenateId:
             pHyphenateIds) {
            User user = getContactByHyphenateId(hyphenateId);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 保存联系人
     * @param pUser User
     * @param isMyContact 是否是当前用户的联系人
     * @return 成功返回true 失败返回false
     */
    public boolean saveContact(User pUser, boolean isMyContact) {

        //User 是空的
        if (pUser == null) {
            return false;
        }

        ContentValues values = createContactPrams(pUser);
        if (!isMyContact) {
            values.put(ContactTable.IS_CONTACT, 0);
        }
        long row = getDb().replace(ContactTable.TABLE_NAME, null, values);
        return row > 0;
    }

    /**
     * 保存一组联系人
     * @param pUsers 一组联系人
     * @param isMyContact 是否是当前用户的联系人
     */
    public void saveContact(List<User> pUsers, boolean isMyContact) {
        //User 组是空的
        if (pUsers == null || pUsers.isEmpty()) {
            return;
        }

        for (User user : pUsers) {
            saveContact(user, isMyContact);
        }
    }

    /**
     * 删除指定 HyphenateId 的联系人
     * @param pHyphenateId 指定 HyphenateId
     * @return 成功返回 true 失败返回false
     */
    public boolean deleteContactByHyphenateId(String pHyphenateId) {

        //HyphenateId 是空的
        if (TextUtils.isEmpty(pHyphenateId)) {
            return false;
        }
        String condition = "AND " + ContactTable.HYPHENATE_ID + " = ?";
        return delete(ContactTable.TABLE_NAME, condition, new String[]{pHyphenateId});
    }

    private SQLiteDatabase getDb() {
        return mContactDb.getWritableDatabase();
    }

    @Override
    protected SQLiteDatabase getSQLiteDatabase() {
        return getDb();
    }

    @Override
    protected User getBean(Cursor pCursor) {
        String hyphenateId = pCursor.getString(pCursor.getColumnIndex(ContactTable.HYPHENATE_ID));
        String name = pCursor.getString(pCursor.getColumnIndex(ContactTable.NAME));
        String nickname = pCursor.getString(pCursor.getColumnIndex(ContactTable.NICKNAME));
        String head = pCursor.getString(pCursor.getColumnIndex(ContactTable.HEAD));
        return new User(hyphenateId, name, nickname, head);
    }


    /**
     * 创建 ContentValues
     * @param pUser User
     * @return ContentValues
     */
    protected ContentValues createContactPrams(User pUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.HYPHENATE_ID, pUser.getHyphenateId());
        contentValues.put(ContactTable.NAME, pUser.getName());
        contentValues.put(ContactTable.NICKNAME, pUser.getNickname());
        contentValues.put(ContactTable.HEAD, pUser.getHead());
        return contentValues;
    }

    @Override
    protected String getTableName() {
        return ContactTable.TABLE_NAME;
    }
}
