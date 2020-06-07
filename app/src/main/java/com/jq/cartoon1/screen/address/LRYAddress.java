package com.jq.cartoon1.screen.address;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/1 22:47
 **/
public class LRYAddress {
    public final static Tag[][] xb_path = new Tag[][]{
            {new Tag("最新", "new"), new Tag("排行榜", "hot"), new Tag("国漫", "guochan"), new Tag("日韩", "rihan")},
            {new Tag("港台", "gangtai"), new Tag("欧美", "omei"), new Tag("完结", "wanjie"), new Tag("连载中", "lianzai")},
    };
    public final static Tag lx_path[][] = new Tag[][]{

    };

    public final static Tag[][] dq_path = new Tag[][]{
    };

    public final static Tag[][] jq_path = new Tag[][]{
            {new Tag("少年热血","shaonianrexue"),new Tag("武侠格斗","wuxiagedou"),new Tag("恐怖灵异","kongbulingyi"),new Tag("耽美人生","danmeirensheng")},
            {new Tag("少女爱情","shaonvaiqing"),new Tag("恋爱生活","lianaishenghuo"),new Tag("生活漫画","shenghuomanhua"),new Tag("科幻魔幻","kehuanmohuan")},
            {new Tag("竞技体育","jingjitiyu"),new Tag("爆笑喜剧","baoxiaoxiju"),new Tag("侦探推理","zhentantuili")}
    };

    public static String getdownLoadPath(int[] downLoadNumber) {
        if (downLoadNumber[0] == 0) {
            int row=downLoadNumber[1]/6;//行
            int count=downLoadNumber[1]%6;//列
            return "http://api.pingcc.cn/?mhlb=" + xb_path[row][count].getPath();
        } else {
            int row=downLoadNumber[4]/6;//行
            int count=downLoadNumber[4]%6;//列
            return "http://api.pingcc.cn/?mhlb=" + jq_path[row][count].getPath();
        }
    }

    private static String getData(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "1";
    }

}
