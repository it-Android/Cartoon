package com.jq.cartoon1.screen.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;

import java.util.List;
import java.util.Vector;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/19 23:34
 **/
public class DetailedTable {
    private String TABLE_NAME = "detailedTable";//表名
    private DBHelper db;

    public DetailedTable(Context context, String website) {
        this.TABLE_NAME += "_" + website;
        db = new DBHelper(context);
        if (!db.isTableExits(TABLE_NAME)) {
            String sql = "create table if not exists "
                    + TABLE_NAME + "( id integer primary key autoincrement,"
                    + "漫画id varchar unique,"
                    + "作者 varchar,"
                    + "分类 varchar,"
                    + "漫画状态 varchar,"
                    + "更新 varchar,"
                    + "地区 varchar,"
                    + "阅读进度 integer,"
                    + "时间 long,"
                    + "介绍 text,"
                    + "来源网站 varchar,"
                    + "收藏 varchar)";
            db.creatTable(sql);
        }
    }

    public boolean addData(BaseDetailed detailed) {
        ContentValues values = new ContentValues();
        //values.put("id", detailed.getId());
        values.put("漫画id", detailed.getCartoonId());
        values.put("作者", detailed.getAuthor());
        values.put("分类", detailed.getClassify());
        values.put("漫画状态", detailed.getState());
        values.put("地区", detailed.getRegion());
        values.put("阅读进度", detailed.getProgress());
        values.put("时间", detailed.getTime());
        values.put("介绍", detailed.getIntroduce());
        values.put("来源网站", detailed.getWebsite());
        values.put("收藏", String.valueOf(detailed.getCollection()));
        values.put("更新", String.valueOf(detailed.getUpData()));
        return db.save(TABLE_NAME, values);
    }


    public List<BaseDetailed> getAllDetailed() {
        Cursor cursor = null;
        List<BaseDetailed> list = new Vector<>();
        try {
            cursor = db.find("select *from " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                BaseDetailed detailed = new BaseDetailed();
                detailed.setId(cursor.getInt(cursor.getColumnIndex("id")));
                detailed.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
                detailed.setAuthor(cursor.getString(cursor.getColumnIndex("作者")));
                detailed.setClassify(cursor.getString(cursor.getColumnIndex("分类")));
                detailed.setState(cursor.getString(cursor.getColumnIndex("漫画状态")));
                detailed.setTime(cursor.getLong(cursor.getColumnIndex("时间")));
                detailed.setRegion(cursor.getString(cursor.getColumnIndex("地区")));
                detailed.setProgress(cursor.getInt(cursor.getColumnIndex("阅读进度")));
                detailed.setIntroduce(cursor.getString(cursor.getColumnIndex("介绍")));
                detailed.setWebsite(cursor.getString(cursor.getColumnIndex("来源网站")));
                detailed.setCollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("收藏"))));
                detailed.setUpData(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("更新"))));
                list.add(detailed);
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

    public List<BaseDetailed> getCollection() {
        Cursor cursor = null;
        List<BaseDetailed> list = new Vector<>();
        try {
            cursor = db.find("select *from " + TABLE_NAME + " where 收藏=?", new String[]{"true"});
            while (cursor.moveToNext()) {
                BaseDetailed detailed = new BaseDetailed();
                detailed.setId(cursor.getInt(cursor.getColumnIndex("id")));
                detailed.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
                detailed.setAuthor(cursor.getString(cursor.getColumnIndex("作者")));
                detailed.setClassify(cursor.getString(cursor.getColumnIndex("分类")));
                detailed.setState(cursor.getString(cursor.getColumnIndex("漫画状态")));
                detailed.setRegion(cursor.getString(cursor.getColumnIndex("地区")));
                detailed.setProgress(cursor.getInt(cursor.getColumnIndex("阅读进度")));
                detailed.setTime(cursor.getLong(cursor.getColumnIndex("时间")));
                detailed.setIntroduce(cursor.getString(cursor.getColumnIndex("介绍")));
                detailed.setWebsite(cursor.getString(cursor.getColumnIndex("来源网站")));
                detailed.setCollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("收藏"))));
                detailed.setUpData(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("更新"))));
                list.add(detailed);
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


    /**
     * 获取要删除的时间段的ID
     *
     * @param time
     * @return
     */
    public List<Integer> getDeleteId(String time) {
        Cursor cursor = null;
        List<Integer> list = new Vector<>();
        try {
            cursor = db.find("select id from " + TABLE_NAME + " where 时间 like '" + time + "%'", null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(id);
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

    /**
     * 获取要删除的时间段的ID
     *
     * @param time
     * @return
     */
    public List<String> getDeleteCartoonId(Long time) {
        Cursor cursor = null;
        List<String> list = new Vector<>();
        try {
            cursor = db.find("select 漫画id from " + TABLE_NAME + " where 时间 < " + time+" and 收藏 = \'false\'", null);
            while (cursor.moveToNext()) {
                String cartoonId = cursor.getString(cursor.getColumnIndex("漫画id"));
                list.add(cartoonId);
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


    public BaseDetailed getById(int id) {
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + TABLE_NAME + " where id=?", new String[]{String.valueOf(id)});
            cursor.moveToNext();
            BaseDetailed detailed = new BaseDetailed();
            detailed.setId(cursor.getInt(cursor.getColumnIndex("id")));
            detailed.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
            detailed.setAuthor(cursor.getString(cursor.getColumnIndex("作者")));
            detailed.setClassify(cursor.getString(cursor.getColumnIndex("分类")));
            detailed.setState(cursor.getString(cursor.getColumnIndex("漫画状态")));
            detailed.setRegion(cursor.getString(cursor.getColumnIndex("地区")));
            detailed.setProgress(cursor.getInt(cursor.getColumnIndex("阅读进度")));
            detailed.setTime(cursor.getLong(cursor.getColumnIndex("时间")));
            detailed.setIntroduce(cursor.getString(cursor.getColumnIndex("介绍")));
            detailed.setWebsite(cursor.getString(cursor.getColumnIndex("来源网站")));
            detailed.setCollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("收藏"))));
            detailed.setUpData(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("更新"))));
            return detailed;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }

    public BaseDetailed getByCartoonId(String cartoonId) {
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + TABLE_NAME + " where 漫画id=?", new String[]{String.valueOf(cartoonId)});
            cursor.moveToNext();
            BaseDetailed detailed = new BaseDetailed();
            detailed.setId(cursor.getInt(cursor.getColumnIndex("id")));
            detailed.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
            detailed.setAuthor(cursor.getString(cursor.getColumnIndex("作者")));
            detailed.setClassify(cursor.getString(cursor.getColumnIndex("分类")));
            detailed.setState(cursor.getString(cursor.getColumnIndex("漫画状态")));
            detailed.setRegion(cursor.getString(cursor.getColumnIndex("地区")));
            detailed.setProgress(cursor.getInt(cursor.getColumnIndex("阅读进度")));
            detailed.setTime(cursor.getLong(cursor.getColumnIndex("时间")));
            detailed.setIntroduce(cursor.getString(cursor.getColumnIndex("介绍")));
            detailed.setWebsite(cursor.getString(cursor.getColumnIndex("来源网站")));
            detailed.setCollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("收藏"))));
            detailed.setUpData(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("更新"))));
            return detailed;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }

    public boolean upData(BaseDetailed detailed) {
        ContentValues values = new ContentValues();
        String cartoonId = detailed.getCartoonId();
        if (cartoonId==null||cartoonId.equals("")) return false;
        //values.put("id", checkId);
        values.put("漫画id", detailed.getCartoonId());
        values.put("作者", detailed.getAuthor());
        values.put("分类", detailed.getClassify());
        values.put("漫画状态", detailed.getState());
        values.put("地区", detailed.getRegion());
        values.put("阅读进度", detailed.getProgress());
        values.put("时间", detailed.getTime());
        values.put("介绍", detailed.getIntroduce());
        values.put("来源网站", detailed.getWebsite());
        values.put("收藏", String.valueOf(detailed.getCollection()));
        values.put("更新", String.valueOf(detailed.getUpData()));
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataCollection(String cartoonId, Boolean collection) {
        String value = String.valueOf(collection);
        ContentValues values = new ContentValues();
        values.put("收藏", value);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataProgress(String cartoonId, int progress) {
        ContentValues values = new ContentValues();
        values.put("阅读进度", progress);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataUpData(String  cartoonId, Boolean upData) {
        String value = String.valueOf(upData);
        ContentValues values = new ContentValues();
        values.put("更新", value);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataState(String cartoonId, String state, long time) {
        ContentValues values = new ContentValues();
        values.put("漫画状态", state);
        values.put("时间", time);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataState(String cartoonId, String state) {
        ContentValues values = new ContentValues();
        values.put("漫画状态", state);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean upDataTime(String cartoonId, long time) {
        ContentValues values = new ContentValues();
        values.put("时间", time);
        return db.updata(TABLE_NAME, values, "漫画id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean delete(int id) {
        return db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }
    public boolean delete(String cartoonId) {
        return db.delete(TABLE_NAME, "漫画Id=?", new String[]{String.valueOf(cartoonId)});
    }

    public boolean deleteTime(long time) {
        return db.delete(TABLE_NAME, "时间 < " + time+" and 收藏 = \'false\'", null);
    }
}
