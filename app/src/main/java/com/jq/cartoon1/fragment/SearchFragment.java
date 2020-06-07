package com.jq.cartoon1.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jq.cartoon1.R;
import com.jq.cartoon1.activity.DetailedActivity;
import com.jq.cartoon1.activity.MainActivity;
import com.jq.cartoon1.adapter.PreviewRecycleAdapter;
import com.jq.cartoon1.dialog.LoadingDialog;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.adapter.javaBean.PreviewImgData;
import com.jq.cartoon1.screen.address.CarToonAddress;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.mvp.MvpViewLayer;
import com.jq.cartoon1.mvp.F_SearchPresenterLayer;
import com.jq.cartoon1.mvp.base.BaseFragmentLayer;
import com.jq.cartoon1.utils.SpUtils;
import com.jq.cartoon1.utils.SystemUtils;

import java.util.List;
import java.util.Vector;

/**
 * 搜索碎片页面
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 17:37
 **/
public class SearchFragment extends BaseFragmentLayer implements View.OnClickListener,MvpViewLayer, RecycleViewLisitenter.onItemClickLisitenter {
    private Context context;//上下文对象
    private Toolbar toolbar;//标题栏
    private RecyclerView recyclerView;//显示搜索信息的控件
    private PreviewRecycleAdapter adapter;//显示控件的适配器
    private List<PreviewImgData> list=new Vector<>();//数据
    private EditText ed_input;//输入框
    private String []inputData=new String[]{"",""};//保存搜索输入
    private F_SearchPresenterLayer presenterLayer;
    private SearchAllEntity allEntity;//单次的搜索记录
    private Boolean isDownLoad=false;
    private TextView tv_submit;//搜索按钮
    private SwipeRefreshLayout swipeRefreshLayout;//刷新布局
    private LoadingDialog dialog;
    @Nullable
    @Override
    public synchronized View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();//初始化上下文对象
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_main_search_layout,container,false);//获取布局，初始化控件
        toolbar=view.findViewById(R.id.fra_search_toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        presenterLayer=new F_SearchPresenterLayer();
        presenterLayer.attachView(this);
        initView(view);
        return view;
    }

    private void initView(View view){
        dialog=new LoadingDialog(context);
        adapter=new PreviewRecycleAdapter(list);//实例化适配器
        recyclerView=view.findViewById(R.id.fra_search_recycle);
        GridLayoutManager manager=new GridLayoutManager(context,3);//
        recyclerView.setLayoutManager(manager);//添加布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLisitenter(this);//设置点击事件
        tv_submit=view.findViewById(R.id.fra_search_tv_submit);//注册按钮
        tv_submit.setOnClickListener(this);//设置按钮的点击事件
        ed_input =view.findViewById(R.id.fra_search_et_input);
        swipeRefreshLayout=view.findViewById(R.id.fra_search_swipe);
        //滚动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();//最后一个可见控件下标
                int itemCount = manager.getItemCount();//一共多少个
                if(itemCount<=lastVisibleItemPosition+3*6){
                    if(isDownLoad&allEntity!=null&&allEntity.getLastNumber()!=allEntity.getNowNumber()){//可以下载，且数据不为空，当前不是最后一页
                        isDownLoad=false;
                        presenterLayer.getHtmlData(allEntity.getNext()
                                ,""
                                ,SpUtils.getString(context,getString(R.string.sp_website))
                                ,10);
                        Log.e("数据监控","我开始下载啦");
                    }
                }

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(context,"开始搜索",Toast.LENGTH_SHORT).show();
                tv_submit.setClickable(false);//
                SystemUtils.closeKeybord((MainActivity)getActivity());//关闭软键盘
                upData();//重新下载
            }
        });
        //输入框点击回车事件，
        ed_input.setOnEditorActionListener((TextView v, int actionId, KeyEvent event)->{
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索（发送消息）操作（此处省略，根据读者自己需要，直接调用自己想实现的方法即可）
                onClick(tv_submit);//点击按钮
            }
            return false;
        });
    }

    @Override
    public void showData(Object obj, String msg, long id) {
       switch ((int) id){
           case 10:
               allEntity= (SearchAllEntity) obj;
               if(allEntity!=null&&allEntity.getNowNumber()!=allEntity.getLastNumber()){//当前页数不等于最后页数就继续可以下载
                   isDownLoad=true;
               }
               if(allEntity.getNowNumber()==1){//判断当前页是否最后页
                   getActivity().runOnUiThread(()->{
                       Toast.makeText(context,"搜索到"+allEntity.getAll()+"个结果",Toast.LENGTH_SHORT).show();
                   });
               }
               //添加下载好的漫画信息，显示
               for(int i=0;i<allEntity.getListSearch().size();i++){
                   PreviewImgData imgData=new PreviewImgData();
                   BasicEntity basicEntity = allEntity.getListSearch().get(i);//读取单个漫画信息
                   imgData.setCartoonId(basicEntity.getCartoonId());//添加漫画id
                   imgData.setTime(basicEntity.getNewTime());
                   imgData.setName(basicEntity.getName());
                   imgData.setImgPath(basicEntity.getImgPath());
                   imgData.setChapterName(basicEntity.getNewest());
                   imgData.setPath(basicEntity.getPath());
                   list.add(imgData);
               }
               getActivity().runOnUiThread(()->{
                   adapter.notifyDataSetChanged();
                   dialog.closeDialog();
               });
               break;
           case 11:
               DetailedEntity detailedEntity=(DetailedEntity)obj;
               String cartoonId=detailedEntity.getCartoonId();//获取漫画id
               Intent intent=new Intent(getActivity(), DetailedActivity.class);//跳转到详细信息界面
               intent.putExtra("cartoonId",cartoonId);//传递漫画id过去
               startActivity(intent);//跳转到详细信息界面
               break;
       }
    }

    @Override
    public void showEnd(String msg, long id) {
        super.showEnd(msg, id);
        getActivity().runOnUiThread(()->{
            tv_submit.setClickable(true);//下载成功将下载按钮设置为可用
        });
        if (swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing()){
            getActivity().runOnUiThread(()->{
                swipeRefreshLayout.setRefreshing(false);//
            });
        }
    }

    @Override
    public void onClick(View v) {
        inputData[0]= ed_input.getText().toString().trim();//读取输入框数据
        if(inputData[0].equals(inputData[1])){//判断两次点击按钮输入东西是否一样
            Toast.makeText(context,"索引一样,请刷新搜索",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(context,"开始搜索",Toast.LENGTH_SHORT).show();
            tv_submit.setClickable(false);//
            dialog.showDialog();
            SystemUtils.closeKeybord((MainActivity)getActivity());//关闭软键盘
            upData();//获取漫画
        }
    }
    @Override
    public void onItemClick(View v, int position) {
        PreviewImgData imgData = list.get(position);//获取点击的漫画的基本信息
        new ChapterTable(getActivity(),getWebsite(),imgData.getCartoonId());//判断表是否存在，不存在创建
        presenterLayer.getHtmlData(imgData.getPath(),""//下载漫画详细信息
                ,SpUtils.getString(context,getString(R.string.sp_website))//网站信息
                ,imgData.getCartoonId()//漫画id
                ,11);//返回标识码
    }

    private void upData(){
        list.clear();//清空显示数据
        presenterLayer.closeThread();//停止线程
        allEntity=new SearchAllEntity();
        if(inputData[0].equals("")){//判断输入是否为空
            Toast.makeText(getActivity(),"输入不能为空",Toast.LENGTH_SHORT).show();
            if (swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
                tv_submit.setClickable(true);//
            }
            return;
        }
        presenterLayer.getHtmlData(
                CarToonAddress.getSearchPath(SpUtils.getString(context,getActivity().getString(R.string.sp_website)),inputData[0])//获取漫画搜索连接
                 ,""
                ,SpUtils.getString(context,getString(R.string.sp_website)),10);
        inputData[1]=inputData[0];
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterLayer.closeThread();
        presenterLayer.detachView();
    }
}
