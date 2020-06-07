package com.jq.cartoon1.mvp;

import com.jq.cartoon1.screen.PictureInterpreting;
import com.jq.cartoon1.screen.javaBean.PictureEntity;
import com.jq.cartoon1.screen.sql.ChapterTable;
import com.jq.cartoon1.screen.sql.DetailedTable;
import com.jq.cartoon1.mvp.base.BasePresenterLayer;

import java.io.File;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/29 10:26
 **/
public class ReadPresenterLayter extends BasePresenterLayer<MvpViewLayer<PictureEntity>> {

    private String cartoonId;//漫画id

    public ReadPresenterLayter(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    /**
     * get下载器
     *
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
                    PictureEntity picture = PictureInterpreting.getPicture(data, params, website);//解析图片信息
                    if (picture == null) {
                        getView().showFailed("章节信息获取失败！", id);
                    }
                    getView().showData(picture, msg, id);
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

    public void getImgData(String params, String msg, String website, long id) {
        if (!isViewAttached()) {
            return;
        }
        String filePath = getView().getContext().getCacheDir().getPath() + File.separator + "图片缓存";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filePath += File.separator + msg;
        getView().showStart(filePath, id);
        getThread().executeGetFile(params, filePath, id, new MvpCallback<String>() {
            @Override
            public void onSuccess(String data, String msg, long id) {
                if (isViewAttached()) {
                    try {
                        getView().showData(null, msg, id);
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

    private ChapterTable chapterTable;//章节信息
    private DetailedTable detailedTable;//详细信息

    /**
     * 更新章节信息
     *
     * @param chapterId       章节id
     * @param pictureProgress 图片阅读进度
     */
    public void upDataChapter(String chapterId, int pictureProgress) {
        if (!isViewAttached()) {
            return;
        }
        if (chapterTable == null) {
            chapterTable = new ChapterTable(getView().getContext(), getWebsite(), cartoonId);
        }
        getThread().executeOther(new Runnable() {
            @Override
            public void run() {
                if (pictureProgress == 1 || pictureProgress == 5) {
                    chapterTable.upDataByChapterId(chapterId, true, pictureProgress);//保存是否阅读了该章节，该章节阅读进度
                    upDataDetailed(chapterTable.getByChapterId(chapterId).getId());//保存阅读到第几章节
                } else {
                    chapterTable.upDataProgressByChapterId(chapterId, pictureProgress);
                }
            }
        });
    }

    public void upDataDetailed(int progress) {
        if (!isViewAttached()) {
            return;
        }
        if (detailedTable == null) {
            detailedTable = new DetailedTable(getView().getContext(), getWebsite());
        }
        getThread().executeOther(() -> {
            detailedTable.upDataProgress(cartoonId, progress);
        });
    }

}
