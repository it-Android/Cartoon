package com.jq.cartoon1.utils;

import android.graphics.BitmapFactory;

import com.jq.cartoon1.mvp.MvpCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 、
 * 线程工具类
 *
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/25 0:22
 **/
public class ThreadUtils {
    private ExecutorService downloadFileThread;//网络资源文件下载
    private ExecutorService downloadStringThread;//网络资源字符串下载
    private ExecutorService otherThread;



    public void openDownloadFile() {
        if (downloadFileThread == null) {
            downloadFileThread = Executors.newFixedThreadPool(4);
        }
    }

    public void closeDownloadFile() {
        if (downloadFileThread != null) {
            downloadFileThread.shutdown();
            downloadFileThread = null;
        }
    }

    public void openOther() {
        if (otherThread == null) {
            otherThread = Executors.newFixedThreadPool(5);
        }
    }

    public void closeOther() {
        if (otherThread != null) {
            otherThread.shutdown();
            otherThread = null;
        }
    }

    public void openDownloadString() {
        if (downloadStringThread == null) {
            downloadStringThread = Executors.newFixedThreadPool(3);
        }
    }

    public void closeDownloadString() {
        if (downloadStringThread != null) {
            downloadStringThread.shutdown();
            downloadStringThread = null;
        }
    }

    public void openAllThread() {
        openDownloadFile();
        openDownloadString();
        openOther();
    }

    public void closeAllThread() {
        closeDownloadFile();
        closeDownloadString();
        closeOther();
    }

    public void executeGetString(String param,String msg,long id, MvpCallback<String> mvpCallback){
        openDownloadString();
        downloadStringThread.execute(new Runnable() {
            @Override
            public void run() {
                getHtml(param,msg,id,mvpCallback);
                mvpCallback.onComplete();
            }
        });
    }

    public void executePostString(String param,Map<String, String> map,String msg,long id, MvpCallback<String> mvpCallback){
        openDownloadString();
        downloadStringThread.execute(new Runnable() {
            @Override
            public void run() {
                postHtml(param,msg,id,map,mvpCallback);
                mvpCallback.onComplete();
            }
        });
    }

    public void executeOther(Runnable runnable){
        openOther();
        otherThread.execute(runnable);
    }

    private void postHtml(String urlPath,String msg,long id, Map<String, String> map,MvpCallback<String> mvpCallback) {
        if(mvpCallback==null)return;
        URL url = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        InputStreamReader inr = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader read = null;
        try {
            url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue());
                if (++i != map.size()) {
                    builder.append("&");
                }
            }
            conn.getOutputStream().write(builder.toString().getBytes("UTF-8"));
            if (conn.getResponseCode() == 200) {
                in = conn.getInputStream();
                inr = new InputStreamReader(in, "UTF-8");
                read = new BufferedReader(inr);
                String link = "";
                while ((link = read.readLine()) != null) {
                    buffer.append(link + "\n");
                }
                mvpCallback.onSuccess(buffer.toString(),msg,id);
                return;
            } else {
                mvpCallback.onFailed("服务器数连接异常，返回码"+conn.getResponseCode(),+id);
                return;
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inr != null) {
                try {
                    inr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.connect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    private void  getHtml(String site,String msg,long id,MvpCallback<String> mvpCallback) {
        if(mvpCallback==null)return;//判断接口返回是否为空
        URL url = null;//url请求
        HttpURLConnection conn = null;
        InputStream in = null;//字节流
        InputStreamReader inr = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader read = null;
        try {
            url = new URL(site);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                in = conn.getInputStream();
                inr = new InputStreamReader(in, "UTF-8");
                read = new BufferedReader(inr);
                String link = "";
                while ((link = read.readLine()) != null) {
                    buffer.append(link + "\n");
                }
                mvpCallback.onSuccess(buffer.toString(),msg,id);
                return;
            } else {
                mvpCallback.onFailed("服务器数连接异常，返回码"+conn.getResponseCode(),+id);
                return;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inr != null) {
                try {
                    inr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.connect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeGetFile(String param,String msg,long id, MvpCallback<String> mvpCallback){
        openDownloadFile();
        downloadFileThread.execute(new Runnable() {
            @Override
            public void run() {
                getFile(param,msg,id,mvpCallback);
            }
        });
    }


    /**
     * 获取网络图片数据
     * @param site
     * @param msg  完整的文件路径
     * @param id 返回标识码
     * @param mvpCallback
     */
    private void  getFile(String site,String msg,long id,MvpCallback<String> mvpCallback) {
        if(mvpCallback==null)return;//判断接口返回是否为空
        if(isImage(msg)){
            mvpCallback.onSuccess(site,msg,id);
            mvpCallback.onComplete();
            return;
        }
        URL url = null;//url请求
        HttpURLConnection conn = null;
        InputStream in = null;//字节流
        try {
            url = new URL(site);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(10000);
            if (conn.getResponseCode() == 200) {
                in = conn.getInputStream();
                writeToLocal(msg,in);
                mvpCallback.onSuccess(site,msg,id);
                return;
            } else {
                mvpCallback.onFailed("服务器数连接异常，返回码"+conn.getResponseCode(),+id);
                return;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mvpCallback.onError(id);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.connect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
    }


    public static boolean isImage(String filePath){
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        BitmapFactory.Options options = null;
        if (options == null) options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options); //filePath代表图片路径
        if (options.mCancel || options.outWidth == -1
                || options.outHeight == -1) {
            //表示图片已损毁
            return false;
        }else{
            return true;
        }
    }



}
