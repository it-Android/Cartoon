package com.jq.cartoon1.mvp;

import com.jq.cartoon1.adapter.javaBean.CollectImgData;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;
import com.jq.cartoon1.screen.sql.BasicTable;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.mvp.base.BasePresenterLayer;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/30 0:43
 **/
public class F_CollectPresenterLayter extends BasePresenterLayer<MvpViewLayer<CollectImgData>> {
    private BasicTable basicTable;

    /**
     * 使用线程来获取章节信息判断是否有更新
     * @param cartoonId 漫画id
     * @param progress 进度(下标)
     * @param isRead 是否阅读
     * @param msg
     * @param id
     */
    public void getChapter(String cartoonId,int progress,Boolean isRead,String msg,int id){
        if(!isViewAttached()){
            return;
        }
        if(basicTable==null){
            basicTable=new BasicTable(getView().getContext(),getWebsite());//初始化基本信息及表
        }
        getView().showStart(msg,id);
        getThread().executeOther(()->{
            ChapterTable chapterTable=new ChapterTable(getView().getContext(),getWebsite(),cartoonId);//获取章节信息表数据
            BasicEntity basicByID = basicTable.getBasicByID(cartoonId);//获取基本信息
            CollectImgData collectImgData=new CollectImgData();//收藏页面显示的图片信息
            collectImgData.setCartoonId(cartoonId);//漫画id
            collectImgData.setPath(basicByID.getPath());//连接
            collectImgData.setImgPath(basicByID.getImgPath());//封面连接
            collectImgData.setAllChapter(chapterTable.getChapter().size());//全部章节
            collectImgData.setName(basicByID.getName());//漫画名称
            collectImgData.setProgress(progress);//进度信息
            collectImgData.setRead(isRead);//是否阅读
            if(isViewAttached()){
                getView().showData(collectImgData,msg,id);//返回漫画信息
                getView().showEnd(msg,id);
            }
        });
    }
}
