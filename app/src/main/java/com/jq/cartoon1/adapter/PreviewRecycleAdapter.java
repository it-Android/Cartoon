package com.jq.cartoon1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jq.cartoon1.R;
import com.jq.cartoon1.adapter.javaBean.PreviewImgData;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.utils.SystemUtils;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 23:58
 **/
public class PreviewRecycleAdapter extends RecyclerView.Adapter<PreviewRecycleAdapter.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<PreviewImgData> list;
    private int number=3;
    private int[] screen=new int[2];

    private RecycleViewLisitenter.onItemClickLisitenter onItemClickLisitenter;

    public PreviewRecycleAdapter(List<PreviewImgData> list) {
        this.list = list;
    }

    public void setOnItemClickLisitenter(RecycleViewLisitenter.onItemClickLisitenter onItemClickLisitenter) {
        this.onItemClickLisitenter = onItemClickLisitenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
            screen= SystemUtils.getScreen(context);
            //Log.e("屏幕",screen[0]+"");
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_preview_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviewImgData imgData=list.get(position);
        holder.tv_chapter.setText(imgData.getChapterName());//章节名称
        holder.tv_name.setText(imgData.getName());//名称
        holder.tv_time.setText(imgData.getTime());//时间
        Glide.with(context).load(imgData.getImgPath()).into(holder.imageView);//图片
        holder.cardView.setTag(position);
        int w=(screen[0]-((number*2)*10))/number;
        int h= (int) (w/0.75);
        holder.params.width=w;//设置图片的宽
        holder.params.height=h;//设置高
        holder.imageView.setLayoutParams(holder.params);//添加设置
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //点击事件监听
    @Override
    public void onClick(View v) {
        if(onItemClickLisitenter!=null){
            onItemClickLisitenter.onItemClick(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_time,tv_name,tv_chapter;
        ViewGroup.LayoutParams params;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recycle_item_preview_img);//促初始化图片显示
            tv_time=itemView.findViewById(R.id.recycle_item_preview_time);//时间
            tv_name=itemView.findViewById(R.id.recycle_item_preview_name);//名称
            tv_chapter=itemView.findViewById(R.id.recycle_item_preview_chapter);//
            cardView=itemView.findViewById(R.id.recycle_item_preview_cardView);//
            params=imageView.getLayoutParams();
        }
    }

}
