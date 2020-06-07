package com.jq.cartoon1.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jq.cartoon1.R;
import com.jq.cartoon1.activity.MainActivity;
import com.jq.cartoon1.activity.PreloadActivity;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.utils.SpUtils;
import com.jq.cartoon1.utils.SystemUtils;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/6 19:10
 **/
public class ReplaceWebsiteDialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private AlertDialog dialog;
    private int[] screen = new int[2];
    private String[] website=new String[]{"",""};

    public ReplaceWebsiteDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        screen = SystemUtils.getScreen(context);
        website[0]= SpUtils.getString(context,context.getString(R.string.sp_website));
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_replace_website_layout, null, false);
        initView(view);
        dialog = new AlertDialog.Builder(context).setView(view).setCancelable(true).create();
        dialog.show();
        /*Window window = dialog.getWindow();
        int width= (int) (screen[0]*2.5/10);
        window.setLayout(width,width);*/
    }

    //初始话控件
    private void initView(View view) {
        ((RadioGroup) view.findViewById(R.id.dl_website_rg_box)).setOnCheckedChangeListener(this);
        view.findViewById(R.id.dl_website_tv_submit).setOnClickListener(this);
        view.findViewById(R.id.dl_website_tv_cancel).setOnClickListener(this);
        //初始化，选中
        switch (website[0]){
            case Website.MHK:
                ((RadioButton) view.findViewById(R.id.dl_website_rb_mhk)).setChecked(true);
                break;
            case Website.TOHOMH123:
                ((RadioButton) view.findViewById(R.id.dl_website_rb_toho)).setChecked(true);
                break;
            case Website.LRY:
                ((RadioButton) view.findViewById(R.id.dl_website_rb_lry)).setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dl_website_tv_submit:
                String tip="";
                if(website[0].equals(website[1])){
                    tip="来源没有修改！";
                }else {
                    SpUtils.putString(context,context.getString(R.string.sp_website),website[1]);
                    tip="切换成功！";
                    context.startActivity(new Intent(context, PreloadActivity.class));
                    SpUtils.putInt(context,context.getString(R.string.sp_home_xb),0);
                    SpUtils.putInt(context,context.getString(R.string.sp_home_lx),0);
                    SpUtils.putInt(context,context.getString(R.string.sp_home_dq),0);
                    SpUtils.putInt(context,context.getString(R.string.sp_home_jq),0);
                    SpUtils.putBoolean(context,context.getString(R.string.sp_home_category),true);
                }
                Toast.makeText(context,tip,Toast.LENGTH_SHORT).show();
                ((MainActivity)context).finish();
                closeDialog();
                break;
            case R.id.dl_website_tv_cancel:
                closeDialog();
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.dl_website_rb_mhk:
                website[1]= Website.MHK;
                break;
            case R.id.dl_website_rb_toho:
                website[1]= Website.TOHOMH123;
                break;
            case R.id.dl_website_rb_lry:
                website[1]= Website.LRY;
                break;
        }
    }

    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
