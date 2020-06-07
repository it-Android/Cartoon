package com.jq.cartoon1.screen.address;

import com.jq.cartoon1.screen.javaBean.base.Website;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/2 0:53
 **/
public class CarToonAddress {
    public static String getAddress(String website,int[] downLoadNumber){
        String path=null;
        switch (website){
            case Website.MHK:
                path =MhkAddress.getdownLoadPath(downLoadNumber);
                break;
            case Website.TOHOMH123:
                path =TohoAddress.getdownLoadPath(downLoadNumber);
                break;
            case Website.LRY:
                path =LRYAddress.getdownLoadPath(downLoadNumber);
                break;
        }
        return path;
    }

    public static Tag[][] getXb(String website){
        switch (website){
            case Website.MHK:
                return MhkAddress.xb_path;
            case Website.TOHOMH123:
                return TohoAddress.xb_path;
            case Website.LRY:
                return LRYAddress.xb_path;
        }
        return MhkAddress.xb_path;
    }
    public static Tag[][] getLx(String website){
        switch (website){
            case Website.MHK:
                return MhkAddress.lx_path;
            case Website.TOHOMH123:
                return TohoAddress.lx_path;
            case Website.LRY:
                return LRYAddress.lx_path;
        }
        return MhkAddress.lx_path;
    }
    public static Tag[][] getDq(String website){
        switch (website){
            case Website.MHK:
                return MhkAddress.dq_path;
            case Website.TOHOMH123:
                return TohoAddress.dq_path;
            case Website.LRY:
                return LRYAddress.dq_path;
        }
        return MhkAddress.dq_path;
    }


    public static Tag[][] getJq(String website){
        switch (website){
            case Website.MHK:
                return MhkAddress.jq_path;
            case Website.TOHOMH123:
                return TohoAddress.jq_path;
            case Website.LRY:
                return LRYAddress.jq_path;
        }
        return MhkAddress.jq_path;
    }
    public static String getSearchPath(String website,String name){
        switch (website){
            case Website.MHK:
                return "https://www.mhk8.com/index.php?m=vod-search-wd-"+getData(name)+".html";
            case Website.TOHOMH123:
                return "https://mip.tohomh123.com/action/Search?keyword="+getData(name);
            case Website.LRY:
                return "http://api.pingcc.cn/?mhname="+getData(name);
        }
        return "https://www.mhk8.com/index.php?m=vod-search-wd-"+getData(name)+".html";
    }

    private static String getData(String name){
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "1";
    }

}
