package com.jq.cartoon1.mvp.base;

import android.content.Context;

/**
 * mvp的v层的顶级父类
 *
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:28
 **/
public interface BaseViewLayer {
    void showStart(String msg, long id);//开始时调用

    void showFailed(String msg, long id);//显示失败的数据

    void showError(long id);//显示错误的数据

    void showEnd(String msg, long id);//结束时调用

    Context getContext();

}
