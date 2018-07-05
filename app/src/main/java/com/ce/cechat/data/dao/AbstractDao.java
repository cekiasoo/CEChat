package com.ce.cechat.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CE Chen
 * <p>
 * 作用 :
 */
public abstract class AbstractDao {

    protected abstract SQLiteDatabase getSQLiteDatabase();


    /**
     * 开始 transaction
     */
    public void beginTransaction() {
        getSQLiteDatabase().beginTransaction();
    }

    /**
     * 设置 transaction successful
     */
    public void setTransactionSuccessful() {
        getSQLiteDatabase().setTransactionSuccessful();
    }

    /**
     * 结束 transaction
     */
    public void endTransaction() {
        getSQLiteDatabase().endTransaction();
    }

    /**
     * 获取数据库表有多少条数据
     * @param pCondition condition
     * @return count
     */
    public int getCount(String pCondition) {
        String tableName = getTableName();
        return getCount(tableName, pCondition);
    }

    /**
     * Get count
     * @param pTableName 表名
     * @param pCondition 查询条件
     * @return count
     */
    public int getCount(String pTableName, String pCondition) {
        Cursor cursor = execSQL("SELECT * FROM ? WHERE 1=1 ?", new String[] {pTableName, pCondition});
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * 删除
     * @param pTableName 表名
     * @param pCondition 查询条件 AND XXX
     * @return 成功返回true 失败返回false
     */
    public boolean delete(String pTableName, String pCondition, String[] pConditionArgs) {
        return getSQLiteDatabase().delete(pTableName, " 1=1 " + pCondition, pConditionArgs) >= 0;
    }

    /**
     * 获取 list
     * @param pSQL sql
     * @param pSelectionArgs selection args
     * @return List
     */
    public <T> List<T> getList(String pSQL, String[] pSelectionArgs) {
        Cursor cursor = execSQL(pSQL, pSelectionArgs);
        return cursor2List(cursor);
    }

    /**
     * Cursor 转 List
     * @param pCursor cursor
     * @param <T> type
     * @return List
     */
    private <T> List<T> cursor2List(Cursor pCursor) {
        List<T> list = new LinkedList<>();
        while (pCursor.moveToNext()) {
            T t = getBean(pCursor);
            list.add(t);
        }
        pCursor.close();
        return list;
    }

    /**
     * 获取需要操作的实体类对象
     * @param pCursor cursor
     * @param <T> type
     * @return 实体类对象
     */
    protected abstract <T> T getBean(Cursor pCursor);


    /**
     * 获取数据库表名
     * @return 表名
     */
    protected abstract String getTableName();

    /**
     * 执行 Sql 语句
     * @param pSQL sql
     * @param pSelectionArgs selection args
     * @return Cursor
     */
    private Cursor execSQL(String pSQL, String[] pSelectionArgs) {
        return getSQLiteDatabase().rawQuery(pSQL, pSelectionArgs);
    }

}
