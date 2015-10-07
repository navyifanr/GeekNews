package cn.cfanr.geeknews.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.adapter.TabFragmentAdapter;
import cn.cfanr.geeknews.fragment.CollectionFragment;
import cn.cfanr.geeknews.fragment.HottestFragment;
import cn.cfanr.geeknews.fragment.NewestFragment;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        initToolbar();
        initTabLayout();
    }

    private void initToolbar(){
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("GeekNews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTabLayout() {
        List<String> tabList = new ArrayList<>();
        tabList.add("热门");
        tabList.add("最新");
        tabList.add("Android");
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(2)));

        List<Fragment> fragmentList = new ArrayList<>();

        HottestFragment hottestFragment=new HottestFragment();
        fragmentList.add(hottestFragment);

        NewestFragment newestFragment=new NewestFragment();
        fragmentList.add(newestFragment);

        CollectionFragment collectionFragment=new CollectionFragment();
        fragmentList.add(collectionFragment);
//        for(int i=0;i<tabList.size();i++) {
//            ScrollFragment scrollFragment = new ScrollFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("tabId", ""+(i+1));
//            scrollFragment.setArguments(bundle);
//            fragmentList.add(scrollFragment);
//        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
    }

}
