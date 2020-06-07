package com.jq.cartoon1.screen.interpreting.mhk;


import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画狂网址搜索解析
 * @author JQ
 *
 */
public class MhkSearchInterpreting {
	
	public static List<BasicEntity> getBasic(String html){
		if(html==null) {
			return null;
		}
		List<BasicEntity> list=new ArrayList<BasicEntity>();
		Document doc=Jsoup.parse(html);
		Elements elements=doc.select("ul[id=list_img]").select("li");
		for(int i=0;i<elements.size();i++) {
			Element element=elements.get(i);
			BasicEntity searchEntity=new BasicEntity();
			String path=element.select("a").attr("href");
			String[] allId=path.split("/");
			String sId=allId[allId.length-1].split("\\.")[0];
			String imgPath=element.select("img").attr("data-src");
			String newest=element.select("span").text();
			String name=element.select("p").text();
			String newTime=element.select("em").text();
			searchEntity.setCartoonId(sId);
			searchEntity.setPath("https://www.mhk8.com"+path);
			searchEntity.setImgPath(imgPath);
			searchEntity.setName(name);
			searchEntity.setNewest(newest);
			searchEntity.setNewTime(newTime);
			list.add(searchEntity);
			//System.out.println(searchEntity.toString());
		}
		
		//System.out.println(elements.toString());
		return list;
	}
	
	public static SearchAllEntity getSearchAll(String html){
		if(html==null) {
			return null;
		}
		List<BasicEntity> list=new ArrayList<BasicEntity>();
		SearchAllEntity allEntity=new SearchAllEntity();
		Document doc=Jsoup.parse(html);
		Elements eles=doc.select("div[class=paixu_box]").select("strong");
		allEntity.setSearchName(eles.get(0).text());
		allEntity.setAll(Integer.valueOf(eles.get(1).text()));
		
		eles=doc.select("em[class=num]");
		String[] number=eles.get(0).text().split("/");
		eles=doc.select("a[target=_self]");
		Element ele=null;
		for(int i=0;i<eles.size();i++) {
			ele=eles.get(i);
			if(ele.text().equals("下一页")) {
				break;
			}
		}
		if(!number[0].equals(number[1])) {
			allEntity.setNext("https://www.mhk8.com"+ele.attr("href"));
		}
		
		allEntity.setNowNumber(Integer.valueOf(number[0]));
		allEntity.setLastNumber(Integer.valueOf(number[1]));
		Elements elements=doc.select("ul[id=list_img]").select("li");
		for(int i=0;i<elements.size();i++) {
			Element element=elements.get(i);
			BasicEntity searchEntity=new BasicEntity();
			String path=element.select("a").attr("href");
			String[] allId=path.split("/");
			String sId=allId[allId.length-1].split("\\.")[0];
			String imgPath=element.select("img").attr("data-src");
			String newest=element.select("span").text();
			String name=element.select("p").text();
			String newTime=element.select("em").text();
			searchEntity.setCartoonId(sId);
			searchEntity.setPath("https://www.mhk8.com"+path);
			searchEntity.setImgPath(imgPath);
			searchEntity.setName(name);
			searchEntity.setNewest(newest);
			searchEntity.setNewTime(newTime);
			list.add(searchEntity);
			//System.out.println(searchEntity.toString());
		}
		allEntity.setListSearch(list);
		//System.out.println(allEntity.toString());
		return allEntity;
	}
	
}
