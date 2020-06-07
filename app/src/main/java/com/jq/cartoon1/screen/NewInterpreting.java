package com.jq.cartoon1.screen;

import com.jq.cartoon1.screen.interpreting.lry.LRYClassifyInterpreting;
import com.jq.cartoon1.screen.interpreting.mhk.MhkNewInterpreting;
import com.jq.cartoon1.screen.interpreting.toho.TohoClassifyInterpreting;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 21:31
 **/
public class NewInterpreting {
    public static SearchAllEntity getNewSearchAll(String html,String  path, String website,int type) {
        SearchAllEntity searchAllEntity = null;
        switch (website) {
            case Website.MHK:
                    searchAllEntity= MhkNewInterpreting.getSearchAll(html);
                break;
            case Website.TOHOMH123:
                    searchAllEntity= TohoClassifyInterpreting.getClassify(html,type);//土豪漫画最新章节信息下载
                break;
            case Website.LRY:
                    searchAllEntity= LRYClassifyInterpreting.getClassify(html,path);//LRY
                break;
        }
        return searchAllEntity;
    }
}
