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
import com.jq.cartoon1.adapter.javaBean.CollectImgData;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.utils.SystemUtils;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/23 23:58
 **/
public class CollectRecycleAdapter extends RecyclerView.Adapter<CollectRecycleAdapter.ViewHolder> implements View.OnClickListener{
    private Context context;//上下文
    private List<CollectImgData> list;//收藏显示的数据
    private int number=3;//分3份
    private int[] screen=new int[2];//屏幕尺寸，0宽，1高

    private RecycleViewLisitenter.onItemClickLisitenter onItemClickLisitenter;//RecyclerView的item的点击事件
    public CollectRecycleAdapter(List<CollectImgData> list) {//初始化
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
            screen= SystemUtils.getScreen(context);//获取屏幕尺寸
           // Log.e("屏幕",screen[0]+"");
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_collect_item_layout,parent,false);//初始化布局
        ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(this);//设置点击
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollectImgData collectImgData = list.get(position);//获取item需要显示的数据
        Glide.with(context).load(collectImgData.getImgPath()).into(holder.imageView);//封面图片
        holder.tv_name.setText(collectImgData.getName());//名称
        if(collectImgData.getRead()){
            holder.tv_upData.setVisibility(View.VISIBLE);//没阅读就显示
        }else {
            holder.tv_upData.setVisibility(View.GONE);//阅读了就隐藏
        }
        if(collectImgData.getAllChapter()!=0){
            String textData=null;
            //判断是否阅读过漫画
            if(collectImgData.getProgress()==0||collectImgData.getProgress()==-1){
                textData="未阅读";
            }else{
                textData=(collectImgData.getProgress()+1)+"/"+collectImgData.getAllChapter();//阅读记录显示
            }
            holder.tv_progress.setText(textData);//进度设置
        }else{
            holder.tv_progress.setText("暂无数据");//进度设置
        }
        holder.cardView.setTag(position);//保存点击的是第几个控件使用tag来保存
        int w=(screen[0]-((number*2)*10))/number;//获取单个封面图片的宽
        int h= (int) (w/0.75);//获取单个封面图片的高
        holder.params.width=w;//设置宽
        holder.params.height=h;//设置高
        holder.imageView.setLayoutParams(holder.params);//添加设置
    }
    @Override
    public int getItemCount() {
        return list.size();//返回长度，这个的长度就是上面初始化的次数
    }

    //idtm的点击事件
    @Override
    public void onClick(View v) {
        if(onItemClickLisitenter!=null){
            onItemClickLisitenter.onItemClick(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_progress,tv_name, tv_upData;
        ViewGroup.LayoutParams params;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recycle_item_collect_img);//促初始化图片显示
            tv_progress =itemView.findViewById(R.id.recycle_item_collect_tv_progress);//时间
            tv_name=itemView.findViewById(R.id.recycle_item_collect_tv_name);//名称
            tv_upData =itemView.findViewById(R.id.recycle_item_collect_tv_upData);//
            cardView=itemView.findViewById(R.id.recycle_item_collect_cardView);//
            params=imageView.getLayoutParams();
        }
    }

}
