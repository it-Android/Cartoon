package com.jq.cartoon1.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jq.cartoon1.R;
import com.jq.cartoon1.activity.DetailedActivity;
import com.jq.cartoon1.adapter.CollectRecycleAdapter;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.adapter.javaBean.CollectImgData;
import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.mvp.F_CollectPresenterLayter;
import com.jq.cartoon1.mvp.MvpViewLayer;
import com.jq.cartoon1.mvp.base.BaseFragmentLayer;

import java.util.List;
import java.util.Vector;
/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 17:37
 **/
public class CollectFragment extends BaseFragmentLayer implements MvpViewLayer<CollectImgData>, RecycleViewLisitenter.onItemClickLisitenter {
    private Context context;//上下文对象
    private RecyclerView recyclerView;
    private GridLayoutManager manager;
    private CollectRecycleAdapter adapter;
    private List<CollectImgData> list = new Vector<>();
    private DetailedTable detailedTable;
    private F_CollectPresenterLayter presenterLayter;

    @Nullable
    @Override
    public synchronized View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();//初始化上下文对象
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_main_collect_layout, container, false);//获取布局，初始化控件
        detailedTable = new DetailedTable(context,getWebsite());//详细信息表
        presenterLayter = new F_CollectPresenterLayter();
        presenterLayter.attachView(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fra_collect_recycle);
        manager = new GridLayoutManager(context, 3);
        adapter = new CollectRecycleAdapter(list);
        adapter.setOnItemClickLisitenter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        List<BaseDetailed> detailedList = detailedTable.getCollection();//获取全部收藏的漫画信息
        list.clear();//
        for (int i = 0; i < detailedList.size(); i++) {
            BaseDetailed baseDetailed = detailedList.get(i);//获取单本漫画的基本信息
            presenterLayter.getChapter(baseDetailed.getCartoonId()//漫画id
                    , baseDetailed.getProgress()//漫画进度
                    , baseDetailed.getUpData()//漫画是否有更新
                    , "", i);//返回接收标识
            CollectImgData collectImgData = new CollectImgData();//漫画显示的信息
            list.add(collectImgData);//添加
        }
        adapter.notifyDataSetChanged();//刷新显示
    }

    @Override
    public void onItemClick(View v, int position) {
        String cartoonId = list.get(position).getCartoonId();
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        intent.putExtra("cartoonId", cartoonId);
        startActivity(intent);
    }

    @Override
    public void showData(CollectImgData obj, String msg, long id) {
        list.set((int) id, obj);
        getActivity().runOnUiThread(() -> {
            adapter.notifyItemChanged((int) id);
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
            initView(getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterLayter.closeThread();
        presenterLayter.detachView();
    }


}
