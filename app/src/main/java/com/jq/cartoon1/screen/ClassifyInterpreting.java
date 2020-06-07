package com.jq.cartoon1.screen;

import com.jq.cartoon1.screen.interpreting.lry.LRYClassifyInterpreting;
import com.jq.cartoon1.screen.interpreting.mhk.MhkClassifyInterpreting;
import com.jq.cartoon1.screen.interpreting.toho.TohoClassifyInterpreting;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * 分类解析器分拣
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 21:19
 **/
public class ClassifyInterpreting {

    public static SearchAllEntity getClassify(String html,String  path,String website){
        SearchAllEntity searchAllEntity=null;
        switch (website){
            case Website.MHK:
                searchAllEntity= MhkClassifyInterpreting.getSearchAll(html);
                break;
            case Website.TOHOMH123:
                searchAllEntity= TohoClassifyInterpreting.getClassify(html,1);//土豪漫画分类信息解析器
                break;
            case Website.LRY:
                searchAllEntity= LRYClassifyInterpreting.getClassify(html,path);//LRY
                break;
        }
        return searchAllEntity;
    }

}
