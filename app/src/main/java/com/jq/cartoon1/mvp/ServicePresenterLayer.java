package com.jq.cartoon1.mvp;

import android.util.Log;

import com.jq.cartoon1.R;
import com.jq.cartoon1.screen.DetailedInterpreting;
import com.jq.cartoon1.screen.javaBean.BasicEntity;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;
import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;
import com.jq.cartoon1.screen.javaBean.base.Website;
import com.jq.cartoon1.screen.sql.BasicTable;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.mvp.base.BasePresenterLayer;
import com.jq.cartoon1.utils.SpUtils;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:49
 **/
public class ServicePresenterLayer extends BasePresenterLayer<MvpViewLayer> {

    /**
     * 更新收藏
     */
    public void upDataCollect() {
        if (!isViewAttached()) {
            return;
        }
        DetailedTable detailedTable = new DetailedTable(getView().getContext(), getWebsite());//详细信息表
        BasicTable basicTable = new BasicTable(getView().getContext(), getWebsite());//基本信息表
        List<BaseDetailed> collection = detailedTable.getCollection();//获取全部的收藏漫画
        for (int i = 0; i < collection.size(); i++) {
            BaseDetailed baseDetailed = collection.get(i);//获取单个漫画的详细信息
            int finalI = i;
            if (isViewAttached()) {
                //开启线程检查漫画更新
                getThread().executeOther(() -> {
                    BasicEntity basicByID = basicTable.getBasicByID(baseDetailed.getCartoonId());//获取指定漫画id的数据
                    getThread().executeGetString(basicByID.getPath(), "", finalI, new MvpCallback<String>() {
                        @Override
                        public void onSuccess(String data, String msg, long id) {
                            if (isViewAttached()) {
                                DetailedEntity detailedEntity = DetailedInterpreting.getDetaile(data,
                                        basicByID.getPath(), SpUtils.getString(getView().getContext(), getView().getContext().getString(R.string.sp_website)));//解析出来网站漫画的全部信息
                                if (detailedEntity == null) {
                                    getView().showFailed("更新失败,没有找到漫画", id);
                                    return;
                                }


                                ChapterTable chapterTable = new ChapterTable(getView().getContext(), getWebsite(), detailedEntity.getCartoonId());//获取本地保存的漫画章节信息表操作类
                                List<BaseChapter> chapterList = chapterTable.getChapter();//全部本地章节信息
                                if (chapterList.size() == detailedEntity.getAllChapter().size()) {
                                    Log.e("更新监控", "漫画 " + detailedEntity.getCartoonId() + " 没有章节更新");
                                } else {
                                    List<BaseChapter> baseChapter = getBaseChapter(detailedEntity);//网络数据
                                    for (BaseChapter chapter : chapterList) {
                                        for (int i = 0; i < baseChapter.size(); i++) {
                                            if (chapter.getChapterId().equals(baseChapter.get(i).getChapterId())) {
                                                baseChapter.remove(i);//移除网络数据中公共部分
                                                break;
                                            }
                                        }
                                    }
                                    if (baseChapter.size() != 0) {
                                        //修改数据库
                                        chapterTable.addData(baseChapter);//保存漫画的章节信息
                                        detailedTable.upDataTime(detailedEntity.getCartoonId(), System.currentTimeMillis());//修改时间
                                        detailedTable.upDataUpData(detailedEntity.getCartoonId(), true);//设置有更新
                                        if (isViewAttached()) {
                                            getView().showData(detailedEntity.getCartoonId(), "collect", 100);//漫画有更新
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailed(String msg, long id) {
                            if (isViewAttached()) {
                                getView().showFailed("collect", 100);
                            }
                        }

                        @Override
                        public void onError(long id) {
                            if (isViewAttached()) {
                                getView().showError(100);
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (isViewAttached()) {
                                getView().showEnd("collect", 100);
                            }
                        }
                    });
                });
            }

        }
    }

    //清理历史记录
    public void closeHist() {
        if (!isViewAttached()) {
            return;
        }
        long deleteTime = SpUtils.getLong(getView().getContext(), getView().getContext().getString(R.string.sp_closeTime));//获取删除的时间间隔
        for (String website : Website.ALL_WEBSITE) {
            getThread().executeOther(() -> {
                long closeTime = System.currentTimeMillis() - deleteTime;//要删除的时间线
                DetailedTable detailedTable = new DetailedTable(getView().getContext(), website);//获取第一个网站的数据表
                BasicTable basicTable = new BasicTable(getView().getContext(), website);
                List<String> deleteCartoonId = detailedTable.getDeleteCartoonId(closeTime);//获取要删除的漫画id
                for (String cartoonId : deleteCartoonId) {
                    ChapterTable chapterTable = new ChapterTable(getView().getContext(), website, cartoonId);
                    chapterTable.deleteTable();//删除漫画章节信息
                    basicTable.delete(cartoonId);//删除基本信息
                }
                detailedTable.deleteTime(closeTime);//删除信息信息
            });

        }
    }
}
