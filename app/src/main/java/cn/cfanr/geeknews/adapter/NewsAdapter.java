package cn.cfanr.geeknews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
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

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
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