package com.jq.cartoon1.mvp;

import android.util.Log;

import com.jq.cartoon1.screen.DetailedInterpreting;
import com.jq.cartoon1.screen.SearchInterpreting;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;
import com.jq.cartoon1.screen.javaBean.base.BaseDetailed;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.mvp.base.BasePresenterLayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 11:49
 **/
public class F_SearchPresenterLayer extends BasePresenterLayer<MvpViewLayer> {

    /**
     * get下载器
     * @param params
     * @param msg
     * @param website
     * @param id
     */
    public void getHtmlData(String params, String msg, String website, long id) {
        if (!isViewAttached()) {
            return;
        }
        getView().showStart(msg, id);
        getThread().executeGetString(params, msg, id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data, String msg, long id) {
                if (isViewAttached()) {
                    switch ((int) id) {
                        case 10:
                            SearchAllEntity searchAll = SearchInterpreting.getSearchAll(data, website);//解析搜索数据
                            if(searchAll==null){
                                getView().showFailed("无搜索结果",id);
                                return;
                            }
                            getView().showData(searchAll, msg, id);
                            break;
                        case 11:
                            DetailedEntity detaile = DetailedInterpreting.getDetaile(data, params,website);//解析详细漫画数据
                            if(detaile==null){
                                getView().showFailed("没有漫画信息",id);
                                return;
                            }
                            saveData(detaile);
                            getView().showData(detaile, msg, id);
                            break;
                    }
                }
            }

            @Override
            public void onFailed(String msg, long id) {
                if (isViewAttached()) {
                    getView().showFailed(msg, id);
                }
            }

            @Override
            public void onError(long id) {
                if (isViewAttached()) {
                    getView().showError(id);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getView().showEnd(msg, id);
                }
            }
        });
    }

    public void getHtmlData(String params, String msg, String website, String cartoonId, long id) {
        if (!isViewAttached()) {
            return;
        } else {
            long timeMillis = System.currentTimeMillis();//获取当前系统时间13位的long类型
            DetailedTable detailedTable = new DetailedTable(getView().getContext(), getWebsite());//获取基本表操作类
            BaseDetailed baseDetailed = detailedTable.getByCartoonId(cartoonId);//根据漫画id获取数据
            if (baseDetailed == null || timeMillis - baseDetailed.getTime() > 15 * 60 * 1000) {//当该漫画id不存在时baseDetailed返回为null
                getHtmlData(params, msg, website, id);
                Log.e("数据监控", "网络获取获取");
            } else {
                getView().showStart(msg, id);
                DetailedEntity detaile = new DetailedEntity();
                detaile.setCartoonId(cartoonId);
                getView().showData(detaile, msg, id);
                getView().showEnd(msg, id);
                //Log.e("数据监控","过短时间重复使用");
            }
        }

    }

}
