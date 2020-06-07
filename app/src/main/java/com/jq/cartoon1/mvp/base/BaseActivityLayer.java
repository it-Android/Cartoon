package com.jq.cartoon1.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jq.cartoon1.R;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.utils.SpUtils;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/29 10:21
 **/
public class BaseActivityLayer extends AppCompatActivity implements BaseViewLayer{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showStart(String msg, long id) {

    }

    @Override
    public void showFailed(String msg, long id) {
    }

    @Override
    public void showError(long id) {
        Log.e("Activity","数据下载异常,数据id为"+id);
    }

    @Override
    public void showEnd(String msg, long id) {

    }

    @Override
    public Context getContext() {
        return this;
    }



    //获取数据来源网站
    public String getWebsite(){
        String website= SpUtils.getString(this,getString(R.string.sp_website));
        if(website.equals("")){
            return Website.TOHOMH123;
        }
        return website;
    }

}
