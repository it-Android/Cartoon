package com.jq.cartoon1.screen.javaBean;

import java.util.ArrayList;
import java.util.List;

public class PictureEntity {
	private int id=0;
	private String chapterId="";
	private String name="";
	private String chapter="";
	private int total=0;
	private List<String> listImg=new ArrayList<String>();;
	
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<String> getListImg() {
		return listImg;
	}
	public void setListImg(List<String> listImg) {
		this.listImg = listImg;
	}
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("ID："+getId()+"\n");
		builder.append("章节ID："+getChapterId()+"\n");
		builder.append("名称："+getName()+"\n");
		builder.append("章节名称："+getChapter()+"\n");
		builder.append("页数："+getTotal()+"\n");
		builder.append("图片连接："+listImg.toString());
		return builder.toString();
		
	}
}
