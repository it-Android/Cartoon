package com.jq.cartoon1.mvp.base;

import android.content.Context;

import com.jq.cartoon1.R;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.ChapterEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;
import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.screen.sql.BasicTable;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.utils.SpUtils;
import com.jq.cartoon1.utils.ThreadUtils;

import java.util.List;
import java.util.Vector;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:41
 **/
public class BasePresenterLayer<V extends BaseViewLayer> {
    private V baseViewLayer;
    private ThreadUtils threadUtils;
    //绑定view
    public void attachView(V baseViewLayer){
        this.baseViewLayer=baseViewLayer;
        this.threadUtils=new ThreadUtils();
    }

    //断开view
    public void detachView() {
        this.baseViewLayer = null;
        this.threadUtils.closeAllThread();
        this.threadUtils=null;
    }

    //是否连接成功
    public boolean isViewAttached(){
        return baseViewLayer != null||getView()!=null;
    }

    /**
     * 获取连接的view
     */
    public V getView(){
        return baseViewLayer;
    }

    public ThreadUtils getThread(){
        return this.threadUtils;
    }

    public void closeThread(){
        threadUtils.closeAllThread();
    }

    public BasicEntity gteBasicEntity(DetailedEntity detailedEntity) {
        BasicEntity basicEntity = new BasicEntity();
        basicEntity.setId(detailedEntity.getId());
        basicEntity.setCartoonId(detailedEntity.getCartoonId());
        basicEntity.setImgPath(detailedEntity.getImgPath());
        basicEntity.setName(detailedEntity.getName());
        basicEntity.setPath(detailedEntity.getPath());
        basicEntity.setNewest(detailedEntity.getLastTimeChapter());
        basicEntity.setNewTime(detailedEntity.getLastTime());
        return basicEntity;
    }

    public BaseDetailed getBaseDetailed(DetailedEntity detailedEntity) {
        BaseDetailed baseDetailed = new BaseDetailed();
        baseDetailed.setId(detailedEntity.getId());//id
        baseDetailed.setCartoonId(detailedEntity.getCartoonId());//作者
        baseDetailed.setAuthor(detailedEntity.getAuthor());//作者
        baseDetailed.setClassify(detailedEntity.getClassify());//分类
        baseDetailed.setCollection(detailedEntity.getCollection());//收藏
        baseDetailed.setIntroduce(detailedEntity.getIntroduce());//介绍
        baseDetailed.setProgress(detailedEntity.getProgress());//进度
        baseDetailed.setRegion(detailedEntity.getRegion());//地区
        baseDetailed.setState(detailedEntity.getState());//漫画进度
        baseDetailed.setTime(System.currentTimeMillis());//最后阅读时间
        baseDetailed.setWebsite(detailedEntity.getWebsite());//来源网站
        return baseDetailed;
    }

    public List<BaseChapter> getBaseChapter(DetailedEntity detailedEntity) {
        List<BaseChapter> list = new Vector<>();
        for (ChapterEntity chapterEntity : detailedEntity.getAllChapter()) {
            BaseChapter baseChapter = new BaseChapter();
            baseChapter.setId(chapterEntity.getId());//漫画下标
            baseChapter.setChapterId(chapterEntity.getChapterId());//章节id
            baseChapter.setChapterCheck(chapterEntity.getChapterCheck());//是否阅读完毕
            baseChapter.setChapterName(chapterEntity.getChapterName());//章节名称
            baseChapter.setProgress(chapterEntity.getProgress());//章节阅读进度
            baseChapter.setChapterPath(chapterEntity.getChapterPath());//章节连接
            list.add(baseChapter);
        }
        return list;
    }

    /**
     * 保存更新信息
     * @param detailedEntity
     */
    public void saveData(DetailedEntity detailedEntity) {
        Context context = getView().getContext();
        BasicTable basicTable = new BasicTable(context,getWebsite());//基本数据表
        DetailedTable detailedTable = new DetailedTable(context,getWebsite());//详细信息表
        ChapterTable chapterTable = new ChapterTable(context,getWebsite(), detailedEntity.getCartoonId());//章节信息表
        if (basicTable.getBasicByID(detailedEntity.getCartoonId()) == null) {
            basicTable.addData(gteBasicEntity(detailedEntity));//保存漫画的基本信息
            detailedTable.addData(getBaseDetailed(detailedEntity));//保存漫画的详细信息
            chapterTable.addData(getBaseChapter(detailedEntity));//保存漫画的章节信息
        } else {
            List<BaseChapter> chapterList = chapterTable.getChapter();//本地数据库数据
            List<BaseChapter> baseChapter = getBaseChapter(detailedEntity);//网络数据
            if (chapterList.size() != baseChapter.size()) {//循环遍历移除公共部分
                for (BaseChapter chapter : chapterList) {
                    for (int i = 0; i < baseChapter.size(); i++) {
                        if (chapter.getChapterId() == baseChapter.get(i).getChapterId()) {
                            baseChapter.remove(i);
                            break;
                        }
                    }
                }
                if (baseChapter.size() != 0) {
                    chapterTable.addData(baseChapter);//保存漫画的章节信息
                }
            }
            //detailedTable.upDataTime(detailedEntity.getId(), System.currentTimeMillis());//修改时间
            detailedTable.upDataState(detailedEntity.getCartoonId(),detailedEntity.getState(),System.currentTimeMillis());
        }
    }

    public String getWebsite(){
        String website= SpUtils.getString(getView().getContext(),getView().getContext().getString(R.string.sp_website));
        if(website.equals("")){
            return Website.MHK;
        }
        return website;
    }
}
