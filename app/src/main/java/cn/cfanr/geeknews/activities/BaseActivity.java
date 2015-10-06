package cn.cfanr.geeknews.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.cfanr.geeknews.R;
import cn.cfanr.geeknews.app.AppController;
import cn.cfanr.geeknews.utils.DensityUtil;

public class BaseActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;
    private boolean isKitkat=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注意是调用父类的方法，super
        super.setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isKitkat=true;
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        initToolbar();
    }

    private void initToolbar() {
        if (mToolbar != null) {
            if(isKitkat) {
                mToolbar.setPadding(0, DensityUtil.dip2px(this, 25), 0, 0);
            }
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }

    public  void show(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_LONG).show();
        }
    }
}