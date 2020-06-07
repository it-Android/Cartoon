package com.jq.cartoon1.screen.address;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/1 22:47
 **/
public class TohoAddress {
    public final static Tag[][] xb_path = new Tag[][]{
            {new Tag("最新", "https://mip.tohomh123.com/f-1------updatetime--1.html"), new Tag("排行榜", "https://mip.tohomh123.com/f-1------hits--1.html"), new Tag("国漫", "https://mip.tohomh123.com/f-1--" + getData("国漫") + "----hits--1.html"), new Tag("日本", "https://mip.tohomh123.com/f-1--" + getData("日本") + "----hits--1.html")},
            {new Tag("完结", "https://mip.tohomh123.com/f-1------hits-0-1.html"), new Tag("连载", "https://mip.tohomh123.com/f-1------hits-1-1.html")},
    };
    public final static Tag[][] lx_path = new Tag[][]{
            {new Tag("全部", ""), new Tag("最新", "updatetime"), new Tag("热门", "hits"), new Tag("新作", "addtime")}
    };
    public final static Tag[][] dq_path = new Tag[][]{
            {new Tag("全部", ""), new Tag("国漫", getData("国漫")), new Tag("日本", getData("日本")), new Tag("港台", getData("港台")), new Tag("欧美", getData("欧美"))}
    };

    public final static Tag[][] jq_path = new Tag[][]{
            {new Tag("全部", ""), new Tag("热血", "1"), new Tag("恋爱", "2"), new Tag("校园", "3"), new Tag("百合", "4"), new Tag("彩虹", "5")},
            {new Tag("冒险", "6"), new Tag("后宫", "7"), new Tag("仙侠", "8"), new Tag("武侠", "9"), new Tag("悬疑", "10"), new Tag("推理", "11")},
            {new Tag("搞笑", "12"), new Tag("奇幻", "13"), new Tag("猎奇", "14"), new Tag("玄幻", "15"), new Tag("古风", "16"), new Tag("萌系", "17")},
            {new Tag("日常", "18"), new Tag("治愈", "19"), new Tag("烧脑", "20"), new Tag("穿越", "21"), new Tag("都市", "22"), new Tag("腹黑", "23")}

    };


    public static String getdownLoadPath(int[] downLoadNumber) {
        if (downLoadNumber[0] == 0) {
            int row = downLoadNumber[1] / 6;//行
            int count = downLoadNumber[1] % 6;//列
            return xb_path[row][count].getPath();
        } else {
            int row = downLoadNumber[4] / 6;//行
            int count = downLoadNumber[4] % 6;//列
            StringBuffer buffer = new StringBuffer();
            buffer.append("https://mip.tohomh123.com/f-1-")
                    .append(jq_path[row][count].getPath())//剧情
                    .append("-");//分割符

            row = downLoadNumber[3] / 6;//行
            count = downLoadNumber[3] % 6;//列
            buffer.append(dq_path[row][count].getPath())//地区
                    .append("----");//分割符

            row = downLoadNumber[2] / 6;//行
            count = downLoadNumber[2] % 6;//列
            buffer.append(lx_path[row][count].getPath())//类型，1为少年，2少女，3青年，4女性
                    .append("--1.html");
            return buffer.toString();
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
