package cn.cfanr.geeknews.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.utils.DensityUtil;

/**
 * @author xifan
 * @time 2016/5/19
 * @desc
 */
public abstract class BaseBarActivity extends BaseActivity {
    private Toolbar mToolbar;
    private FrameLayout mRootContent;
    public View layoutView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setBaseContentView(int layoutResId) {
        super.setContentView(R.layout.activity_base);
        mRootContent=$(R.id.base_content_root);
        layoutView = LayoutInflater.from(this).inflate(layoutResId, null);
        mRootContent.addView(layoutView);
        initToolbar();
    }

    private void initToolbar(){
        mToolbar=$(R.id.toolbar_base);
        mToolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.getLayoutParams().height = DensityUtil.getAppBarHeight(this);
            mToolbar.setPadding(mToolbar.getPaddingLeft(), DensityUtil.getStatusBarHeight(this), mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }
}
