package cn.cfanr.geeknews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.cfanr.geeknews.parser.utils.Constants;

public class NewestFragment extends MainFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        super.newsType = Constants.NEWS_TYPE_NEWEST;
    }
}
