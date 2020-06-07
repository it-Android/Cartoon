package com.jq.cartoon1.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jq.cartoon1.R;
import com.jq.cartoon1.fragment.MainFragmentManager;
import com.jq.cartoon1.mvp.base.BaseActivityLayer;
import com.jq.cartoon1.service.NetworkService;
import com.jq.cartoon1.utils.CacheUtil;

public class MainActivity extends BaseActivityLayer implements RadioGroup.OnCheckedChangeListener{
    private MainFragmentManager fragmentManager;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(MainActivity.this,NetworkService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        initView();//初始化控件
    }

    //初始化控件
    private void initView() {
        ((RadioGroup) (findViewById(R.id.main_rg_box))).setOnCheckedChangeListener(this);//实例化radio并注册子类item的选中事件
        fragmentManager = new MainFragmentManager(getSupportFragmentManager());
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NetworkService.MyIBinder binder= (NetworkService.MyIBinder) service;
            networkService= binder.getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rb_home:
                //Toast.makeText(MainActivity.this,"选中了首页",Toast.LENGTH_SHORT).show();
                fragmentManager.replace(0);
                break;
            case R.id.main_rb_search:
                fragmentManager.replace(1);
                break;
            case R.id.main_rb_collect:
                //Toast.makeText(MainActivity.this,"选中了收藏",Toast.LENGTH_SHORT).show();
                fragmentManager.replace(2);
                break;
            case R.id.main_rb_mine:
                //Toast.makeText(MainActivity.this,"选中了我的",Toast.LENGTH_SHORT).show();
                fragmentManager.replace(3);
                break;
        }
    }
    private boolean isColse=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(isColse){
                    finish();
                }else{
                    Toast.makeText(this, "再点一次返回键退出应用", Toast.LENGTH_SHORT).show();
                    new Thread(()->{
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isColse=false;
                    }).start();
                    isColse=true;
                }
                break;
        }
        //return false;
        return false;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if(fragmentManager!=null){
            fragmentManager.onRestart();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheUtil.getInstance().clearImageAllCache(this);
        unbindService(connection);//关闭服务
    }


}
