package com.jq.cartoon1.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jq.cartoon1.R;
import com.jq.cartoon1.dialog.ReplaceWebsiteDialog;
import com.jq.cartoon1.mvp.base.BaseFragmentLayer;
import com.jq.cartoon1.utils.CacheUtil;

/**
 * 我的碎片页面
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 17:37
 **/
public class MineFragment extends BaseFragmentLayer implements View.OnClickListener{
    private Context context;//上下文对象
    private TextView tv_switch,tv_clear,tv_show;
    @Nullable
    @Override
    public synchronized View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();//初始化上下文对象
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_main_mine_layout,container,false);//获取布局，初始化控件
        initView(view);
        return view;
    }

    private void initView(View view){
        tv_switch=view.findViewById(R.id.fra_mine_tv_switch);
        tv_clear=view.findViewById(R.id.fra_mine_tv_clear);
        tv_show=view.findViewById(R.id.fra_mine_tv_show );
        tv_switch.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        tv_show.setText(CacheUtil.getInstance().getCacheSize(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fra_mine_tv_switch:
                ReplaceWebsiteDialog websiteDialog=new ReplaceWebsiteDialog(context);
                websiteDialog.showDialog();
                break;
            case R.id.fra_mine_tv_clear:
                CacheUtil.getInstance().clearImageAllCache(context);
                tv_show.setText("0 KB");
                Toast.makeText(getActivity(),"清除完成",Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
