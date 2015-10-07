package cn.cfanr.geeknews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.cfanr.geeknews.parser.utils.Constants;


/**
 * Created by neokree on 24/11/14.
 */
public class CollectionFragment extends MainFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.newsType = Constants.NEWS_TYPE_ANDROID;
        super.onActivityCreated(savedInstanceState);
    }
}