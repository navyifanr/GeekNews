package cn.cfanr.geeknews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.app.AppController;
import cn.cfanr.geeknews.data.NewsItem;

/**
 * Created by ifanr on 2015/5/3.
 */
public class NewsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<NewsItem> mDatas;

    public NewsListAdapter(Context context, List<NewsItem> datas) {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);

    }

    public void addAll(List<NewsItem> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    public void setDatas(List<NewsItem> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.news_item, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindData(mDatas, position);
        return convertView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView name;
        @InjectView(R.id.tv_time)
        TextView time;
        @InjectView(R.id.tv_title)
        TextView title;
        @InjectView(R.id.tv_like)
        TextView likeNum;
        @InjectView(R.id.tv_comment)
        TextView commentNum;
        @InjectView(R.id.tv_website)
        TextView hostName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void bindData(List<NewsItem> data, int position) {
            if (data != null) {
                NewsItem newsItem = data.get(position);

                name.setText(newsItem.getUserName());
                time.setText(newsItem.getTime());
                title.setText(newsItem.getTitle());
                /**
                 * 此处判断文章是否已读
                 */
                int textColor = AppController.getmInstance().isHistoryContains(
                        newsItem.getLink()) ? Color.GRAY : Color.BLACK;
                title.setTextColor(textColor);

                int likes=newsItem.getLikeNum();
                if(likes>1){
                    likeNum.setText(likes+" points");
                }else {
                    likeNum.setText(likes + " point");
                }
                int comments=newsItem.getCommentNum();
                if(comments>1){
                    commentNum.setText(comments+" comments");
                }else if(comments==1){
                    commentNum.setText(comments+" comment");
                }else if(comments==0){
                    commentNum.setText("no comments");
                }
                hostName.setText(newsItem.getHostName());
            }
        }
    }
}
