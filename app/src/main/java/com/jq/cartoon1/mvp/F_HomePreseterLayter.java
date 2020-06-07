package com.jq.cartoon1.mvp;

import com.jq.cartoon1.mvp.base.BasePresenterLayer;
import com.jq.cartoon1.screen.ClassifyInterpreting;
import com.jq.cartoon1.screen.DetailedInterpreting;
import com.jq.cartoon1.screen.NewInterpreting;
import com.jq.cartoon1.screen.address.CarToonAddress;
import com.jq.cartoon1.screen.javaBean.DetailedEntity;
import com.jq.cartoon1.screen.javaBean.SearchAllEntity;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/31 22:19
 **/
public class F_HomePreseterLayter extends BasePresenterLayer<MvpViewLayer> {


    //全部分类下载器
    private void getHtmlClassifyData(String params, String msg, String website, long id) {
        if (!isViewAttached()) {
            return;
        }
        getView().showStart(msg, id);//开始
        getThread().executeGetString(params, msg, id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data, String msg, long id) {
                if (isViewAttached()) {
                    try {
                        SearchAllEntity classify = ClassifyInterpreting.getClassify(data, params, website);
                        if (classify == null) {
                            getView().showFailed("列表获取失败！", id);
                            return;
                        }
                        getView().showData(classify, msg, id);//
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailed(String msg, long id) {
                if (isViewAttached()) {
                    try {
                        getView().showFailed(msg, id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(long id) {
                if (isViewAttached()) {
                    try {
                        getView().showError(id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    try {
                        getView().showEnd(msg, id);
                    } catch (Exception e) {
                    }
                }
            }
        });

    }

    //小编分类下载器
    private void getHtmlNewData(String params, String msg, String website, int type, long id) {
        if (!isViewAttached()) {
            return;
        }
        getView().showStart(msg, id);//开始
        getThread().executeGetString(params, msg, id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data, String msg, long id) {
                if (isViewAttached()) {
                    switch ((int) id) {
                        case 0:
                            try {
                                SearchAllEntity classify = NewInterpreting.getNewSearchAll(data, params, website, type);
                                if (classify == null) {
                                    getView().showFailed("列表获取失败！", id);
                                    return;
                                }
                                getView().showData(classify, msg, id);//
                            } catch (Exception e) {
                            }
                            break;
                        case 1:
                            DetailedEntity detaile = DetailedInterpreting.getDetaile(data, params, website);//解析详细漫画数据
                            if (detaile == null) {
                                getView().showFailed("漫画不存在", id);
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
                    try {
                        getView().showFailed(msg, id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(long id) {
                if (isViewAttached()) {
                    try {
                        getView().showError(id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    try {
                        getView().showEnd(msg, id);
                    } catch (Exception e) {
                    }
                }
            }
        });

    }

    //整合下载器
    public void getHtmlData(String params, String msg, int downLoadNumber, String website, int type, long id) {
        if (!isViewAttached()) {
            return;
        }
        getView().showStart(msg, id);//开始
        getThread().executeGetString(params, msg, id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data, String msg, long id) {
                if (isViewAttached()) {
                    try {
                        switch ((int) id) {
                            case 0:
                                try {
                                    if (downLoadNumber == 0) {
                                        try {
                                            SearchAllEntity classify = NewInterpreting.getNewSearchAll(data, params, website, type);
                                            if (classify == null) {
                                                getView().showFailed("列表获取失败！", id);
                                                return;
                                            }
                                            getView().showData(classify, msg, id);//
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        try {
                                            SearchAllEntity classify = ClassifyInterpreting.getClassify(data, params, website);
                                            if (classify == null) {
                                                getView().showFailed("列表获取失败！", id);
                                                return;
                                            }
                                            getView().showData(classify, msg, id);//
                                        } catch (Exception e) {
                                        }
                                    }
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    DetailedEntity detaile = DetailedInterpreting.getDetaile(data, params, website);//解析详细漫画数据
                                    if (detaile == null) {
                                        getView().showFailed("漫画不存在", id);
                                        return;
                                    }
                                    saveData(detaile);
                                    getView().showData(detaile, msg, id);
                                } catch (Exception e) {
                                }
                                break;
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailed(String msg, long id) {
                if (isViewAttached()) {
                    try {
                        getView().showFailed(msg, id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(long id) {
                if (isViewAttached()) {
                    try {
                        getView().showError(id);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    try {
                        getView().showEnd(msg, id);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    /**
     * 获取下载数据
     *
     * @param downLoadNumber 下载数据集合
     * @param msg
     * @param website        网站
     * @param id             返回码标识
     */
    public void getHomeData(int[] downLoadNumber, String msg, String website, long id) {
        String path = CarToonAddress.getAddress(website, downLoadNumber);//获取下载连接
        if (downLoadNumber[0] == 0) {//判断是否为小编分类
            if (downLoadNumber[1] == 0)
                getHtmlNewData(path, msg, website, 0, id);//小编分类下载器
            else
                getHtmlNewData(path, msg, website, 1, id);//小编分类下载器
        } else {
            getHtmlClassifyData(path, msg, website, id);//全部分类下载器
        }
    }


}
