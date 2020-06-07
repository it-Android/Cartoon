package com.jq.cartoon1.mvp.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jq.cartoon1.mvp.MvpViewLayer;

/**
 *
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:51
 **/
public class BaseServiceLayer extends Service implements MvpViewLayer {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showData(Object obj, String msg, long id) {

    }

    @Override
    public void showStart(String msg, long id) {

    }

    @Override
    public void showFailed(String msg, long id) {

    }

    @Override
    public void showError(long id) {
        Log.e("Service监听","数据错误，数据ID为 "+id);
    }

    @Override
    public void showEnd(String msg, long id) {

    }

    @Override
    public Context getContext() {
        return this;
    }

}
