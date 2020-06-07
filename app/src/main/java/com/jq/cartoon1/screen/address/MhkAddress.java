package com.jq.cartoon1.screen.address;

import com.jq.cartoon1.screen.javaBean.base.Website;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/1 22:47
 **/
public class MhkAddress {
    public final static Tag[][] xb_path = new Tag[][]{
            {new Tag("最新", "https://www.mhk8.com/page/new/1.html"), new Tag("排行榜", "https://www.mhk8.com/page/hot/1.html"), new Tag("国产漫画", "https://www.mhk8.com/page/guochan/1.html"), new Tag("日韩漫画", "https://www.mhk8.com/page/rihan/1.html")},
            {new Tag("完结", "https://www.mhk8.com/page/wanjie/1.html"), new Tag("连载", "https://www.mhk8.com/page/lianzai/1.html")},
    };
    public final static Tag[][] lx_path = new Tag[][]{
            {new Tag("少年", "1"), new Tag("少女", "2"), new Tag("青年", "3"), new Tag("女性", "4")}
    };
    public final static Tag[][] dq_path = new Tag[][]{
            {new Tag("全部", ""), new Tag("大陆", "大陆漫画"), new Tag("日韩", "日韩漫画"), new Tag("港台", "港台漫画"), new Tag("欧美", "欧美漫画")}
    };

    public final static Tag[][] jq_path = new Tag[][]{
            {new Tag("全部", "0"), new Tag("热血", "1"), new Tag("恋爱", "9"), new Tag("校园", "41"), new Tag("百合", "19"), new Tag("彩虹", "261")},
            {new Tag("冒险", "37"), new Tag("后宫", "5"), new Tag("仙侠", "20"), new Tag("武侠", "197"), new Tag("悬疑", "10"), new Tag("推理", "14")},
            {new Tag("搞笑", "7"), new Tag("奇幻", "42"), new Tag("猎奇", "29"), new Tag("玄幻", "2"), new Tag("古风", "191"), new Tag("萌系", "40")},
            {new Tag("日常", "18"), new Tag("治愈", "11"), new Tag("烧脑", "14"), new Tag("穿越", "22"), new Tag("都市", "185"), new Tag("腹黑", "17")}

    };


    public static String getdownLoadPath(int[] downLoadNumber) {
        if (downLoadNumber[0] == 0) {
            int row = downLoadNumber[1] / 6;//行
            int count = downLoadNumber[1] % 6;//列
            return xb_path[row][count].getPath();
        } else {
            int row = downLoadNumber[2] / 6;//行
            int count = downLoadNumber[2] % 6;//列

            StringBuffer buffer = new StringBuffer();
            buffer.append("https://www.mhk8.com/list/").
                    append(lx_path[row][count].getPath())//类型，1为少年，2少女，3青年，4女性
                    .append("/")
                    .append("1-");//页数
            row = downLoadNumber[4] / 6;//行
            count = downLoadNumber[4] % 6;//列
            buffer.append(jq_path[row][count].getPath())//剧情
                    .append("-");//分割符
            row = downLoadNumber[3] / 6;//行
            count = downLoadNumber[3] % 6;//列
            buffer.append(dq_path[row][count].getPath())//地区
                    .append("--time.html");
            return buffer.toString();
        }
    }


}
