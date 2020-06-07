package com.jq.cartoon1.adapter.javaBean;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/29 10:37
 **/
public class ReadImgData {
    private String chapterId="";//章节id
    private int chapterNumber=0;//第几张图片
    private int chapterAll=0;//章节图片总数
    private int number=0;//章节
    private String chapterImg="";//章节图片
    private int width=0;
    private int height=0;
    private int isOk=0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getChapterAll() {
        return chapterAll;
    }

    public void setChapterAll(int chapterAll) {
        this.chapterAll = chapterAll;
    }

    public String getChapterImg() {
        return chapterImg;
    }

    public void setChapterImg(String chapterImg) {
        this.chapterImg = chapterImg;
    }
}
