package cn.cfanr.geeknews.activities;

import android.os.Bundle;

import cn.cfanr.geeknews.R;

public class SettingsActivity extends BaseBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.setting;
    }

    @Override
    public void initView() {
        this.setTitle("设置");
    }

    @Override
    public void initEvent() {

    }
}
