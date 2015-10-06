package cn.cfanr.geeknews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ObservableWebView extends WebView {

    private final static int TOUCH_STATE_REST = 0;

    private final static int TOUCH_STATE_SCROLL_UP = 1;

    private final static int TOUCH_STATE_SCROLL_DOWN = 2;

    private int mTouchState = TOUCH_STATE_REST;

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ObservableWebView(final Context context) {
        super(context);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t, oldl, oldt);
            if (t >= 0 && oldt >= 0) {
                if (oldt > t && mTouchState != TOUCH_STATE_SCROLL_UP) {
                    mTouchState = TOUCH_STATE_SCROLL_UP;
                    mOnScrollChangedCallback.onScrollUp();
                } else if (oldt < t && mTouchState != TOUCH_STATE_SCROLL_DOWN) {
                    mTouchState = TOUCH_STATE_SCROLL_DOWN;
                    mOnScrollChangedCallback.onScrollDown();
                }
            }
        }

    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the
     * webview
     */
    public static interface OnScrollChangedCallback {
        public void onScroll(int l, int t, int oldl, int oldt);

        /**
         * 向上滚动
         */
        public void onScrollUp();

        /**
         * 向下滚动
         */
        public void onScrollDown();
    }

}
