package com.jq.cartoon1.screen.interpreting.lry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.jq.cartoon1.screen.javaBean.PictureEntity;


public class LRYPictureInterpreting {
	public static PictureEntity getPicture(String json, String path) {
		if(json==null||json.equals(""))return null;
		PictureEntity picture=new PictureEntity();
		Gson gson=new Gson();
		AllImg allImg=null;
		try {
			allImg=gson.fromJson(json, AllImg.class);
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		if(allImg==null||allImg.code!=0)return null;
		String[] allId=path.split("mhurl2=");
		if(allId==null||allId.length<=1)return null;
		String[] allChapterId=allId[1].split("/");
		String chapterId=allChapterId[allChapterId.length-1];
		picture.setChapterId(chapterId.replace(".html", ""));//漫画id
		List<Img> list=allImg.getList();
		picture.setTotal(list.size());//页数
		for(int i=0;i<list.size();i++) {
			picture.getListImg().add(list.get(i).getImg());
		}
		return picture;
	}
	
	
	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
	class Img{
		private String img="";

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}
		public String toString() {
			return "图片："+img;
			
		}
	}
	
	
	class AllImg{
		private int code=-1;
		private String message ="";
		private List<Img> list=new ArrayList<Img>();
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
		public List<Img> getList() {
			return list;
		}
		public void setList(List<Img> list) {
			this.list = list;
		}
		
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("返回码：").append(code);
			buffer.append("，提示消息：").append(message);
			buffer.append("，漫画图片：");
			if(list!=null)
			buffer.append(list.toString());
			return buffer.toString();
			
		}
		
	}
	
}
