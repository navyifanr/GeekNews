package cn.cfanr.geeknews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.cfanr.geeknews.parser.utils.Constants;

public class HottestFragment extends MainFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.newsType = Constants.NEWS_TYPE_HOTTEST;
        super.onActivityCreated(savedInstanceState);
    }
}