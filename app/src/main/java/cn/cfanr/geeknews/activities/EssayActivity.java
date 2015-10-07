package cn.cfanr.geeknews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.view.ObservableWebView;
import cn.cfanr.geeknews.view.WebViewController;


public class EssayActivity extends BaseActivity implements ObservableWebView.OnScrollChangedCallback {
    private String url;
    private String title;
    public ProgressBar bar;
    private ObservableWebView mWebView;
    private WebViewController mWebViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fragment_browse, null);
        setContentView(view);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        title = extras.getString("title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView(view);
    }

    private void initView( View view) {

        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        mWebViewController = new WebViewController(this);

        mWebView = (ObservableWebView) findViewById(R.id.web_view);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setOnScrollChangedCallback(this);
        mWebViewController.initControllerView(mWebView, view);
        mWebViewController.loadUrl(url);
    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {

    }

    @Override
    public void onScrollDown() {
        mWebViewController.hideBrowseBar();
    }

    @Override
    public void onScrollUp() {
        mWebViewController.showBrowseBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_essay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            //监听ActionBar的返回按钮，退出当前Activity（不是跳转否则会再次执行刷新信息列表）
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                shareEssay();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     */
    private void shareEssay() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getShareContent());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private String getShareContent() {
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("  ").append(url);
        return builder.toString();
    }

}
