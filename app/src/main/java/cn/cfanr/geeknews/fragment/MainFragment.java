package cn.cfanr.geeknews.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.activities.EssayActivity;
import cn.cfanr.geeknews.adapter.NewsAdapter;
import cn.cfanr.geeknews.app.AppController;
import cn.cfanr.geeknews.dao.NewsItemDao;
import cn.cfanr.geeknews.data.NewsItem;
import cn.cfanr.geeknews.data.NewsItemBiz;
import cn.cfanr.geeknews.parser.exception.EssaySpiderException;
import cn.cfanr.geeknews.parser.utils.Constants;
import cn.cfanr.geeknews.utils.AppUtil;
import cn.cfanr.geeknews.utils.NetUtil;
import cn.cfanr.geeknews.utils.ToastUtil;
import cn.cfanr.geeknews.view.PullLoadMoreRecyclerView;

public class MainFragment extends Fragment {
    @Bind(R.id.pull_load_more_rv)
    PullLoadMoreRecyclerView mRecyclerView;

    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFRESH = 0x111;
    private static final int TIP_ERROR_NO_NETWORK = 0X112;
    private static final int TIP_ERROR_SERVER = 0X113;
    private NewsAdapter mAdapter;
    private NewsItemDao mNewsItemDao;
    private boolean isFirstIn = true;
    private boolean isConnNet = false;
    private boolean isLoadingDataFromNetWork;
    public int newsType = Constants.NEWS_TYPE_HOTTEST;
    private int currentPage = 1;
    private List<NewsItem> newsList = new ArrayList<>();
    private NewsItemBiz newsItemBiz = new NewsItemBiz();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        newsType=bundle.getInt("newsType");
        mNewsItemDao = new NewsItemDao(getActivity());
        mAdapter = new NewsAdapter(getActivity(), newsList);
        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsItem newsItem = newsList.get(position);
                Intent intent = new Intent(getActivity(), EssayActivity.class);
                intent.putExtra("url", newsItem.getLink());
                intent.putExtra("title", newsItem.getTitle());
                startActivity(intent);
                AppController.getInstance().addHistory(newsItem.getLink());  //存储已读记录
                mAdapter.notifyDataSetChanged(); //通知适配器修改已读状态
            }
        });
        /**
         * 初始化
         */
        mRecyclerView.setLinearLayout();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                new LoadNewsTask().execute(LOAD_REFRESH);
            }

            @Override
            public void onLoadMore() {
                new LoadNewsTask().execute(LOAD_MORE);
            }
        });

        if (isFirstIn) {  //进来时直接刷新
            mRecyclerView.setRefreshing(true);
            mRecyclerView.refresh();
            isFirstIn = false;
        } else {
            mRecyclerView.setRefreshing(false);
        }
    }

    /**
     * 记载数据的异步任务
     */
    class LoadNewsTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_MORE:
                    loadMoreData();
                    break;
                case LOAD_REFRESH:
                    return refreshData();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case TIP_ERROR_NO_NETWORK:
                    ToastUtil.show(getActivity(), "没有网络连接！");
                    mAdapter.setData(newsList);
                    mAdapter.notifyDataSetChanged();
                    break;
                case TIP_ERROR_SERVER:
                    ToastUtil.show(getActivity(), "服务器错误！");
                    break;
                default:
                    break;
            }
            mRecyclerView.setRefreshing(false);
            mRecyclerView.setPullLoadMoreCompleted();
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 下拉刷新数据
     */
    public Integer refreshData() {
        if (NetUtil.checkNet(getActivity())) {
            isConnNet = true;
            // 获取最新数据
            try {
                List<NewsItem> newsItems = newsItemBiz.getNewsItems(newsType, currentPage);
                mAdapter.setData(newsItems);
                isLoadingDataFromNetWork = true;
                // 设置刷新时间
                AppUtil.setRefreashTime(getActivity(), newsType);
                // 清除数据库数据
                mNewsItemDao.deleteAll(newsType);
                // 存入数据库
                mNewsItemDao.add(newsItems);
            } catch (EssaySpiderException e) {
                e.printStackTrace();
                isLoadingDataFromNetWork = false;
                return TIP_ERROR_SERVER;
            }
        } else {
            Log.e("xxx", "no network");
            isConnNet = false;
            isLoadingDataFromNetWork = false;
            // TODO从数据库中加载
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            newsList = newsItems;
            // mAdapter.setDatas(newsItems);
            return TIP_ERROR_NO_NETWORK;
        }
        return -1;
    }

    /**
     * 会根据当前网络情况，判断是从数据库加载还是从网络继续获取
     */
    public void loadMoreData() {
        // 当前数据是从网络获取的
        if (isLoadingDataFromNetWork) {
            currentPage += 1;
            try {
                List<NewsItem> newsItems = newsItemBiz.getNewsItems(newsType, currentPage);
                mNewsItemDao.add(newsItems);
                mAdapter.addAll(newsItems);
            } catch (EssaySpiderException e) {
                e.printStackTrace();
            }
        } else {  // 从数据库加载的
            currentPage += 1;
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            mAdapter.addAll(newsItems);
        }
    }
}
