package com.jq.cartoon1.screen;

import com.jq.cartoon1.screen.interpreting.lry.LRYPictureInterpreting;
import com.jq.cartoon1.screen.interpreting.mhk.MhkPictureInterpreting;
import com.jq.cartoon1.screen.interpreting.toho.TohoPictureInterpreting;
import com.jq.cartoon1.screen.javaBean.PictureEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 21:42
 **/
public class PictureInterpreting {
    public static PictureEntity getPicture(String html,String  path, String website) {
        PictureEntity pictureEntity=null;
        switch (website){
            case Website.MHK:
                pictureEntity= MhkPictureInterpreting.getPicture(html);
                break;
            case Website.TOHOMH123:
                pictureEntity= TohoPictureInterpreting.getPicture(html);//土豪漫画下载图片
                break;
            case Website.LRY:
                pictureEntity= LRYPictureInterpreting.getPicture(html,path);//土豪漫画下载图片
                break;
        }
        return pictureEntity;
    }
}
