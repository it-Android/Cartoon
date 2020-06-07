package com.jq.cartoon1.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jq.cartoon1.R;
import com.jq.cartoon1.utils.SystemUtils;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2019/10/20 12:32
 **/
public class LoadingDialog {
    private Context context;
    private AlertDialog dialog;

    private int[] screen=new int[2];

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showDialog(){
        screen= SystemUtils.getScreen(context);//获取屏幕尺寸
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_loading_image_layout,null,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.dl_loading_im_1);
        dialog=new AlertDialog.Builder(context).setView(view).setCancelable(false).create();
        Glide.with(view).load(R.raw.loading_1).into(imageView);
        dialog.show();
        Window window = dialog.getWindow();
        int width= (int) (screen[0]*2.5/10);
        window.setLayout(width,width);
    }

    public void closeDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }
}
