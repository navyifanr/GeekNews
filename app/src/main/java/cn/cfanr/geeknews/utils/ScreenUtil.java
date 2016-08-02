package cn.cfanr.geeknews.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import cn.cfanr.geeknews.app.AppController;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc 屏幕工具类
 */
public class ScreenUtil {
	private int widthDip; // 屏幕宽度的dip
	private int heightDip; // 屏幕长度的dip
	private int screenWidth; // 获取屏幕的px
	private int screenHeight; // 获取屏幕的px
	private Context context= AppController.getInstance();

	public int getWidthDip() {
		return widthDip;
	}

	public int getHeightDip() {
		return heightDip;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public  ScreenUtil(){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		widthDip = px2dip(screenWidth);
		heightDip = px2dip(screenHeight);
	}

	public  ScreenUtil(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		widthDip = px2dip(screenWidth);
		heightDip = px2dip(screenHeight);
	}

	public int dip2px(double dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2dip(double pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public int getAppBarHeight() {
		return dip2px(56)+getStatusBarHeight();
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
