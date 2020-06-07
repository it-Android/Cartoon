package com.jq.cartoon1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jq.cartoon1.R;
import com.jq.cartoon1.adapter.javaBean.ReadImgData;
import com.jq.cartoon1.utils.SystemUtils;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/29 0:09
 **/
public class ReadRecycleAdapter extends RecyclerView.Adapter<ReadRecycleAdapter.ViewHolder> {

    private Context context;
    private int[] screen = new int[2];
    private List<ReadImgData> list;

    public ReadRecycleAdapter(List<ReadImgData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
            screen = SystemUtils.getScreen(context);
            //Log.e("屏幕",screen[0]+"");
        }
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_read_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.params.width = screen[0];
        viewHolder.params.height = (int) (screen[1] * 0.7);
        viewHolder.imageView.setLayoutParams(viewHolder.params);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReadImgData readImgData = list.get(position);
        //加载原图的操作
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //跳过磁盘缓存
                //.circleCrop()//圆形
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        Glide.with(context)
                //.asBitmap()
                .load(readImgData.getChapterImg())
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //加载失败时调用
                        holder.params.width = screen[0];//将图片宽设置为屏幕宽
                        double height = (screen[0] * 544) / 306.0;//高以固定比例收缩
                        holder.params.height = (int) height;
                        holder.imageView.setLayoutParams(holder.params);
                        if (holder.textView.getVisibility() != View.VISIBLE) {
                            holder.textView.setVisibility(View.VISIBLE);
                            if (readImgData.getIsOk() == 0) {
                                holder.textView.setText("图片正在下载请稍等！");
                            } else if (readImgData.getIsOk() == -1) {
                                holder.textView.setText("图片地址错误，加载失败！");
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        int w = resource.getIntrinsicWidth();//获取图片的宽
                        int h = resource.getIntrinsicHeight();//获取图片的高
                        holder.params.width = screen[0];//
                        double height = (screen[0] * h) / w;//按比例缩放图片，使图片占满全屏
                        holder.params.height = (int) height;
                        holder.imageView.setLayoutParams(holder.params);
                        holder.imageView.setImageDrawable(resource);
                        if (holder.textView.getVisibility() != View.GONE) {
                            holder.textView.setVisibility(View.GONE);//将文字隐藏
                        }
                        return false;
                    }
                }).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup.LayoutParams params;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycle_item_read_img);
            textView = itemView.findViewById(R.id.recycle_item_read_tv_tip);
            params = imageView.getLayoutParams();
        }
    }
}
