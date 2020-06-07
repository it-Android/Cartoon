package com.jq.cartoon1.screen.javaBean;

import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;

import java.util.ArrayList;
import java.util.List;

public class DetailedEntity extends BaseDetailed {
	private String name="";//名称
	private String path="";
	private String imgPath="";
	private String lastTime="";//漫画最后更新时间
	private String lastTimeChapter="";//漫画最后更新的章节
	private List<ChapterEntity> allChapter=new ArrayList<ChapterEntity>();//全部的章节

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getLastTimeChapter() {
		return lastTimeChapter;
	}

	public void setLastTimeChapter(String lastTimeChapter) {
		this.lastTimeChapter = lastTimeChapter;
	}

	public List<ChapterEntity> getAllChapter() {
		return allChapter;
	}
	public void setAllChapter(List<ChapterEntity> allChapter) {
		this.allChapter = allChapter;
	}
	
	
	
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("\n来源网站："+getWebsite());
		builder.append("\nid："+getId());
		builder.append("\n名称："+getName());
		builder.append("\n下载链接："+getPath());
		builder.append("\n封面图片地址："+getImgPath());
		builder.append("\n地区："+getRegion());
		builder.append("\n分类："+getClassify());
		builder.append("\n作者："+getAuthor());
		builder.append("\n状态："+getState());
		builder.append("\n最近阅读时间："+getTime());
		builder.append("\n最后更新时间："+getLastTime());
		builder.append("\n最后更新的章节："+getLastTimeChapter());
		builder.append("\n介绍："+getIntroduce());
		builder.append("\n章节：\n"+allChapter.toString());
		return builder.toString();
	}
	
	
}
