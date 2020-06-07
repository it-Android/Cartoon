package com.jq.cartoon1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jq.cartoon1.R;
import com.jq.cartoon1.myview.RecycleViewLisitenter;
import com.jq.cartoon1.screen.javaBean.base.BaseChapter;

import java.util.List;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/28 16:32
 **/
public class DetailedRecycleAdapter extends RecyclerView.Adapter<DetailedRecycleAdapter.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<BaseChapter> list;
    private RecycleViewLisitenter.onItemClickLisitenter onItemClickLisitenter;

    public DetailedRecycleAdapter(List<BaseChapter> list) {
        this.list = list;
    }

    public void setOnItemClickLisitenter(RecycleViewLisitenter.onItemClickLisitenter onItemClickLisitenter) {
        this.onItemClickLisitenter = onItemClickLisitenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.recycle_detailed_chapter_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaseChapter baseChapter = list.get(position);
        holder.tv_chapterName.setText(baseChapter.getChapterName());
        holder.cb_read.setChecked(baseChapter.getChapterCheck());
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickLisitenter!=null)
        onItemClickLisitenter.onItemClick(v, (Integer) v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;//图
        TextView tv_chapterName;
        CheckBox cb_read;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.recycle_item_detailed_iv_img);
            tv_chapterName=itemView.findViewById(R.id.recycle_item_detailed_tv_chapterName);
            cb_read=itemView.findViewById(R.id.recycle_item_detailed_cb_read);
            cardView=itemView.findViewById(R.id.recycle_item_detailed_cardView);
        }
    }

}
