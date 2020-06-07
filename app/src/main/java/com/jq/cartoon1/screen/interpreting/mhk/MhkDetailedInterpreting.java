package com.jq.cartoon1.screen.interpreting.mhk;


import com.jq.cartoon1.screen.javaBean.ChapterEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author JQ
 */
public class MhkDetailedInterpreting {
    public static DetailedEntity getDetaile(String html) {
        if(html==null) {
            return null;
        }
        DetailedEntity mDetailedEntity=new DetailedEntity();
        mDetailedEntity.setWebsite(Website.MHK);
        Document doc=Jsoup.parse(html);
        String[] allPath=doc.select("meta[name=mobile-agent]").attr("content").split("/");
        String sId=allPath[allPath.length-1].split("\\.html")[0];
        if(sId==null||sId.equals(""))return null;
        String path="https://www.mhk8.com/"+allPath[allPath.length-2]+"/"+allPath[allPath.length-1];//获取连接
        mDetailedEntity.setCartoonId(sId);
        mDetailedEntity.setPath(path);
        mDetailedEntity.setIntroduce(doc.select("div[class=mt10]").get(0).text());
        Elements elements=doc.select("div[class=maininfo box03 l mb10]");
        String name=elements.select("li").select("h2").text();//获取名称
        String imgPath=elements.select("div[class=bpic l]").select("img").attr("src");//获取封面图片
        mDetailedEntity.setName(name);
        mDetailedEntity.setImgPath(imgPath);
        for(int i=0;i<elements.select("ul").select("li").select("p").size();i++) {
            Element element=elements.select("ul").select("li").select("p").get(i);
            if(i==0) {
                mDetailedEntity.setLastTimeChapter(element.text());
            }else if(i==1) {
                mDetailedEntity.setClassify(element.text());
            }else if(i==2) {
                mDetailedEntity.setAuthor(element.text());
            }else if(i==3) {
                mDetailedEntity.setRegion(element.text());
            }else if(i==4) {
                mDetailedEntity.setLastTime(element.text());
            }
        }


        elements=doc.select("div[id=tabxinfan_1_tab_1]");
        mDetailedEntity.setState(elements.select("h2").text());
        elements=elements.select("li");
        for(int i=0;i<elements.size();i++) {
            Element element=elements.get(i);
            ChapterEntity chapterEntity=new ChapterEntity();
            chapterEntity.setChapterName(element.select("a[rel=nofollow]").text());//章节名称
            chapterEntity.setChapterPath("https://www.mhk8.com"+element.select("a[rel=nofollow]").attr("href"));
            chapterEntity.setChapterCheck(false);
            chapterEntity.setId(i);
            String[] allCheckPath=chapterEntity.getChapterPath().split("/");
            String checkId=allCheckPath[allCheckPath.length-1].split(".html")[0];
            chapterEntity.setChapterId(checkId);
            mDetailedEntity.getAllChapter().add(chapterEntity);
        }


        //System.out.println(elements);
        //System.out.println(mDetailedEntity.toString());
        return mDetailedEntity;
    }
}