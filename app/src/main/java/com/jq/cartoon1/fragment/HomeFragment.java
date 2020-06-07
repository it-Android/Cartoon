package com.jq.cartoon1.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jq.cartoon1.R;
import com.jq.cartoon1.activity.DetailedActivity;
import com.jq.cartoon1.activity.MainActivity;
import com.jq.cartoon1.adapter.PreviewRecycleAdapter;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.adapter.javaBean.PreviewImgData;
import com.jq.cartoon1.mvp.F_HomePreseterLayter;
import com.jq.cartoon1.mvp.MvpViewLayer;
import com.jq.cartoon1.mvp.base.BaseFragmentLayer;
import com.jq.cartoon1.screen.address.CarToonAddress;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.utils.SpUtils;

import java.util.List;
import java.util.Vector;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 17:37
 **/
public class HomeFragment extends BaseFragmentLayer implements MvpViewLayer<Object>, RecycleViewLisitenter.onItemClickLisitenter, RadioGroup.OnCheckedChangeListener {
    private Context context;//上下文对象
    private Toolbar toolbar;//
    private DrawerLayout drawerLayout;//侧滑菜单
    private RecyclerView recyclerView;
    private PreviewRecycleAdapter adapter;
    private List<PreviewImgData> list = new Vector<>();//界面显示数据

    private SearchAllEntity allEntity;//单页搜索的数据
    private F_HomePreseterLayter preseterLayter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout[] ll_box=new LinearLayout[4];

    private RadioGroup[] rg_xb = new RadioGroup[2];
    private RadioGroup[] rg_jq = new RadioGroup[4];
    private RadioGroup rg_lx;
    private RadioGroup rg_dq;

    private RadioButton[] rb_xb = new RadioButton[12];//小编
    private RadioButton[] rb_jq = new RadioButton[24];//剧情
    private RadioButton[] rb_lx = new RadioButton[6];//类型
    private RadioButton[] rb_dq = new RadioButton[6];//地区
    private int[] downLoadNumber = new int[5];
    private int[] oldDownLoadNumber = new int[5];
    private TextView title, subtitle, find_not;
    private String[] netPath=new String[]{"",""};

    private boolean isDownLoad = false;

    @Nullable
    @Override
    public synchronized View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogId(1);
        context = container.getContext();//初始化上下文对象
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_main_home_layout, container, false);//获取布局，初始化控件
        toolbar = view.findViewById(R.id.fra_home_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).setTitle("");//将默认标题隐藏
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.u0);//修改返回主键的图片
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键显示
            setHasOptionsMenu(true);//设置菜单栏可用
        }
        preseterLayter = new F_HomePreseterLayter();
        preseterLayter.attachView(this);

        //获取历史分类信息，0为小编分类，1为全部分类，保存到下标0下面
        if (SpUtils.getBoolead(context, getString(R.string.sp_home_category))) {
            downLoadNumber[0] = 0;//0为小编分类
        } else {
            downLoadNumber[0] = 1;
        }
        downLoadNumber[1] = SpUtils.getInt(context, getString(R.string.sp_home_xb));//保存选中小编分类的哪项（小编标）
        downLoadNumber[2] = SpUtils.getInt(context, getString(R.string.sp_home_lx));//
        downLoadNumber[3] = SpUtils.getInt(context, getString(R.string.sp_home_dq));//
        downLoadNumber[4] = SpUtils.getInt(context, getString(R.string.sp_home_jq));//
        for (int i = 0; i < downLoadNumber.length; i++) {
            oldDownLoadNumber[i] = downLoadNumber[i];//深层拷贝
        }
        initView(view);
        isDownLoad=true;
        preseterLayter.getHomeData(downLoadNumber, ""//下载漫画信息
                , SpUtils.getString(context, getString(R.string.sp_website)), 0);//传入网站，返回码
        isDownLoad=false;
        showTitle();//显示标题和副标题
        return view;
    }

    //初始化控件
    private void initView(View view) {
        title = view.findViewById(R.id.fra_home_title);//标题
        subtitle = view.findViewById(R.id.fra_home_subtitle);//副标题
        find_not = view.findViewById(R.id.fra_home_tv_not);//
        drawerLayout = view.findViewById(R.id.fra_home_draw);
        recyclerView = view.findViewById(R.id.fra_home_recycle);
        adapter = new PreviewRecycleAdapter(list);
        adapter.setOnItemClickLisitenter(this);
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(manager);//添加布局管理器
        recyclerView.setAdapter(adapter);//添加适配器
        swipeRefreshLayout = view.findViewById(R.id.fra_home_swipe);
        for (int i = 0; i < rg_xb.length; i++) {
            rg_xb[i] = view.findViewById(getResource("fra_home_rg_xb" + i));//初始化 小编 RadioGroup
            rg_xb[i].setTag("xb" + i);
            rg_xb[i].setTag("xb" + i);
            rg_xb[i].setOnCheckedChangeListener(this);
        }
        for (int i = 0; i < rg_jq.length; i++) {
            rg_jq[i] = view.findViewById(getResource("fra_home_rg_jq" + i));
            rg_jq[i].setTag("jq" + i);
            rg_jq[i].setOnCheckedChangeListener(this);
        }
        for (int i = 0; i < rb_jq.length; i++) {
            rb_jq[i] = view.findViewById(getResource("fra_home_rb_jq" + i));
            rb_jq[i].setTag("jq" + i);
            int row = i / 6;//行
            int count = i % 6;//列
            if(row<CarToonAddress.getJq(getWebsite()).length&&count<CarToonAddress.getJq(getWebsite())[row].length){
                rb_jq[i].setText(CarToonAddress.getJq(getWebsite())[row][count].getName());
                rb_jq[i].setVisibility(View.VISIBLE);
            }else {
                rb_jq[i].setVisibility(View.GONE);
            }
            if(i<ll_box.length){
                ll_box[i]=view.findViewById(getResource("fra_home_ll_" + i));
                ll_box[i].setVisibility(View.GONE);
            }

            if (i < rb_xb.length) {//初始化小编分类
                rb_xb[i] = view.findViewById(getResource("fra_home_rb_xb" + i));
                rb_xb[i].setTag("xb" + i);
                if(row<CarToonAddress.getXb(getWebsite()).length&&count<CarToonAddress.getXb(getWebsite())[row].length){
                    rb_xb[i].setText(CarToonAddress.getXb(getWebsite())[row][count].getName());
                    rb_xb[i].setVisibility(View.VISIBLE);
                }else{
                    rb_xb[i].setVisibility(View.GONE);
                }
            }
            if (i < rb_lx.length) {
                rb_lx[i] = view.findViewById(getResource("fra_home_rb_lx" + i));
                rb_lx[i].setTag("lx" + i);
                if(row<CarToonAddress.getLx(getWebsite()).length&&count<CarToonAddress.getLx(getWebsite())[row].length){
                    rb_lx[i].setText(CarToonAddress.getLx(getWebsite())[row][count].getName());
                    rb_lx[i].setVisibility(View.VISIBLE);
                }else{
                    rb_lx[i].setVisibility(View.GONE);
                }
            }
            if (i < rb_dq.length) {
                rb_dq[i] = view.findViewById(getResource("fra_home_rb_dq" + i));
                rb_dq[i].setTag("dq" + i);
                if(row<CarToonAddress.getDq(getWebsite()).length&&count<CarToonAddress.getDq(getWebsite())[row].length){
                    rb_dq[i].setText(CarToonAddress.getDq(getWebsite())[row][count].getName());
                    rb_dq[i].setVisibility(View.VISIBLE);
                }else{
                    rb_dq[i].setVisibility(View.GONE);
                }
            }
        }
        if(CarToonAddress.getXb(getWebsite()).length>0)ll_box[0].setVisibility(View.VISIBLE);//显示
        if(CarToonAddress.getLx(getWebsite()).length>0)ll_box[1].setVisibility(View.VISIBLE);//显示
        if(CarToonAddress.getDq(getWebsite()).length>0)ll_box[2].setVisibility(View.VISIBLE);//显示
        if(CarToonAddress.getJq(getWebsite()).length>0)ll_box[3].setVisibility(View.VISIBLE);//显示

        rg_lx = view.findViewById(R.id.fra_home_rg_lx0);//分类
        rg_dq = view.findViewById(R.id.fra_home_rg_dq0);//地区
        rg_lx.setTag("lx");
        rg_dq.setTag("dq");
        rg_lx.setOnCheckedChangeListener(this);
        rg_dq.setOnCheckedChangeListener(this);


        //滚动监听
        list.clear();
        preseterLayter.closeThread();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();//最后一个
                int itemCount = manager.getItemCount();//一共多少个
                if (itemCount <= lastVisibleItemPosition + 3 * 6) {
                    netPath[0]=allEntity.getNext();
                    if (isDownLoad & allEntity != null && !netPath[0].equals(netPath[1])) {//可以下载，且数据不为空，当前不是最后一页
                        netPath[1]=netPath[0];
                        isDownLoad = false;
                        preseterLayter.getHtmlData(netPath[0]//下载链接
                                , "", downLoadNumber[0]//
                                , SpUtils.getString(context, getString(R.string.sp_website))//网站
                                ,1
                                , 0);//发回标识码
                        Log.e("数据监控", "我开始下载啦");
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(context,"开始搜索",Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                upData();//重新下载
            }
        });

        //侧滑菜单栏的监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            //打开
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (SpUtils.getBoolead(context, getString(R.string.sp_home_category))) {//判断当前是小编分类还是全部分类
                    int xb = SpUtils.getInt(context, getString(R.string.sp_home_xb));
                    rb_xb[xb].setChecked(true);
                } else {
                    int lx = SpUtils.getInt(context, getString(R.string.sp_home_lx));
                    int dq = SpUtils.getInt(context, getString(R.string.sp_home_dq));
                    int jq = SpUtils.getInt(context, getString(R.string.sp_home_jq));
                    rb_lx[lx].setChecked(true);
                    rb_dq[dq].setChecked(true);
                    rb_jq[jq].setChecked(true);
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                for (int i = 0; i < downLoadNumber.length; i++) {
                    if (downLoadNumber[i] != oldDownLoadNumber[i]) {//数据有变更
                        //Toast.makeText(context, "有变更", Toast.LENGTH_SHORT).show();
                        break;//跳出循环
                    }
                    if (i == downLoadNumber.length - 1) {
                        //Toast.makeText(context, "无变更，不执行", Toast.LENGTH_SHORT).show();
                        return;//结束
                    }
                }

                for (int i = 0; i < downLoadNumber.length; i++) {
                    oldDownLoadNumber[i] = downLoadNumber[i];
                }
                upData();//重新下载
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //清空数据重新下载
    private void upData() {
        list.clear();
        preseterLayter.closeThread();
        allEntity=new SearchAllEntity();
        getActivity().runOnUiThread(()->{
            adapter.notifyDataSetChanged();
        });
        isDownLoad = true;
        preseterLayter.getHomeData(downLoadNumber, ""
                , SpUtils.getString(context, getString(R.string.sp_website)), 0);
        showTitle();//更新标题
    }

    //侧滑菜单关闭或打开
    private void drawOpenOrClose() {
        if (drawerLayout != null) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);//打开
            } else {
                drawerLayout.closeDrawers();//关闭
                //drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawOpenOrClose();
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View v, int position) {
        //Toast.makeText(context,"点击"+position,Toast.LENGTH_SHORT).show();
        PreviewImgData imgData = list.get(position);
        new ChapterTable(getActivity(),getWebsite(), imgData.getCartoonId());//判断表是否存在，不存在创建
        preseterLayter.getHtmlData(imgData.getPath()
                , "", downLoadNumber[0]
                , SpUtils.getString(context, getString(R.string.sp_website))
                ,1
                , 1);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = getView().findViewById(group.getCheckedRadioButtonId());//获取勾选的是那个按钮
        String str = (String) radioButton.getTag();//获取保存在按钮上的标签
        int number = Integer.valueOf(str.substring(2));
        str = str.substring(0, 2);
        closeXb(group.getId());//
        closeClassic(group.getId());
        Boolean category = false;
        switch (str) {
            case "xb":
                downLoadNumber[1] = number;
                SpUtils.putInt(context, getString(R.string.sp_home_xb), number);//
                break;
            case "lx":
                downLoadNumber[2] = number;
                SpUtils.putInt(context, getString(R.string.sp_home_lx), number);//
                break;
            case "dq":
                downLoadNumber[3] = number;
                SpUtils.putInt(context, getString(R.string.sp_home_dq), number);//
                break;
            case "jq":
                downLoadNumber[4] = number;
                SpUtils.putInt(context, getString(R.string.sp_home_jq), number);//
                break;
        }
        if (((String) group.getTag()).indexOf("xb") != -1) {
            closelx();
            closedq();
            category = true;
            downLoadNumber[0] = 0;
        } else {
            downLoadNumber[0] = 1;
            if (!rb_lx[downLoadNumber[2]].isChecked())
                rb_lx[downLoadNumber[2]].setChecked(true);
            if (!rb_dq[downLoadNumber[3]].isChecked())
                rb_dq[downLoadNumber[3]].setChecked(true);
            if (!rb_jq[downLoadNumber[4]].isChecked())
                rb_jq[downLoadNumber[4]].setChecked(true);
        }
        SpUtils.putBoolean(context, getString(R.string.sp_home_category), category);//小编分类还是全部分类
    }

    private void closeXb(int id) {
        for (int i = 0; i < rg_xb.length; i++) {
            if (id != rg_xb[i].getId()) {
                rg_xb[i].setOnCheckedChangeListener(null);
                rg_xb[i].clearCheck();
                rg_xb[i].setOnCheckedChangeListener(this);

            }
        }
    }

    private void closelx() {
        rg_lx.setOnCheckedChangeListener(null);
        rg_lx.clearCheck();
        rg_lx.setOnCheckedChangeListener(this);
    }

    private void closedq() {
        rg_dq.setOnCheckedChangeListener(null);
        rg_dq.clearCheck();
        rg_dq.setOnCheckedChangeListener(this);
    }

    private void closeClassic(int id) {
        for (int i = 0; i < rg_jq.length; i++) {
            if (id != rg_jq[i].getId()) {
                rg_jq[i].setOnCheckedChangeListener(null);
                rg_jq[i].clearCheck();
                rg_jq[i].setOnCheckedChangeListener(this);
            }
        }
    }


    @Override
    public void showData(Object obj, String msg, long id) {
        switch ((int) id) {
            case 0:
                allEntity = (SearchAllEntity) obj;
                netPath[0]=allEntity.getNext();
                if (allEntity != null && !netPath[0].equals(netPath[1])) {//下一页是否与当前页一样
                    isDownLoad = true;
                }

                if(allEntity.getNowNumber() == 1){
                    if (allEntity.getListSearch().size() == 0) {
                        getActivity().runOnUiThread(() -> {
                            find_not.setText("没有漫画信息！");
                        });
                    } else {
                        getActivity().runOnUiThread(() -> {
                            find_not.setText("");
                        });
                    }
                }
                //添加新下载的漫画信息到显示
                for (int i = 0; i < allEntity.getListSearch().size(); i++) {
                    PreviewImgData imgData = new PreviewImgData();
                    BasicEntity basicEntity = allEntity.getListSearch().get(i);
                    imgData.setCartoonId(basicEntity.getCartoonId());
                    imgData.setTime(basicEntity.getNewTime());
                    imgData.setName(basicEntity.getName());
                    imgData.setImgPath(basicEntity.getImgPath());
                    imgData.setChapterName(basicEntity.getNewest());
                    imgData.setPath(basicEntity.getPath());
                    list.add(imgData);
                }
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });                break;
            case 1:
                DetailedEntity detailedEntity = (DetailedEntity) obj;
                String cartoonId = detailedEntity.getCartoonId();
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtra("cartoonId", cartoonId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showFailed(String msg, long id) {
        super.showFailed(msg, id);
        getActivity().runOnUiThread(()->{
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        preseterLayter.closeThread();
        preseterLayter.detachView();
    }

    private void showTitle() {
        if (downLoadNumber[0] == 0&&ll_box[0]!=null&&ll_box[0].getVisibility()==View.VISIBLE) {
            title.setText("小编分类");
            subtitle.setText(rb_xb[downLoadNumber[1]].getText());
        } else {
            title.setText("全部分类");
            StringBuffer buffer = new StringBuffer();
            buffer.append("# ");
            if(ll_box[1]!=null&&ll_box[1].getVisibility()==View.VISIBLE){
                buffer.append(rb_lx[downLoadNumber[2]].getText()).append(" # ");
            }
            if(ll_box[2]!=null&&ll_box[2].getVisibility()==View.VISIBLE){
                if (downLoadNumber[3] == 0&&rb_dq[downLoadNumber[3]].getText().equals("全部")) {
                    buffer.append("全部地区").append(" # ");
                } else {
                    buffer.append(rb_dq[downLoadNumber[3]].getText()).append(" # ");
                }
            }
            if(ll_box[3]!=null&&ll_box[3].getVisibility()==View.VISIBLE){
                if (downLoadNumber[4] == 0&&rb_jq[downLoadNumber[4]].getText().equals("全部")) {
                    buffer.append("全部剧情").append(" # ");
                } else {
                    buffer.append(rb_jq[downLoadNumber[4]].getText()).append(" #");
                }
            }
            subtitle.setText(buffer.toString());
        }
    }

    public int getResource(String imageName) {
        Context ctx = getActivity().getBaseContext();
        int resId = getResources().getIdentifier(imageName, "id", ctx.getPackageName());
        return resId;
    }

}
