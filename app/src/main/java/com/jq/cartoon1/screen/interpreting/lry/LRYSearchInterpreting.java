package com.jq.cartoon1.screen.interpreting.lry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;


public class LRYSearchInterpreting {

	public static SearchAllEntity getSearch(String json) {
		if(json==null||json.equals(""))return null;
		SearchAllEntity searchAllEntity=new SearchAllEntity();
		Gson gson=new Gson();
		LryAllSearch lryAllSearch=null;
		try {
			lryAllSearch=gson.fromJson(json, LryAllSearch.class);
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		if(lryAllSearch==null||lryAllSearch.getCode()!=0)return null;//返回码不等于零就是失败了
		searchAllEntity.setAll(lryAllSearch.getList().size());//共找到的结果
		searchAllEntity.setLastNumber(1);//最后的页数
		searchAllEntity.setNowNumber(1);//当前页数
		searchAllEntity.setNext("");//下一章链接
		searchAllEntity.setSearchName("");//搜索名称

		for(int i=0;i<lryAllSearch.getList().size();i++) {
			LrySearch search=lryAllSearch.list.get(i);
			BasicEntity basicEntity=new BasicEntity();
			basicEntity.setCartoonId(search.getUrl().replace(":", "").replace(".", "").replace("/", ""));//漫画id
			basicEntity.setImgPath(search.getCover());//漫画封面图片
			basicEntity.setName(search.getName());//漫画名称
			basicEntity.setNewest(search.getLatest());//
			basicEntity.setNewTime(search.getTime());//更新时间
			basicEntity.setPath("http://api.pingcc.cn/?mhurl1="+search.getUrl());
			searchAllEntity.getListSearch().add(basicEntity);//添加漫画
		}
		return searchAllEntity;
	}
	
	
	class LrySearch{
		private String name="";
		private String url="";
		private String cover="";
		private String latest="";//最新章节
		private String tag="";//分类标签
		private String time="";//最近更新时间
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getCover() {
			return cover;
		}
		public void setCover(String cover) {
			this.cover = cover;
		}
		
		public String getLatest() {
			return latest;
		}
		public void setLatest(String latest) {
			this.latest = latest;
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
			buffer.append("名称：").append(name);
			buffer.append("，标签：").append(tag);
			buffer.append("，最新章节：").append(latest);
			buffer.append("，更新时间：").append(time);
			buffer.append("，链接：").append(url);
			buffer.append("，封面：").append(cover);
			return buffer.toString();
		}
	}
	
	class LryAllSearch{
		private int code=-1;//返回码 
		private String message="";//是否成功
		private List<LrySearch> list=new ArrayList<LrySearch>();
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
		public List<LrySearch> getList() {
			return list;
		}
		public void setList(List<LrySearch> list) {
			this.list = list;
		}
		
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("返回码：").append(code);
			buffer.append("是否成功：").append(message);
			buffer.append("结果：").append(list.toString());
			return buffer.toString();
		}
		
		
	}
}
