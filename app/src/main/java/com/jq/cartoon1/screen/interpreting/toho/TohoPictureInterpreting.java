package com.jq.cartoon1.screen.interpreting.toho;

import com.jq.cartoon1.screen.javaBean.PictureEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TohoPictureInterpreting {
	
	public static PictureEntity getPicture(String html) {
		if(html==null||html.equals("")) {
			return null;
		}
		PictureEntity pictureEntity=new PictureEntity();
		Document doc=Jsoup.parse(html);
		Elements elements=doc.select("script[type=text/javascript]");
		Element element=null;
		for(Element elem:elements) {
			if(elem.toString().indexOf("imgDomain")!=-1) {
				element=elem;
				break;
			}
		}
		if(element==null)return null;
		
		String[] allData= element.toString().split(";");
		for(String data:allData) {
			if(data.indexOf("sid")!=-1) {
				String sid=data.split("=")[1];
				pictureEntity.setChapterId(sid);//章节id
				continue;
			}
			if(data.indexOf("pcount")!=-1) {
				String pcount=data.split("=")[1];
				pictureEntity.setTotal(getInteger(pcount));//全部章节
				continue;
			}
			if(data.indexOf("pl")!=-1) {
				String pl=data.split("=")[1].replace("\'", "");//去掉\
				String[] allPath=pl.split("/");//分分割
				String pathHead=pl.split(allPath[allPath.length-1])[0];//获取图片链接头
				String suffix=allPath[allPath.length-1].split("\\.")[1];//获取图片后缀
				String starNumber=allPath[allPath.length-1].split("\\.")[0];//获取图片开始地址
				for(int i=getInteger(starNumber);i<pictureEntity.getTotal();i++) {
					pictureEntity.getListImg().add(pathHead+String.format("%04d."+suffix, i));//拼接图片地址，并给数字补齐4位数
				}
				continue;
			}
		}
		elements=doc.select("div[class=titleBar]");
		//String cartoonId=elements.select("span").select("a").attr("href").replace("/", "");
		//pictureEntity.setChapterId(cartoonId);//漫画id
		for(int i=0;i<elements.select("div[id=title]").size();i++) {
			if(i==0) {
				pictureEntity.setName(elements.select("div[id=title]").get(i).text());
				continue;
			}
			if(i==1) {
				pictureEntity.setChapter(elements.select("div[id=title]").get(i).text());
				continue;
			}
		}
		return pictureEntity;
	}
	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
}
