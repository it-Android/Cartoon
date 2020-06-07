package com.jq.cartoon1.mvp;

import com.jq.cartoon1.mvp.base.BaseViewLayer;

/**
 * 服务的view层
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:45
 **/
public interface MvpViewLayer<T> extends BaseViewLayer {
    void showData(T obj,String msg,long id);//显示数据
}
