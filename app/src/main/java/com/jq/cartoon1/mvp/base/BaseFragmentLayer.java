package com.jq.cartoon1.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jq.cartoon1.R;
import com.jq.cartoon1.dialog.LoadingDialog;
import com.jq.cartoon1.mvp.MvpViewLayer;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.utils.SpUtils;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/27 16:46
 **/
public class BaseFragmentLayer extends Fragment implements BaseViewLayer {
    private LoadingDialog dialog;
    private int dialogId=11;

    @Override
    public void showStart(String msg, long id) {
        if (id == dialogId) {
            if(dialog==null){
                dialog=new LoadingDialog(getContext());
            }
            dialog.showDialog();
        }
    }

    @Override
    public void showFailed(String msg, long id) {

    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public void showError(long id) {
        Log.e("Fragment监听", "数据错误，数据ID为 " + id);
    }

    @Override
    public void showEnd(String msg, long id) {
        if (id == dialogId) {
            dialog.closeDialog();
        }
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public void onRestart(){

    }

    //获取数据来源网站
    public String getWebsite(){
        String website= SpUtils.getString(getContext(),getString(R.string.sp_website));
        if(website.equals("")){
            return Website.TOHOMH123;
        }
        return website;
    }
}
