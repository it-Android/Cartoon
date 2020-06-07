package com.jq.cartoon1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.jq.cartoon1.R;
import com.jq.cartoon1.adapter.AppBarStateChangeListener;
import com.jq.cartoon1.adapter.DetailedRecycleAdapter;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.mvp.base.BaseActivityLayer;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;
import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;
import com.jq.cartoon1.screen.sql.BasicTable;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.utils.SpUtils;

import java.util.Collections;
import java.util.List;

public class DetailedActivity extends BaseActivityLayer implements RadioGroup.OnCheckedChangeListener, RecycleViewLisitenter.onItemClickLisitenter {
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private String cartoonId = "";
    private List<BaseChapter> list;//章节信息
    private RecyclerView recyclerView;
    private DetailedRecycleAdapter adapter;
    private LinearLayoutManager manager;
    private ImageView iv_bg;
    private TextView tv_name, tv_author, tv_classify, tv_introduce, tv_time, tv_region, tv_state, tv_all,tv_read;
    private BasicTable basicTable;//基本信息表
    private ChapterTable chapterTable;//章节信息表操作类
    private DetailedTable detailedTable;//详细信息表操作类
    private BasicEntity basicByID;//一本漫画的基本信息
    private BaseDetailed baseDetailed;//一本漫画的详细信息
    private Boolean order = true;//保存数组，排序
    private RadioGroup rg_box;
    private AppBarLayout appBarLayout;
    private CheckBox cb_collection;
    private RadioButton rb_just,rb_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        cartoonId = intent.getStringExtra("cartoonId");
        basicTable = new BasicTable(this,getWebsite());//基本信息表
        detailedTable = new DetailedTable(this,getWebsite());//详细信息表
        chapterTable = new ChapterTable(this,getWebsite(), cartoonId);//章节信息学表
        basicByID = basicTable.getBasicByID(cartoonId);//获取固定id的漫画的基本信息
        baseDetailed = detailedTable.getByCartoonId(cartoonId);//获取固定id的详细漫画信息
        list = chapterTable.getChapter();//获取全部详细信息
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.detailed_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.u0);//修改返回主键的图片
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键显示
        }
        appBarLayout = findViewById(R.id.detailed_ab_appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    toolbar.setTitle(basicByID.getName());
                } else {
                    //中间状态
                    toolbar.setTitle("");
                }
            }

        });
        linearLayout = (LinearLayout) findViewById(R.id.detailed_ll_introduceBox);
        ((CheckBox) findViewById(R.id.detailed_cb_showIntroduce))
                .setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                    if (isChecked) {
                        linearLayout.setVisibility(View.VISIBLE);//显示漫画介绍信息
                    } else {
                        linearLayout.setVisibility(View.GONE);//隐藏漫画介绍信息
                    }
                });
        recyclerView = (RecyclerView) findViewById(R.id.detailed_recycle);
        manager = new LinearLayoutManager(this);
        adapter = new DetailedRecycleAdapter(list);
        adapter.setOnItemClickLisitenter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        iv_bg = (ImageView) findViewById(R.id.detailed_iv_bg);//背景图片
        Glide.with(this).load(basicByID.getImgPath()).into(iv_bg);//使用框架加载图片
        tv_read=findViewById(R.id.detailed_tv_read);
        String str=chapterTable.getById(baseDetailed.getProgress()).getChapterName();
        if(str.equals("")){
            str="开始阅读";
        }
        tv_read.setText(str);
        tv_name = (TextView) findViewById(R.id.detailed_tv_name);//名称
        tv_name.setText(basicByID.getName());//获取名称并显示
        tv_author = (TextView) findViewById(R.id.detailed_tv_author);//作者
        tv_author.setText("作者：" + baseDetailed.getAuthor());//获取作者
        tv_classify = (TextView) findViewById(R.id.detailed_tv_classify);//分类
        tv_classify.setText("#" + baseDetailed.getClassify() + "#");
        tv_introduce = (TextView) findViewById(R.id.detailed_tv_introduce);//介绍
        tv_introduce.setText("\t\t" + baseDetailed.getIntroduce());
        tv_time = (TextView) findViewById(R.id.detailed_tv_time);//更新时间
        String time=basicByID.getNewTime();
        if(time!=null&&!time.equals(""))
        tv_time.setText(time);
        tv_region = (TextView) findViewById(R.id.detailed_tv_region);
        tv_region.setText("地区：" + baseDetailed.getRegion());//地区
        tv_state = (TextView) findViewById(R.id.detailed_tv_state);
        if(baseDetailed.getState()!=null&&!baseDetailed.getState().equals("")) {
            tv_state.setText("#" + baseDetailed.getState() + "#");//连载
        }
        else {
            tv_state.setText("" );//连载
        }
        tv_all = (TextView) findViewById(R.id.detailed_tv_all);
        tv_all.setText("共 " + list.size() + " 章");//章节总数
        rg_box = (RadioGroup) findViewById(R.id.detailed_rg_box);
        rg_box.setOnCheckedChangeListener(this);
        cb_collection = (CheckBox) findViewById(R.id.detailed_cb_collection);
        cb_collection.setChecked(baseDetailed.getCollection());
        cb_collection.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked != baseDetailed.getCollection()) {
                detailedTable.upDataCollection(baseDetailed.getCartoonId(), isChecked);
                baseDetailed.setCollection(isChecked);
                if (isChecked) {
                    Toast.makeText(DetailedActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailedActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_read.setOnClickListener((View v)->{
            String chapterId = chapterTable.getById(baseDetailed.getProgress()).getChapterId();
            jumpActivity(chapterId);
            //Toast.makeText(this,"你点了我",Toast.LENGTH_SHORT).show();
        });
        rb_back=(RadioButton)findViewById(R.id.detailed_rb_back);
        rb_just=(RadioButton)findViewById(R.id.detailed_rb_just);
        if(!SpUtils.getBoolead(this,getString(R.string.sp_collection))){
            rb_back.setChecked(true);
            rb_just.setChecked(false);
        }
        upRead();//更新阅读时间
    }

    @Override
    public void onItemClick(View v, int position) {
        BaseChapter baseChapter = list.get(position);
        String chapterId = baseChapter.getChapterId();
        jumpActivity(chapterId);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<BaseChapter> chapter = chapterTable.getChapter();//获取全部详细信息
        baseDetailed=detailedTable.getByCartoonId(baseDetailed.getCartoonId());
        list.clear();
        if (!order) {
            Collections.reverse(chapter);
        }
        list.addAll(chapter);
        upRead();//更新阅读时间
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            tv_read.setText(chapterTable.getById(baseDetailed.getProgress()).getChapterName());
        });
    }

    private void jumpActivity(String chapterId) {
        String cartoonId = basicByID.getCartoonId();
        BaseChapter byChapterId = chapterTable.getByChapterId(chapterId);
        int readProgress=byChapterId.getProgress();

        Intent intent = new Intent(DetailedActivity.this, ReadActivity.class);
        intent.putExtra("chapterId", chapterId);//章节id
        intent.putExtra("cartoonId", cartoonId);//漫画id
        intent.putExtra("readProgress", readProgress);//阅读进度
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.detailed_rb_just:
                if (order == false) {
                    reverse();
                }
                break;
            case R.id.detailed_rb_back:
                if (order == true) {
                    reverse();
                }
                break;
        }
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();//关闭
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        upRead();
    }

    /**更新阅读了**/
    public void upRead(){
        String cartoonId = baseDetailed.getCartoonId();
        //detailedTable.upDataTime(id,System.currentTimeMillis());
        detailedTable.upDataUpData(cartoonId,false);
    }

    private Boolean reverse() {
        Collections.reverse(list);
        order = !order;
        SpUtils.putBoolean(DetailedActivity.this,getString(R.string.sp_collection),order);
        return order;
    }

}
