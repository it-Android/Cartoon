package com.jq.cartoon1.screen.interpreting.lry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.jq.cartoon1.screen.javaBean.ChapterEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;
public class LRYDetailedInterpreting {
	
	public static DetailedEntity getDetailed(String json, String path) {
		if(json==null||json.equals(""))return null;
		DetailedEntity detailed=new DetailedEntity();
		Gson gson=new Gson();
		AllLryData allLryData=null;
		try {
			allLryData=gson.fromJson(json, AllLryData.class);//全部数据
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		if(allLryData==null||allLryData.code!=0)return null;
		AllLryChapter allChapter=allLryData.getData();//漫画的全部信息
		String[] allId=path.split("mhurl1=");
		if(allId==null||allId.length<=1)return null;
		if(allChapter==null)return null;
		detailed.setCartoonId(allId[1].replace(":", "").replace(".", "").replace("/", ""));//漫画id
		detailed.setAuthor(allChapter.getAuthor());//作者
		detailed.setClassify(allChapter.getTag());//分类
		detailed.setImgPath(allChapter.getCover());//封面
		detailed.setIntroduce(allChapter.getIntroduce());//介绍
		detailed.setLastTime(allChapter.getTime());//更新时间
		detailed.setLastTimeChapter(allChapter.getLatest());//最新章节
		detailed.setName(allChapter.getName());//漫画名称
		detailed.setPath(path);//漫画链接
		detailed.setState(allChapter.getStatus());//状态
		detailed.setWebsite(Website.LRY);//来源网站
		for(int i=0;i<allLryData.getList().size();i++) {
			LryChapter chapter=allLryData.getList().get(i);
			ChapterEntity chapterEntity=new ChapterEntity();
			chapterEntity.setChapterCheck(false);

			String[] allChapterId=chapter.getUrl().split("/");
			String chapterId=allChapterId[allChapterId.length-1].split(".html")[0];	
			
			chapterEntity.setChapterId(chapterId);//章节id
			chapterEntity.setChapterName(chapter.getNum());//章节名称
			chapterEntity.setChapterPath("http://api.pingcc.cn/?mhurl2="+chapter.getUrl());//章节连接
			chapterEntity.setId(i);
			detailed.getAllChapter().add(chapterEntity);
		}
		
		return detailed;
	}
	
	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
	class LryChapter{
		private String num="";///章节名称
		private String url="";//链接
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("\n章节名称：").append(num);
			buffer.append("章节链接：").append(url);
			return buffer.toString();
		}
		
	}
	
	
	class AllLryChapter{
		private String name="";//漫画名称
		private String status="";//漫画状态
		private String introduce="";//简介
		private String cover="";//封面图片
		private String author="";//作者
		private String tag="";//分类
		private String time="";//更新时间
		private String latest="";//
		
		
		public String getLatest() {
			return latest;
		}
		public void setLatest(String latest) {
			this.latest = latest;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getIntroduce() {
			return introduce;
		}
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
		public String getCover() {
			return cover;
		}
		public void setCover(String cover) {
			this.cover = cover;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("漫画名称：").append(name);
			buffer.append("，漫画作者：").append(author);
			buffer.append("，漫画状态：").append(status);
			buffer.append("，漫画分类：").append(tag);
			buffer.append("，最新章节：").append(latest);
			buffer.append("，漫画简介：").append(introduce);
			buffer.append("，漫画封面：").append(cover);
			buffer.append("，更新时间：").append(time);
			buffer.append("，更新章节信息：");
			return buffer.toString();
		}
		
	}
	
	class AllLryData{
		private int code=-1;
		private String message ="";
		private AllLryChapter data=new AllLryChapter();
		private List<LryChapter> list=new ArrayList<LryChapter>();//漫画章节信息
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public AllLryChapter getData() {
			return data;
		}
		public void setData(AllLryChapter data) {
			this.data = data;
		}
		
		public List<LryChapter> getList() {
			return list;
		}
		public void setList(List<LryChapter> list) {
			this.list = list;
		}
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("返回码：").append(code);
			buffer.append("，消息：").append(message);
			buffer.append(",数据：");
			if(data!=null)
			buffer.append(data);
			if(list!=null)
			buffer.append(list.toString());
			return buffer.toString();
		}
		
	}
}
