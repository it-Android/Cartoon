package com.jq.cartoon1.screen.interpreting.toho;

import com.jq.cartoon1.screen.javaBean.ChapterEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TohoDetailedInterpreting {
	
	
	public static DetailedEntity getDetailed(String html) {
		if(html==null||html.equals(""))return null;
		DetailedEntity detailedEntity=new DetailedEntity();
		Document doc=Jsoup.parse(html);
		detailedEntity.setWebsite(Website.TOHOMH123);//网站
		String path=doc.select("link[rel=canonical]").attr("href").replace("/m.", "/mip.");
		detailedEntity.setPath(path);//连接
		String[] allId=path.split("/");
		detailedEntity.setCartoonId(allId[allId.length-1]);
		Elements elements=doc.select("div[class=detailInfo d-border]");
		String imgPath=elements.select("mip-img[layout=container]").attr("src");
		String name=elements.select("mip-img[layout=container]").attr("title");
		detailedEntity.setImgPath(imgPath);	//封面
		detailedEntity.setName(name);//名称
		String tip=elements.select("div[class=info d-item-content]").select("p[class=remind]").text();
		detailedEntity.setTip(tip);//提示
		String classify=elements.select("div[class=info d-item-content]")
				.select("p[class=subtitle d-nowrap]").select("a").text();
		detailedEntity.setClassify(classify);//分类
		
		for(Element element:elements.select("div[class=info d-item-content]").select("p[class=subtitle d-nowrap]")) {
			if(element.text().indexOf("作者")!=-1) {
				detailedEntity.setAuthor(element.text().replace("作者：", ""));//作者
				continue;
			}
			if(element.text().indexOf("更新时间")!=-1) {
				detailedEntity.setLastTime(element.text().replace("更新时间：", ""));//漫画更新时间
				break;
			}
			
		}
		//最新章节名称
		detailedEntity.setLastTimeChapter(elements.select("div[class=info d-item-content]").select("p[class=bottom d-nowrap]").text());
		String introduce=doc.select("div[class=detailContent]").select("div[class=ccontent]").text();		
		detailedEntity.setIntroduce(introduce);
		elements=doc.select("ul[class=am-avg-sm-4 am-thumbnails list]").select("li");
		int id=elements.size();
		for(Element element:elements) {
			id--;
			ChapterEntity chapterEntity=new ChapterEntity();
			String chapterPath=element.select("a[class=d-nowrap]").attr("href");
			String chapterName=element.select("a[class=d-nowrap]").text();
			String[] allChapterId=chapterPath.split("/");
			String chapterId=allChapterId[allChapterId.length-1].split(".html")[0];			
			chapterEntity.setChapterPath(chapterPath);//章节路径
			chapterEntity.setChapterName(chapterName);//章节名称
			chapterEntity.setId(id);
			chapterEntity.setChapterId(chapterId);
			detailedEntity.getAllChapter().add(chapterEntity);//
		}
		
		Collections.reverse(detailedEntity.getAllChapter());//倒叙
		
		//System.out.println(elements);
		return detailedEntity;
	}
	
	
	
	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
}
