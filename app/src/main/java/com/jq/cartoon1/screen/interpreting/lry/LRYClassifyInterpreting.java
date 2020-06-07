package com.jq.cartoon1.screen.interpreting.lry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;


public class LRYClassifyInterpreting {
	
	public static SearchAllEntity getClassify(String json, String path) {
		SearchAllEntity searchAllEntity=new SearchAllEntity();
		Gson gson=new Gson();
		LryAllClassify allClassify= gson.fromJson(json, LryAllClassify.class);
		LryResult lryResult=gson.fromJson(allClassify.getList().get(0).toString(), LryResult.class);
		allClassify.getList().remove(0);
		
		List<LryClassify> list=gson.fromJson(gson.toJson(allClassify.getList()), 
				new TypeToken<ArrayList<LryClassify>>() {
		}.getType());
		
		searchAllEntity.setAll(lryResult.getNumber());//找到的全部结果
		searchAllEntity.setLastNumber(lryResult.getDpages());//全部页数
		searchAllEntity.setNowNumber(lryResult.getPages());//当前页
		String[] allpath=path.split("mhlb=");
		if(allpath==null||allpath.length!=2)return null;
		String next=allpath[1].split("-")[0];
		searchAllEntity.setNext("http://api.pingcc.cn/?mhlb="+next+"-"+(searchAllEntity.getNowNumber()+1));
		for(int i=0;i<list.size();i++) {
			LryClassify lryClassify=list.get(i);
			BasicEntity basicEntity=new BasicEntity();
			basicEntity.setCartoonId(lryClassify.getUrl().replace(":", "").replace(".", "").replace("/", ""));
			basicEntity.setImgPath(lryClassify.getCover());//封面
			basicEntity.setName(lryClassify.getName());//名称
			basicEntity.setNewest(lryClassify.getLatest());//网站
			basicEntity.setNewTime(lryClassify.getTime());//更新时间
			basicEntity.setPath("http://api.pingcc.cn/?mhurl1="+lryClassify.getUrl());
			searchAllEntity.getListSearch().add(basicEntity);
		}
		return searchAllEntity;
	}

	class LryClassify{
		private String name="";
		private String url="";
		private String cover="";
		private String latest="";//最新章节
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
		
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("名称：").append(name);
			buffer.append("，最新章节：").append(latest);
			buffer.append("，更新时间：").append(time);
			buffer.append("，链接：").append(url);
			buffer.append("，封面：").append(cover);
			return buffer.toString();
		}
	}
	
	class LryResult{
		private int number=0;
		private int pages=0;
		private int dpages=0;
		

		public int getNumber() {
			return number;
		}


		public void setNumber(int number) {
			this.number = number;
		}


		public int getPages() {
			return pages;
		}


		public void setPages(int pages) {
			this.pages = pages;
		}


		public int getDpages() {
			return dpages;
		}


		public void setDpages(int dpages) {
			this.dpages = dpages;
		}


		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("共找到结果：").append(number);
			buffer.append("当前页：").append(pages);
			buffer.append("总页数：").append(dpages);
			return buffer.toString();
		}
	}
	
	class LryAllClassify{
		private int code=-1;//返回码 
		private String message="";//是否成功
		private List<Object> list=new ArrayList<>();
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
		
		public List<Object> getList() {
			return list;
		}
		public void setList(List<Object> list) {
			this.list = list;
		}
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("返回码：").append(code);
			buffer.append("是否成功：").append(message);
			buffer.append("结果：");
			if(list!=null)
			buffer.append(list.toString());
			return buffer.toString();
		}
		
	}
}
