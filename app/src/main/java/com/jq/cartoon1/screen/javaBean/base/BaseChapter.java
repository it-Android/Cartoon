package com.jq.cartoon1.screen.javaBean.base;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/20 16:25
 **/
public class BaseChapter {
    private int id = 0;//章节id
    private String chapterId = "";
    private String chapterName = "";//章节名称
    private String chapterPath = "";//章节下载链接
    private Boolean chapterCheck = false;//是否阅读了
    private int progress = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterPath() {
        return chapterPath;
    }

    public void setChapterPath(String chapterPath) {
        this.chapterPath = chapterPath;
    }

    public Boolean getChapterCheck() {
        return chapterCheck;
    }

    public void setChapterCheck(Boolean chapterCheck) {
        this.chapterCheck = chapterCheck;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n章节ID：");
        buffer.append(getChapterId());
        buffer.append("\n下标ID：");
        buffer.append(getId());
        buffer.append("\n");
        buffer.append("章节名称：");
        buffer.append(getChapterName());
        buffer.append("\n");
        buffer.append("阅读进度：");
        buffer.append(getProgress());
        buffer.append("\n");
        buffer.append("章节链接：");
        buffer.append(getChapterPath());
        buffer.append("\n");
        buffer.append("是否阅读：");
        buffer.append(getChapterCheck());
        buffer.append("\n");
        return buffer.toString();
    }
}
