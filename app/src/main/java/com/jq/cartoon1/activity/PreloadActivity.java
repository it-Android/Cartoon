package com.jq.cartoon1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.jq.cartoon1.R;
import com.jq.cartoon1.mvp.base.BaseActivityLayer;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.screen.sql.BasicTable;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.service.NetworkService;
import com.jq.cartoon1.utils.CacheUtil;
import com.jq.cartoon1.utils.SpUtils;

//预加载界面
public class PreloadActivity extends BaseActivityLayer {
    private String website;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        Intent intent = new Intent(PreloadActivity.this, NetworkService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);//启动服务
        CacheUtil.getInstance().clearImageDiskCache(this);//清空磁盘缓存
        website = SpUtils.getString(this, getString(R.string.sp_website));//获取网站
        if (website == null || website.equals("")) {
            website = Website.TOHOMH123;
            SpUtils.putString(this, getString(R.string.sp_website), website);
        }
        new BasicTable(this,Website.MHK);//基本数据表，漫画狂
        new DetailedTable(this,Website.MHK);//详细信息表，漫画狂
        new BasicTable(this,Website.TOHOMH123);//基本数据表，土豪漫画
        new DetailedTable(this,Website.TOHOMH123);//详细信息表，土豪漫画
        new BasicTable(this,Website.LRY);//基本数据表，LRY
        new DetailedTable(this,Website.LRY);//详细信息表，LRY

        boolean first = SpUtils.getSharedPreferences(this).getBoolean(getString(R.string.sp_first), true);//判断是否第一次进入
        if(first){
            SpUtils.putBoolean(this,getString(R.string.sp_first),false);
            SpUtils.putBoolean(this,getString(R.string.sp_home_category),true);
            SpUtils.putLong(this,getString(R.string.sp_closeTime),2*30*24*60*60*1000L);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(PreloadActivity.this, MainActivity.class));
                if(networkService!=null){
                    networkService.zeroTime();
                }
                finish();
            }
        }, 2000);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NetworkService.MyIBinder binder = (NetworkService.MyIBinder) service;
            networkService= binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null){
            unbindService(connection);
        }
    }
}
