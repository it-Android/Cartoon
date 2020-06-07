package com.jq.cartoon1.screen.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jq.cartoon1.screen.javaBean.base.BaseChapter;

import java.util.List;
import java.util.Vector;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/20 16:27
 **/
public class ChapterTable {
    private String tableName = "";
    private DBHelper db;
    public ChapterTable(Context context,String website, String tableName) {
        this.tableName = "table_"+website+"_"+tableName;
        db = new DBHelper(context);
        if (!db.isTableExits(this.tableName)) {
            String sql = "create table if not exists "
                    + this.tableName + " (Id integer primary key ,"
                    + "章节Id varchar,"
                    + "章节名称 varchar,"
                    + "章节链接 varchar,"
                    + "章节是否阅读 varchar,"
                    + "章节进度 integer)";
            db.creatTable(sql);
        }
    }

    public boolean addData(BaseChapter chapter) {
        ContentValues values = new ContentValues();
        values.put("Id", chapter.getId());
        values.put("章节Id", chapter.getChapterId());
        values.put("章节名称", chapter.getChapterName());
        values.put("章节链接", chapter.getChapterPath());
        values.put("章节是否阅读", chapter.getChapterCheck());
        values.put("章节进度", chapter.getProgress());
        return db.save(tableName, values);
    }

    public boolean addData(List<BaseChapter> list) {
        if(list==null||list.size()==0)return true;
        SQLiteDatabase database = db.openConnection();
        try {
            String sql = "insert into " + tableName + "(Id,章节Id,章节名称,章节链接,章节是否阅读,章节进度)"
                    + "values(?,?,?,?,?,?)";
            SQLiteStatement stat = database.compileStatement(sql);
            database.beginTransaction();//第一行
            for (BaseChapter baseChapter : list) {
                stat.bindLong(1, baseChapter.getId());
                stat.bindString(2, baseChapter.getChapterId());
                stat.bindString(3, baseChapter.getChapterName());
                stat.bindString(4, baseChapter.getChapterPath());
                if (baseChapter.getChapterCheck()) {
                    stat.bindString(5, "true");
                } else {
                    stat.bindString(5, "false");
                }
                stat.bindLong(6, baseChapter.getProgress());
                long result = stat.executeInsert();
            }
            database.setTransactionSuccessful();//加上的代码 第二行

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) {
                database.endTransaction();//加上的代码 第三行
            }
            db.closeConnection();//关闭数据库
        }
        return true;

    }

    public List<BaseChapter> getChapter() {
        List<BaseChapter> list = new Vector<>();
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + tableName, null);
            while (cursor.moveToNext()) {
                BaseChapter baseChapter = new BaseChapter();
                baseChapter.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                baseChapter.setChapterId(cursor.getString(cursor.getColumnIndex("章节Id")));
                baseChapter.setChapterName(cursor.getString(cursor.getColumnIndex("章节名称")));
                baseChapter.setChapterCheck(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("章节是否阅读"))));
                baseChapter.setChapterPath(cursor.getString(cursor.getColumnIndex("章节链接")));
                baseChapter.setProgress(cursor.getInt(cursor.getColumnIndex("章节进度")));
                list.add(baseChapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }

        return list;
    }

    public BaseChapter getById(int id) {
        BaseChapter baseChapter = new BaseChapter();
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + tableName + " where Id=?", new String[]{String.valueOf(id)});
            cursor.moveToNext();
            baseChapter.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            baseChapter.setChapterId(cursor.getString(cursor.getColumnIndex("章节Id")));
            baseChapter.setChapterName(cursor.getString(cursor.getColumnIndex("章节名称")));
            baseChapter.setChapterCheck(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("章节是否阅读"))));
            baseChapter.setChapterPath(cursor.getString(cursor.getColumnIndex("章节链接")));
            baseChapter.setProgress(cursor.getInt(cursor.getColumnIndex("章节进度")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return baseChapter;
    }

    public BaseChapter getByChapterId(String chapterId) {
        BaseChapter baseChapter = new BaseChapter();
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + tableName + " where 章节Id=?", new String[]{String.valueOf(chapterId)});
            cursor.moveToNext();
            baseChapter.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            baseChapter.setChapterId(cursor.getString(cursor.getColumnIndex("章节Id")));
            baseChapter.setChapterName(cursor.getString(cursor.getColumnIndex("章节名称")));
            baseChapter.setChapterCheck(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("章节是否阅读"))));
            baseChapter.setChapterPath(cursor.getString(cursor.getColumnIndex("章节链接")));
            baseChapter.setProgress(cursor.getInt(cursor.getColumnIndex("章节进度")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return baseChapter;
    }

    public boolean upDataReadById(int id, boolean read) {
        ContentValues values = new ContentValues();
        if (read) {
            values.put("章节是否阅读", "true");
        } else {
            values.put("章节是否阅读", "false");
        }
        return db.updata(tableName, values, "Id=?", new String[]{String.valueOf(id)});
    }
    public boolean upDataReadByChapterId(String chapterId, boolean read) {
        ContentValues values = new ContentValues();
        if (read) {
            values.put("章节是否阅读", "true");
        } else {
            values.put("章节是否阅读", "false");
        }
        return db.updata(tableName, values, "章节Id=?", new String[]{String.valueOf(chapterId)});
    }

    public boolean upDataProgressById(int id, int progress) {
        ContentValues values = new ContentValues();
        values.put("章节进度", progress);
        return db.updata(tableName, values, "Id=?", new String[]{String.valueOf(id)});
    }
    public boolean upDataProgressByChapterId(String chapterId, int progress) {
        ContentValues values = new ContentValues();
        values.put("章节进度", progress);
        return db.updata(tableName, values, "章节Id=?", new String[]{String.valueOf(chapterId)});
    }


    public boolean upDataById(int id, boolean read, int progress) {
        ContentValues values = new ContentValues();
        if (read) {
            values.put("章节是否阅读", "true");
        } else {
            values.put("章节是否阅读", "false");
        }
        values.put("章节进度", progress);
        return db.updata(tableName, values, "Id=?", new String[]{String.valueOf(id)});
    }
    public boolean upDataByChapterId(String chapterId, boolean read, int progress) {
        ContentValues values = new ContentValues();
        if (read) {
            values.put("章节是否阅读", "true");
        } else {
            values.put("章节是否阅读", "false");
        }
        values.put("章节进度", progress);
        return db.updata(tableName, values, "章节Id=?", new String[]{String.valueOf(chapterId)});
    }

    public boolean deleteTable(){
        return db.deleteTable(tableName);
    }

}
