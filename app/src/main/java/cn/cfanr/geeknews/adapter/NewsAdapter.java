package cn.cfanr.geeknews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.app.AppController;
import cn.cfanr.geeknews.data.NewsItem;

/**
 * Created by ifanr on 2015/10/3.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsItem> newsList;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsViewHolder holder = new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        NewsItem newsItem = newsList.get(position);

        holder.name.setText(newsItem.getUserName());
        holder.time.setText(newsItem.getTime());
        holder.title.setText(newsItem.getTitle());
        /**
         * 此处判断文章是否已读
         */
        int textColor = AppController.getInstance().isHistoryContains(
                newsItem.getLink()) ? Color.GRAY : Color.BLACK;
        holder.title.setTextColor(textColor);

        int likes=newsItem.getLikeNum();
        if(likes>1){
            holder.likeNum.setText(likes+" points");
        }else {
            holder.likeNum.setText(likes + " point");
        }
        int comments=newsItem.getReadNum();
        if(comments>1){
            holder.commentNum.setText(comments+" page views");
        }else if(comments==1){
            holder.commentNum.setText(comments+" page views");
        }else if(comments==0){
            holder.commentNum.setText("no page views");
        }
        holder.hostName.setText(newsItem.getHostName());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setData(List<NewsItem> newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);
    }

    public void addAll(List<NewsItem> newsList) {
        this.newsList.addAll(newsList);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView name;
        @Bind(R.id.tv_time)
        TextView time;
        @Bind(R.id.tv_title)
        TextView title;
        @Bind(R.id.tv_like)
        TextView likeNum;
        @Bind(R.id.tv_comment)
        TextView commentNum;
        @Bind(R.id.tv_website)
        TextView hostName;

        public NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}