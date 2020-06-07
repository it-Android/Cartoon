package com.jq.cartoon1.screen;

import com.jq.cartoon1.screen.interpreting.lry.LRYSearchInterpreting;
import com.jq.cartoon1.screen.interpreting.mhk.MhkSearchInterpreting;
import com.jq.cartoon1.screen.interpreting.toho.TohoSearchInterpreting;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 21:08
 **/
public class SearchInterpreting {
    public static SearchAllEntity getSearchAll(String html, String website) {
        SearchAllEntity searchAllEntity = null;
        switch (website) {
            case Website.MHK:
                searchAllEntity = MhkSearchInterpreting.getSearchAll(html);//漫画狂网站搜索解析
                break;
            case Website.TOHOMH123:
                searchAllEntity= TohoSearchInterpreting.getSearch(html);//土豪漫画搜索
                break;
            case Website.LRY:
                searchAllEntity= LRYSearchInterpreting.getSearch(html);//LRY
                break;
        }
        return searchAllEntity;
    }
}
