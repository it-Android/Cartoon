package com.jq.cartoon1.mvp;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:23
 **/
public interface MvpCallback<T> {
    void onSuccess(T data,String msg,long id);//成功时返回的
    void onFailed(String msg,long id);//失败时返回的
    void onError(long id);//错误时返回的
    void onComplete();//完成时调用
}
