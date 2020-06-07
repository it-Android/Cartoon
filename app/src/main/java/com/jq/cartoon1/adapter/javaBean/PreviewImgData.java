package com.jq.cartoon1.adapter.javaBean;

import android.graphics.Bitmap;

/**
 * 预览图片的信息
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/24 0:14
 **/
public class PreviewImgData {

    private String cartoonId="";
    private String imgPath="";
    private String name="";
    private String chapterName="";
    private String time="";
    private String path="";

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
