package cn.cfanr.geeknews.activities;

import android.content.Intent;
import android.os.Build;
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
import cn.cfanr.geeknews.fragment.MainFragment;
import cn.cfanr.geeknews.parser.utils.Constants;
import cn.cfanr.geeknews.utils.DensityUtil;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private int[] newsType = {Constants.NEWS_TYPE_HOTTEST, Constants.NEWS_TYPE_NEWEST,
            Constants.NEWS_TYPE_ANDROID, Constants.NEWS_TYPE_IOS,Constants.NEWS_TYPE_FRONT_END};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        ButterKnife.bind(this);
        initToolbar();
        initTabLayout();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("GeekNews");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = DensityUtil.getAppBarHeight(this);
            mToolbar.setPadding(mToolbar.getPaddingLeft(), DensityUtil.getStatusBarHeight(this),
                    mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initTabLayout() {
        List<String> tabList = new ArrayList<>();
        tabList.add("热门");
        tabList.add("最新");
        tabList.add("Android");
        tabList.add("iOS");
        tabList.add("前端");
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(4)));

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            MainFragment mainFragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("newsType", newsType[i]);
            mainFragment.setArguments(bundle);
            fragmentList.add(mainFragment);
        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
