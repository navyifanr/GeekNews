package cn.cfanr.geeknews.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.lang.ref.WeakReference;

import cn.cfanr.geeknews.activities.EssayActivity;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.utils.UIUtils;

public class WebViewController implements OnClickListener {
    
    private WeakReference<Activity> mActivityRef;
    
    private WebView mWebView;
    
    private ImageButton mBackButton;
    private ImageButton mForwardButton;
    private ImageButton mReadabilityButton;
    private ImageButton mRefreshButton;
    private ImageButton mWebSiteButton;
    
    private View mToolBar;
    
    private String mCurrentUrl;
    
    public WebViewController(Activity activity){
        mActivityRef = new WeakReference<>(activity);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initControllerView(WebView webView,View view){
        if(webView == null || view == null){
            return;
        }
        mWebView = webView;
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        if (UIUtils.hasHoneycomb()) {  //3.0以上的版本设置缩放按钮隐藏
            settings.setDisplayZoomControls(false);
        }
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mBackButton = (ImageButton) view.findViewById(R.id.browse_back);
        mForwardButton = (ImageButton) view.findViewById(R.id.browse_forward);
        mReadabilityButton = (ImageButton) view.findViewById(R.id.browse_readability);
        mRefreshButton = (ImageButton) view.findViewById(R.id.browse_refresh);
        mWebSiteButton = (ImageButton) view.findViewById(R.id.browse_website);
        
        mToolBar = view.findViewById(R.id.browse_bar);
        
        mBackButton.setOnClickListener(this);
        mForwardButton.setOnClickListener(this);
        mReadabilityButton.setOnClickListener(this);
        mRefreshButton.setOnClickListener(this);
        mWebSiteButton.setOnClickListener(this);
    }
    
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setCurrentUrl(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setCurrentUrl(url);
        }
    }
    
    private void setCurrentUrl(String url) {
        mCurrentUrl = url;
    }
    
    private String getCurrentUrl() {
        return mCurrentUrl; //TextUtils.isEmpty(mCurrentUrl) ? mOriginalUrl : mCurrentUrl;
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Activity activity = mActivityRef.get();
            if (newProgress == 100) {
                ((EssayActivity) activity).bar.setVisibility(View.GONE);
            } else {
                if (View.INVISIBLE == ((EssayActivity) activity).bar.getVisibility()) {
                    ((EssayActivity) activity).bar.setVisibility(View.VISIBLE);
                }
                ((EssayActivity) activity).bar.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mActivityRef.get().setTitle(title);
        }

    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.browse_back:
                back();
                break;
            case R.id.browse_forward:
                forward();
                break;
            case R.id.browse_readability:
                readability();
                break;
            case R.id.browse_refresh:
                refresh();
                break;
            case R.id.browse_website:
                webSite();
                break;
            default:
                break;
        }

    }

    private void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    private void forward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }

    private void readability() {
        if(TextUtils.isEmpty(mCurrentUrl)){
            return;
        }
        mWebView.loadUrl("http://www.readability.com/m?url=" + getCurrentUrl());
    }

    private void refresh() {
        mWebView.reload();
    }

    private void webSite() {
        // 打开原链接，还是转码的链接呢？
        if(TextUtils.isEmpty(mCurrentUrl)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getCurrentUrl()));
        mActivityRef.get().startActivity(intent);
    }
    
    public WebView getWebView(){
        return mWebView;
    }
    
    public void loadUrl(String url){
        if(TextUtils.isEmpty(url) || url.equals(mCurrentUrl)){
            return;
        }
        mWebView.clearHistory();
        mWebView.loadUrl(url);
    }

    public void showBrowseBar(){
        if(mToolBar != null){
            Animation animation = AnimationUtils.loadAnimation(mActivityRef.get(), R.anim.push_up_in);
            mToolBar.startAnimation(animation);
            mToolBar.setVisibility(View.VISIBLE);
        }
    }
    
    public void hideBrowseBar(){
        if(mToolBar != null){
            Animation animation = AnimationUtils.loadAnimation(mActivityRef.get(), R.anim.push_down_out);
            mToolBar.startAnimation(animation);
            mToolBar.setVisibility(View.GONE);
        }
    }
    
}
