package cn.cfanr.geeknews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import cn.cfanr.geeknews.fragment.CollectionFragment;
import cn.cfanr.geeknews.fragment.HottestFragment;
import cn.cfanr.geeknews.fragment.NewestFragment;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_drawer, null);
        setDrawerHeaderCustom(view);

        // create sections
        this.addSection(newSection("最热", new HottestFragment()));
        this.addSection(newSection("最新", new NewestFragment()));
        this.addSection(newSection("收藏", new CollectionFragment()));
//        this.addSection(newSection("Collection",R.drawable.ic_mic_white_24dp,new CollectionFragment()).setSectionColor(Color.parseColor("#9c27b0")));

        this.addBottomSection(newSection("Bottom Section", R.drawable.abc_ic_search_api_mtrl_alpha, new Intent(this, Settings.class)));
    }
}
