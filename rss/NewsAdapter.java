package com.kly.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Item> mNewsList;

    OnNewsClickListener mNewsClickListener;

    public NewsAdapter(Context context,ArrayList<Item> newsList){
        this.mContext=context;
        this.mNewsList=newsList;
    }

    public void setOnNewsClickListener(OnNewsClickListener listener){
        this.mNewsClickListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=mNewsList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        TextView authorTv;
        ImageView imageIv;
        Item mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.tittle_tv);
            authorTv=itemView.findViewById(R.id.author_tv);
            imageIv=itemView.findViewById(R.id.image_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewsClickListener.onNewsClick(mItem);
                }
            });
        }
        public void bind(Item item){
            mItem=item;
            titleTv.setText(item.getTitle());
            authorTv.setText(item.getAuthor());

            //이미지가 있을 때
            if(item.getEnclosure()!=null){
                String url=item.getEnclosure().getUrl();
                Glide.with(mContext).load(url).into(imageIv);
                imageIv.setVisibility(View.VISIBLE);
            }
            //이미지가 없을 때
            else{
                imageIv.setVisibility(View.GONE);
            }
        }
    }
    interface OnNewsClickListener{
        void onNewsClick(Item item);
    }
}
