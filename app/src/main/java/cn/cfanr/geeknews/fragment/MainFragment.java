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
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.geeknews.activities.EssayActivity;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.adapter.NewsListAdapter;
import cn.cfanr.geeknews.app.AppController;
import cn.cfanr.geeknews.dao.NewsItemDao;
import cn.cfanr.geeknews.data.NewsItem;
import cn.cfanr.geeknews.data.NewsItemBiz;
import cn.cfanr.geeknews.parser.exception.EssaySpliderException;
import cn.cfanr.geeknews.parser.utils.Constants;
import cn.cfanr.geeknews.utils.AppUtil;
import cn.cfanr.geeknews.utils.NetUtil;
import cn.cfanr.geeknews.utils.ToastUtil;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

public class MainFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore {

    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFREASH = 0x111;

    private static final int TIP_ERROR_NO_NETWORK = 0X112;
    private static final int TIP_ERROR_SERVER = 0X113;

    private XListView mXListView;
    private NewsItemDao mNewsItemDao;
    /**
     * 是否是第一次进入
     */
    private boolean isFirstIn = true;
    /**
     * 是否连接网络
     */
    private boolean isConnNet = false;
    /**
     * 当前数据是否是从网络中获取的
     */
    private boolean isLoadingDataFromNetWork;
    /**
     * 默认的newType
     */
    public int newsType = Constants.NEWS_TYPE_HOTTEST;
    /**
     * 当前页面
     */
    private int currentPage = 1;

    private List<NewsItem> newsData = new ArrayList<>();
    private NewsListAdapter mAdapter;
    private NewsItemBiz newsItemBiz = new NewsItemBiz();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNewsItemDao = new NewsItemDao(getActivity());
        mAdapter = new NewsListAdapter(getActivity(), newsData);
        /**
         * 初始化
         */
        mXListView = (XListView) getView().findViewById(R.id.xlistview);
        mXListView.setAdapter(mAdapter);
        mXListView.setPullRefreshEnable(this);
        mXListView.setPullLoadEnable(this);
        mXListView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newsType));

        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem = newsData.get(position - 1);
                Intent intent = new Intent(getActivity(), EssayActivity.class);
                intent.putExtra("url", newsItem.getLink());
                intent.putExtra("title", newsItem.getTitle());
                startActivity(intent);
                AppController.getInstance().addHistory(newsItem.getLink());  //存储已读记录
                mAdapter.notifyDataSetChanged(); //通知适配器修改已读状态
            }
        });

        if (isFirstIn) {
            /**
             * 进来时直接刷新
             */
            mXListView.startRefresh();
            isFirstIn = false;
        } else {
            mXListView.NotRefreshAtBegin();
        }
    }

    @Override
    public void onRefresh() {
        new LoadDatasTask().execute(LOAD_REFREASH);
    }

    @Override
    public void onLoadMore() {
        new LoadDatasTask().execute(LOAD_MORE);
    }


    /**
     * 记载数据的异步任务
     */
    class LoadDatasTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_MORE:
                    loadMoreData();
                    break;
                case LOAD_REFREASH:
                    return refreashData();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case TIP_ERROR_NO_NETWORK:
                    ToastUtil.show(getActivity(), "没有网络连接！");
                    mAdapter.setDatas(newsData);
                    mAdapter.notifyDataSetChanged();
                    break;
                case TIP_ERROR_SERVER:
                    ToastUtil.show(getActivity(), "服务器错误！");
                    break;

                default:
                    break;

            }

            mXListView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newsType));
            mXListView.stopRefresh();
            mXListView.stopLoadMore();
        }

    }

    /**
     * 下拉刷新数据
     */
    public Integer refreashData() {

        if (NetUtil.checkNet(getActivity())) {

            isConnNet = true;
            // 获取最新数据
            try {
                List<NewsItem> newsItems = newsItemBiz.getNewsItems(newsType, currentPage);
                mAdapter.setDatas(newsItems);

                isLoadingDataFromNetWork = true;
                // 设置刷新时间
                AppUtil.setRefreashTime(getActivity(), newsType);
                // 清除数据库数据
                mNewsItemDao.deleteAll(newsType);
                // 存入数据库
                mNewsItemDao.add(newsItems);

            } catch (EssaySpliderException e) {
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
            newsData = newsItems;
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
            } catch (EssaySpliderException e) {
                e.printStackTrace();
            }
        } else{  // 从数据库加载的
            currentPage += 1;
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            mAdapter.addAll(newsItems);
        }
    }
}
