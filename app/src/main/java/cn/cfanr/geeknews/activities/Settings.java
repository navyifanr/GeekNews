package cn.cfanr.geeknews.activities;

import android.os.Bundle;

import cn.cfanr.geeknews.R;

public class Settings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mToolbar.setTitle("设置");
    }

}
