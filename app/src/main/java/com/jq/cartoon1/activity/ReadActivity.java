package com.jq.cartoon1.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.jq.cartoon1.R;
import com.jq.cartoon1.adapter.ReadRecycleAdapter;
import com.jq.cartoon1.myview.ZoomRecyclerView;
import com.jq.cartoon1.adapter.javaBean.ReadImgData;
import com.jq.cartoon1.screen.javaBean.PictureEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.mvp.MvpViewLayer;
import com.jq.cartoon1.mvp.ReadPresenterLayter;
import com.jq.cartoon1.mvp.base.BaseActivityLayer;
import com.jq.cartoon1.utils.SpUtils;
import com.jq.cartoon1.utils.SystemUtils;

import java.util.List;
import java.util.Vector;

/**
 * 漫画阅读界面
 **/
public class ReadActivity extends BaseActivityLayer implements MvpViewLayer<PictureEntity> {

    private ZoomRecyclerView recyclerView;
    private ReadRecycleAdapter adapter;
    private LinearLayoutManager manager;
    private int number = 0, readProgress = 0;
    private String cartoonId = "", chapterId = "";//漫画id
    private List<ReadImgData> list = new Vector<>();
    private ReadPresenterLayter presenterLayter;
    private ChapterTable chapterTable;//章节表操作类
    private List<BaseChapter> chapterList;
    private TextView tv_time, tv_progress;
    private Boolean isDownLoad = false, isFirst = true;
    private Boolean isUpData = true;
    private int oldLastFirstVisibleItemPosition = 0;//
    private Handler handler = new Handler();
    private int dataId=-21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        chapterId = intent.getStringExtra("chapterId");//章节id
        cartoonId = intent.getStringExtra("cartoonId");//漫画id
        readProgress = intent.getIntExtra("readProgress", 0);//阅读进度
        presenterLayter = new ReadPresenterLayter(cartoonId);
        presenterLayter.attachView(this);
        chapterTable = new ChapterTable(this, getWebsite(), cartoonId);
        chapterList = chapterTable.getChapter();
        number = chapterTable.getByChapterId(chapterId).getId();//章节id
        //下载本章节的漫画数据
        presenterLayter.getHtmlData(chapterList.get(number).getChapterPath(), ""
                , SpUtils.getString(ReadActivity.this, getString(R.string.sp_website)), dataId);
        initView();
        showDate();
    }

    private void initView() {
        tv_time = findViewById(R.id.read_time);//显示时间，0.5秒刷新一次
        tv_progress = findViewById(R.id.read_progress);//显示阅读进度信息，第几话，第几张
        recyclerView = (ZoomRecyclerView) findViewById(R.id.read_recycle);;//自定义recyclerView可双击放大
        recyclerView.setEnableScale(true);//允许缩放
        adapter = new ReadRecycleAdapter(list);//适配器
        manager = new LinearLayoutManager(this);//布局
        recyclerView.setAdapter(adapter);//添加适配器
        recyclerView.setLayoutManager(manager);//添加布局
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemCount = manager.getItemCount();//全部的子控件个数
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();//最后一个可见的控件的下标
                //int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();//第一个可见
                if (oldLastFirstVisibleItemPosition != lastVisibleItemPosition) {
                    ReadImgData readImgData = list.get(lastVisibleItemPosition);//获取最后一个可见的图片信息
                    presenterLayter.upDataChapter(readImgData.getChapterId(), readImgData.getChapterNumber());//更新阅读进度
                    StringBuffer buffer = new StringBuffer();//拼接显示章节和阅读进度
                    buffer.append("第")
                            .append((readImgData.getNumber()+1))
                            .append("话  ")
                            .append((readImgData.getChapterNumber() + 1))
                            .append("/")
                            .append(readImgData.getChapterAll());

                    tv_progress.setText(buffer.toString());//设置阅读章节的进度信息
                    oldLastFirstVisibleItemPosition = lastVisibleItemPosition;//更新阅读章节
                }
                if (isDownLoad && itemCount < lastVisibleItemPosition + 10) {
                    if (number < chapterList.size()) {
                        //获取章节漫画信息
                        presenterLayter.getHtmlData(chapterList.get(number).getChapterPath(), ""
                                , SpUtils.getString(ReadActivity.this, getString(R.string.sp_website)), dataId);
                    }
                    isDownLoad = false;//当前有章节在下载中，禁止重复下载
                }
            }
        });
    }

    @Override
    public void showData(PictureEntity obj, String msg, long id) {
        if (obj != null) {
            List<String> listImg = obj.getListImg();
            for (int i = 0; i < listImg.size(); i++) {
                ReadImgData readImgData = new ReadImgData();//阅读图片信息
                readImgData.setChapterId(obj.getChapterId());//获取获取章节 id
                readImgData.setChapterNumber(i);//获取图片的第几张
                readImgData.setChapterAll(obj.getTotal());//获取共章节多少张图片
                readImgData.setChapterImg("");//获取图片的连接
                readImgData.setIsOk(0);//正在下载为0，失败为-1，成功为1
                readImgData.setNumber(number);//章节
                presenterLayter.getImgData(//下载图片
                        listImg.get(i)
                        , obj.getName() + i + obj.getChapterId() + obj.getChapter(),//图片名称
                        //SpUtils.getString(ReadActivity.this, getString(R.string.sp_website))//网站
                        getWebsite()//获取当前数据来源的网站
                        , list.size());
                list.add(readImgData);//添加图片信息到recyclerView准备显示
            }
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();//刷新显示
                //判断是否第一次进入，跳转到上次阅读的位置
                if (readProgress > 0 && isFirst && readProgress < list.size() - 1) {
                    recyclerView.scrollToPosition(readProgress);//滚动到指定位置
                }
                isFirst = false;//不是第一次进入了
                number++;
            });
        } else {
            if (list.size() > id&&id!=dataId) {//图片下载完成
                list.get((int) id).setChapterImg(msg);//图片地址
                list.get((int) id).setIsOk(1);//下载成功
                runOnUiThread(() -> {
                    adapter.notifyItemChanged((int) id);//刷新显示
                });
            }
        }
    }

    @Override
    public void showFailed(String msg, long id) {
        super.showFailed(msg, id);
        if (id == dataId)
            runOnUiThread(() -> {
                Toast.makeText(ReadActivity.this, msg, Toast.LENGTH_SHORT).show();//提示错误信息
            });
    }

    @Override
    public void showError(long id) {
        super.showError(id);
        if (list.size() > id&&id!=dataId) {
            list.get((int) id).setIsOk(-1);//将错误的设置为下载错误
        }
    }

    private void showDate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_time.setText(SystemUtils.time_HHmm());
                if (isUpData) {
                    handler.postDelayed(this, 999);
                }
            }
        }, 0);
    }

    @Override
    public void showEnd(String msg, long id) {
        super.showEnd(msg, id);
        if (id == dataId) {
            isDownLoad = true;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDownLoad = false;
        isDownLoad = false;
        presenterLayter.closeThread();
        presenterLayter.detachView();
    }

}
