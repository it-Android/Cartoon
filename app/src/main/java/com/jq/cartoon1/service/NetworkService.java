package com.jq.cartoon1.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.util.Log;

import com.jq.cartoon1.mvp.ServicePresenterLayer;
import com.jq.cartoon1.mvp.base.BaseServiceLayer;

public class NetworkService extends BaseServiceLayer{
    private ServicePresenterLayer presenterLayer;
    private int upDataTime=3*60;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("数据监控", "服务开启成功");
        presenterLayer=new ServicePresenterLayer();
        presenterLayer.attachView(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                upDataTime--;
                if(upDataTime<=0){
                    presenterLayer.upDataCollect();
                    presenterLayer.closeHist();
                    upDataTime=3*60;
                }

                if(handler!=null){
                    handler.postDelayed(this,5*1000);
                }
            }
        },100);

    }

    public void zeroTime(){
        this.upDataTime=0;
    }
    @Override
    public MyIBinder onBind(Intent intent) {
        return new MyIBinder();
    }

    private Handler handler=new Handler();

    @Override
    public void showData(Object obj, String msg, long id) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterLayer.closeThread();
        presenterLayer.detachView();
        Log.e("数据监控","服务退出成功");
    }

    public class MyIBinder extends Binder{
        public NetworkService getService(){
            return NetworkService.this;
        }
    }
}
