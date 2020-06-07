package com.jq.cartoon1.screen.interpreting.toho;

import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TohoClassifyInterpreting {
	public static SearchAllEntity getClassify(String html,int type) {

		if(type<0)type=0;
		if(type>1)type=1;
		if(html==null||html.equals(""))return null;
		SearchAllEntity searchAllEntity=new SearchAllEntity();
		Document doc=Jsoup.parse(html);
		String nowPath=doc.select("link[rel=canonical]").attr("href");
		String[] allNumber=nowPath.split("-");
		searchAllEntity.setNowNumber(getInteger(allNumber[allNumber.length-1].replace(".html", "")));
		Elements els=doc.select("ul[class=am-avg-sm-3 am-thumbnails list]");
		if(els.size()<=1)type=0;
		Element element=els.get(type);
		for(Element el:element.select("li[class=am-thumbnail]")) {
			BasicEntity basicEntity=new BasicEntity();
			String cartoonId=el.select("a[data-type=mip]").attr("href");
			String path="https://mip.tohomh123.com"+cartoonId;
			basicEntity.setPath(path);
			basicEntity.setCartoonId(cartoonId.replace("/", ""));
			String imgPath=el.select("mip-img[layout=fill]").attr("src");
			basicEntity.setImgPath(imgPath);
			String name=el.select("mip-img[layout=fill]").attr("alt");
			basicEntity.setName(name);
			String newChapter=el.select("span[class=tip]").text();
			basicEntity.setNewest(newChapter);
			searchAllEntity.getListSearch().add(basicEntity);
			//System.out.println(el);
		}
		Elements elements=doc.select("div[class=page-pagination mt20]").select("li").select("a[data-type=mip]");
		searchAllEntity.setNext("https://mip.tohomh123.com"+elements.get(elements.size()-1).attr("href"));
		return  searchAllEntity;
	}

	private static int getInteger(String number){
		try {
			return Integer.valueOf(number);
		}catch (Exception e) {
			return 0;
		}
	}
}
