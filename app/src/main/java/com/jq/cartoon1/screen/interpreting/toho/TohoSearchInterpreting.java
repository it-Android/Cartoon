package com.jq.cartoon1.screen.interpreting.toho;

import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TohoSearchInterpreting {
	public static SearchAllEntity getSearch(String html) {
		if(html==null||html.equals(""))return null;
		SearchAllEntity searchAllEntity=new SearchAllEntity();
		Document doc=Jsoup.parse(html);
		String[] seachName=doc.select("title").text().split(" ");
		for(int i=0;i<seachName.length-2;i++) {
			searchAllEntity.setSearchName(searchAllEntity.getSearchName()+seachName[i]);//添加搜索名
		}
		String searchNumber=doc.select("div[class=searchtip]").select("span[class=red]").text();
		searchAllEntity.setAll(getInteger(searchNumber));//搜索到的全部结果
		searchAllEntity.setLastNumber((int)Math.ceil(searchAllEntity.getAll()/24.0));//向上取模，页数保存
		Elements elements=doc.select("div[class=page-pagination mt20]");
		String nowPath=elements.select("li").select("a[class=active]").attr("href");
		if(nowPath!=null&&!nowPath.equals("")) {
			int nowNumber=getInteger(nowPath.split("page=")[1]);
			searchAllEntity.setNowNumber(nowNumber);
			if(nowNumber<searchAllEntity.getLastNumber()) {
				searchAllEntity.setNext("https://mip.tohomh123.com"+nowPath.split("page=")[0]+"page="+(nowNumber+1));
			}else {
				searchAllEntity.setNext("https://mip.tohomh123.com"+nowPath);
			}
		}
		elements=doc.select("div[id=classList_1]").select("li[class=am-thumbnail]");
		for (Element element : elements) {
			BasicEntity basicEntity=new BasicEntity();
			String carToonId=element.select("a[data-type=mip]").attr("href");//;
			basicEntity.setPath("https://mip.tohomh123.com"+carToonId);
			String carToonName=element.select("a[data-type=mip]").attr("title");
			basicEntity.setCartoonId(carToonId.replace("/", ""));//漫画id
			basicEntity.setName(carToonName);//漫画名称
			String imgPath=element.select("a[data-type=mip]").select("mip-img").attr("src");
			basicEntity.setImgPath(imgPath);
			String newChapter=element.select("span[class=tip]").text();
			basicEntity.setNewest(newChapter);
			searchAllEntity.getListSearch().add(basicEntity);
		}
		
		return searchAllEntity;
	}
	
	
	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
}
