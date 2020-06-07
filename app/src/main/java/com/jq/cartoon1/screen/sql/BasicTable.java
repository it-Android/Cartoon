package com.jq.cartoon1.screen.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jq.cartoon1.screen.javaBean.BasicEntity;

import java.util.List;
import java.util.Vector;

/**
 * 基本数据表
 *
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/19 22:00
 **/
public class BasicTable {
    private String TABLE_NAME = "basicTable";
    private DBHelper db;

    public BasicTable(Context context,String website) {
    this.TABLE_NAME+="_"+website;
    db = new DBHelper(context);
        if (!db.isTableExits(TABLE_NAME)) {
        String sql = "create table if not exists "
                + TABLE_NAME + " ( id integer primary key autoincrement,"
                + "漫画id varchar unique,"
                + "名称 varchar,"
                + "连接 varchar,"
                + "封面图片 varchar,"
                + "章节名称 varchar,"
                + "更新时间 varchar)";
        db.creatTable(sql);
    }
}

    public boolean addData(BasicEntity basicEntity) {
        ContentValues values = new ContentValues();
        //values.put("id", basicEntity.getId());
        values.put("漫画id", basicEntity.getCartoonId());
        values.put("名称", basicEntity.getName());
        values.put("连接", basicEntity.getPath());
        values.put("封面图片", basicEntity.getImgPath());
        values.put("章节名称", basicEntity.getNewest());
        values.put("更新时间", basicEntity.getNewTime());
        return db.save(TABLE_NAME, values);
    }

    public List<BasicEntity> getBasic() {
        List<BasicEntity> list = new Vector<>();
        Cursor cursor = null;
        try {
            cursor = db.find("select *from " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                BasicEntity basicEntity = new BasicEntity();
                basicEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                basicEntity.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
                basicEntity.setName(cursor.getString(cursor.getColumnIndex("名称")));
                basicEntity.setPath(cursor.getString(cursor.getColumnIndex("连接")));
                basicEntity.setImgPath(cursor.getString(cursor.getColumnIndex("封面图片")));
                basicEntity.setNewest(cursor.getString(cursor.getColumnIndex("章节名称")));
                basicEntity.setNewTime(cursor.getString(cursor.getColumnIndex("更新时间")));
                list.add(basicEntity);
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

    public synchronized BasicEntity getBasicByID(String cartoonId){
        Cursor cursor = null;
        try {
            cursor=db.find("select *from "+TABLE_NAME+" where 漫画id =?",new String[]{cartoonId});
           if(!cursor.moveToNext())return null;
            BasicEntity basicEntity=new BasicEntity();
            basicEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
            basicEntity.setCartoonId(cursor.getString(cursor.getColumnIndex("漫画id")));
            basicEntity.setName(cursor.getString(cursor.getColumnIndex("名称")));
            basicEntity.setPath(cursor.getString(cursor.getColumnIndex("连接")));
            basicEntity.setImgPath(cursor.getString(cursor.getColumnIndex("封面图片")));
            basicEntity.setNewest(cursor.getString(cursor.getColumnIndex("章节名称")));
            basicEntity.setNewTime(cursor.getString(cursor.getColumnIndex("更新时间")));
            return basicEntity;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.closeConnection();
        }
    }

    public boolean upData(BasicEntity basicEntity){
        ContentValues values = new ContentValues();
        String cartoonId=basicEntity.getCartoonId();
        if(cartoonId==null||cartoonId.equals(""))return false;
        //values.put("id", checkId);
        values.put("漫画id", basicEntity.getCartoonId());
        values.put("名称", basicEntity.getName());
        values.put("连接", basicEntity.getPath());
        values.put("封面图片", basicEntity.getImgPath());
        values.put("章节名称", basicEntity.getNewest());
        values.put("更新时间", basicEntity.getNewTime());
        return db.updata(TABLE_NAME,
                values,
                "漫画id=?",
                new String[]{cartoonId});

    }

    public boolean upDataNew(String cartoonId,String  newest,String newtime){
        ContentValues values=new ContentValues();
        values.put("章节名称",newest);
        values.put("更新时间",newtime);
        return db.updata(TABLE_NAME,values,"漫画id=?",new String[]{cartoonId});
    }
    public boolean delete(String cartoonId){
        return db.delete(TABLE_NAME,"漫画id=?",new String[]{cartoonId});
    }

    public boolean delete(BasicEntity basicEntity){
        return delete(basicEntity.getCartoonId());
    }
}
