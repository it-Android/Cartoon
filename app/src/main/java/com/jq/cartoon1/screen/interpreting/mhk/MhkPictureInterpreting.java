package com.jq.cartoon1.screen.interpreting.mhk;


import com.google.gson.Gson;
import com.jq.cartoon1.screen.javaBean.PictureEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class MhkPictureInterpreting {
    public static PictureEntity getPicture(String html) {
        if (html == null) {
            return null;
        }
        PictureEntity pictureEntity = new PictureEntity();
        Document doc = Jsoup.parse(html);
        String name = doc.select("div[class=mh_readtitle tc]").select("strong").text();
        String[] allName = name.split("\\(");
        pictureEntity.setName(allName[0]);
        pictureEntity.setChapter(allName[1].substring(0, allName[1].length() - 1));

        String path = doc.select("meta[name=mobile-agent]").attr("content");
        String[] allId = path.split("/");
        int id = Integer.valueOf(allId[allId.length - 2]);
        pictureEntity.setId(id);
        pictureEntity.setChapterId(allId[allId.length - 1].split(".html")[0]);


        Elements elements = doc.select("script");
        Element element = null;
        for (int i = 0; i < elements.size(); i++) {
            element = elements.get(i);
            if (element.toString().indexOf("var z_img=") != -1) {
                break;
            }
        }
        String[] allImg = element.toString().replace("\'", "").replace("\\", "").split(";");
        String imgPathHead = allImg[0].split("=")[1];//获取连接头
        String imgPath = allImg[1].split("=")[1];
        Gson gson = new Gson();
        List<String> listImg = gson.fromJson(imgPath, ArrayList.class);
        for (int i = 0; i < listImg.size(); i++) {
            String strPath = listImg.get(i);
            if(strPath.indexOf("http")==-1){
                listImg.set(i, imgPathHead + strPath);
            }else{
                listImg.set(i,strPath);
            }
        }
        pictureEntity.setListImg(listImg);
        pictureEntity.setTotal(listImg.size());

        //System.out.println(pictureEntity);
        return pictureEntity;
    }
}
