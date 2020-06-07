package com.jq.cartoon1.screen.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/18 11:05
 **/
public class DBHelper extends SQLiteOpenHelper {
    public final static String DB_NAME = "cartoon.db";
    private final static int DB_VERSION = 1;//版本
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (db == null) {
            db = getWritableDatabase();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    //打开
    public SQLiteDatabase openConnection() {
        if (!db.isOpen()) {//当前是否打开
            db = getWritableDatabase();//获取对象的方式来打开
        }
        return db;
    }

    //关闭
    public void closeConnection() {
        try {
            if (db != null && db.isOpen()) {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean creatTable(String createTableSql) {
        try {
            openConnection();
            db.execSQL(createTableSql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }


    public boolean save(String tableName, ContentValues values) {
        try {
            openConnection();
            long insert = db.insert(tableName, null, values);
            if(insert==-1){
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updata(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        try {
            openConnection();
            db.update(tableName, values, whereClause, whereArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }


    public boolean delete(String tableName, String whereClause, String[] whereArgs) {
        try {
            openConnection();
            db.delete(tableName, whereClause, whereArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }


    public boolean deleteTable(String tableName){
        try{
            openConnection();
            String sql="drop table "+tableName;
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return false;
    }




    public Cursor find(String findSql, String[] obj) {
        try {
            openConnection();
            Cursor cursor = db.rawQuery(findSql, obj);
            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTableExits(String tableName) {
        try {
            openConnection();
            String sql = "select count(*) xcount  from "
                    + tableName;
            db.rawQuery(sql, null);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

}
