package com.jq.cartoon1.screen;

import com.jq.cartoon1.screen.interpreting.lry.LRYDetailedInterpreting;
import com.jq.cartoon1.screen.interpreting.mhk.MhkDetailedInterpreting;
import com.jq.cartoon1.screen.interpreting.toho.TohoDetailedInterpreting;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * 详细信息分拣器
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 21:23
 **/
public class DetailedInterpreting {
    public static DetailedEntity getDetaile(String html,String  path, String website) {
        DetailedEntity detailedEntity=null;
        switch (website){
            case Website.MHK:
                detailedEntity= MhkDetailedInterpreting.getDetaile(html);
                break;
            case Website.TOHOMH123:
                detailedEntity= TohoDetailedInterpreting.getDetailed(html);//土豪漫画详细信息下载
                break;
            case Website.LRY:
                detailedEntity= LRYDetailedInterpreting.getDetailed(html,path);//土豪漫画详细信息下载
                break;
        }
        return detailedEntity;
    }
}
