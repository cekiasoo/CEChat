package com.ce.cechat.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ce.cechat.bean.User;
import com.ce.cechat.data.db.UserDbHelper;

import java.util.List;

/**
 * @author CE Chen
 */
public class UserDao extends AbstractDao{

    private static final String TAG = "UserDao";

    private Context mContext;

    private SQLiteDatabase mSqLiteDatabase;

    public UserDao(Context pContext) {
        this.mContext = pContext;
    }

    @Override
    protected String getTableName() {
        return UserTable.TABLE_NAME;
    }

    /**
     * 替换 User
     * @param pUser user
     * @return 成功返回true 失败返回false
     */
    public boolean replaceUser(User pUser) {
        ContentValues values = createUserPrams(pUser);
        int rowId = (int) getSQLiteDatabase().replace(UserTable.TABLE_NAME, null, values);
        Log.v(TAG, "rowId = " + rowId);
        return rowId > 0;
    }

    public User getUserById(String hyphenateId) {
        String sql = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE 1=1 AND " + UserTable.HYPHENATE_ID + " = ?";
        Cursor cursor = getSQLiteDatabase().rawQuery(sql, new String[] {hyphenateId});
        Log.v(TAG, "getUserById Count = " + cursor.getCount());
        User user = null;
        while(cursor.moveToNext()) {
            user = getBean(cursor);
        }
        cursor.close();
        return user;
    }

    /**
     * 插入 User
     * @param pUser ModelUser
     * @return 成功返回true 失败返回false
     */
    public boolean insertUser(User pUser) {
        ContentValues contentValues = createUserPrams(pUser);
        long rowID = getSQLiteDatabase().insert(getTableName(), null, contentValues);
        return rowID > 0;
    }

    /**
     * 删除 User
     * @param pCondition condition
     * @return 成功返回true 失败返回false
     */
    public boolean deleteUser(String pCondition, String[] pConditionArgs) {
        return delete(getTableName(), pCondition, pConditionArgs);
    }

    /**
     * 修改 User
     * @param pUser User
     * @param pWhereClause Where clause
     * @param pWhereArgs Where args
     * @return 成功返回true 失败返回false
     */
    public boolean updateUser(User pUser, String pWhereClause, String[] pWhereArgs) {
        ContentValues contentValues = createUserPrams(pUser);
        return getSQLiteDatabase().update(getTableName(), contentValues, pWhereClause, pWhereArgs) > 0;
    }

    public boolean updateUser(ContentValues pContentValues, String pWhereClause, String[] pWhereArgs) {
        return getSQLiteDatabase().update(getTableName(), pContentValues, pWhereClause, pWhereArgs) > 0;
    }

    /**
     * 获取 User List
     * @param pCondition condition "AND ...?...?..."
     * @param pConditionArgs condition args
     * @return User list
     */
    public List<User> getUserList(String pCondition, String[] pConditionArgs) {
        String pSql = "SELECT * FROM " + getTableName() + " WHERE 1=1 " + pCondition;
        return getList(pSql, pConditionArgs);
    }

    @Override
    protected User getBean(Cursor pCursor) {
        User user = new User();
        user.setHyphenateId(pCursor.getString(pCursor.getColumnIndex(UserTable.HYPHENATE_ID)));
        user.setName(pCursor.getString(pCursor.getColumnIndex(UserTable.NAME)));
        user.setNickname(pCursor.getString(pCursor.getColumnIndex(UserTable.NICKNAME)));
        user.setHead(pCursor.getString(pCursor.getColumnIndex(UserTable.HEAD)));
        return user;
    }

    /**
     * 创建 ContentValues
     * @param pUser User
     * @return ContentValues
     */
    protected ContentValues createUserPrams(User pUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.HYPHENATE_ID, pUser.getHyphenateId());
        contentValues.put(UserTable.NAME, pUser.getName());
        contentValues.put(UserTable.NICKNAME, pUser.getNickname());
        contentValues.put(UserTable.HEAD, pUser.getHead());
        return contentValues;
    }

    /**
     * Get SQLiteDatabase instance
     * @return SQLiteDatabase instance
     */
    @Override
    protected SQLiteDatabase getSQLiteDatabase() {
        if (mSqLiteDatabase == null) {
            UserDbHelper databaseHelper = UserDbHelper.getInstance(mContext);
            mSqLiteDatabase = databaseHelper.getWritableDatabase();
        }
        return mSqLiteDatabase;
    }

}
