package com.jq.cartoon1.screen.javaBean.base;
/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/20 0:42
 **/
public class BaseBasic {
    private int id=0;//id
    private String cartoonId="";//漫画id
    private String name="";//漫画名称
    private String path="";//漫画连接
    private String imgPath="";//封面图片
    private String newest="";//漫画最新章节
    private String newTime="";//漫画最新更新时间

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCartoonId() {
        return cartoonId;
    }
    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getNewest() {
        return newest;
    }
    public void setNewest(String newest) {
        this.newest = newest;
    }
    public String getNewTime() {
        return newTime;
    }
    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }
    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("\nid:").append(id);
        buffer.append("\n漫画id:").append(cartoonId);
        buffer.append("\n名称:").append(name);
        buffer.append("\n连接:").append(path);
        buffer.append("\n封面连接:").append(imgPath);
        buffer.append("\n最新章节:").append(newest);
        buffer.append("\n更新时间:").append(newTime).append("\n");
        return buffer.toString();
    }
}
