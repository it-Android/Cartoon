package com.jq.cartoon1.adapter.javaBean;

/**
 * 预览图片的信息
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/24 0:14
 **/
public class CollectImgData {

    private String cartoonId="";//漫画id
    private String imgPath="";//封面图片
    private String name="暂无数据";//漫画名称
    private String path="";//漫画路径
    private Boolean isRead=false;//是否阅读了
    private int allChapter=0;//漫画全部章节数
    private int progress=0;//漫画阅读章节进度

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Boolean getRead() {
        return this.isRead;
    }

    public void setRead(Boolean read) {
        this.isRead = read;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getAllChapter() {
        return allChapter;
    }

    public void setAllChapter(int allChapter) {
        this.allChapter = allChapter;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
