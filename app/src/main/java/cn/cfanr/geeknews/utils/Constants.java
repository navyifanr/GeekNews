package cn.cfanr.geeknews.utils;

public abstract class Constants {

    /**
     * abs class
     */
    protected Constants() {

    }

    public static final String PRE_GEEKNEWS_APP = "geeknews_app";

    /**
     * Root Dir used by GN app
     */
    public static final String SDCARD_TOP_DIR = "/GN/";

    /**
     * Crash log dir
     */
    public static final String CRASH_LOG_DIR = SDCARD_TOP_DIR+"/crash/";

    /**
     * 历史记录保存文件名
     */
    public static final String HISTORY_FILE_NAME = "history.db";

    public final class IntentAction {

        /**
         * 登陆后结果cookie user
         *
         * @see #ACTION_LOGIN
         */
        public static final String EXTRA_LOGIN_USER = "geeknews.intent.extra.login.USER";

        /**
         * 登陆结果
         * <p>
         * 输出：登陆后的cookie extras:LOGIN_USER(value:String)
         * </p>
         *
         * @see #EXTRA_LOGIN_USER
         */
        public static final String ACTION_LOGIN = "geeknews.intent.action.LOGIN";

        /**
         * 注销
         */
        public static final String ACTION_LOGOUT = "geeknews.intent.action.LOGOUT";

    }

    public static final String TAG_BROWSE_FRAGMENT = "tag_browse_fragment";


}
