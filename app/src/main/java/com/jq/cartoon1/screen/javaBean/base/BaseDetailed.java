package com.jq.cartoon1.screen.javaBean.base;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/20 0:46
 **/
public class BaseDetailed {
    private int id = 0;//id
    private String cartoonId="";//漫画id
    private String region = "";//漫画地区
    private String classify = "";//漫画分类
    private String author = "";//漫画作者
    private boolean collection = false;//收藏
    private boolean upData=false;//更新
    private String state = "";//漫画状态
    private int progress = 0;//进度
    private String introduce = "";//漫画介绍
    private long time = 0;//最近一次的阅读时间
    private String website = "";//来源网站
    private String tip = "";//提示

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public int getProgress() {
        return progress;
    }

    public boolean getUpData() {
        return upData;
    }

    public void setUpData(boolean upData) {
        this.upData = upData;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean getCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\nID:").append(id);
        buffer.append("\n漫画Id:").append(cartoonId);
        buffer.append("\n作者:").append(author);
        buffer.append("\n提示:").append(getTip());
        buffer.append("\nf分类:").append(classify);
        buffer.append("\n地区:").append(region);
        buffer.append("\n状态:").append(state);
        buffer.append("\n阅读进度:").append(progress);
        buffer.append("\n时间:").append(time);
        buffer.append("\n收藏:").append(collection);
        buffer.append("\n介绍:").append(introduce);
        buffer.append("\n来源:").append(website);
        return buffer.toString();
    }
}
