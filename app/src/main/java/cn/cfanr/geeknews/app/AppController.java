package cn.cfanr.geeknews.app;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import cn.cfanr.geeknews.utils.Constants;


/**
 * Created by ifanr on 2015/5/1.
 */
public class AppController extends Application {

    private static final String LOG_TAG = AppController.class.getSimpleName();
    private HashSet<String> mHistorySet = new HashSet<String>();

    private static AppController mInstance;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "GeekNews thread #" + mCount.getAndIncrement());
        }
    };

    private ExecutorService mExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mExecutorService = Executors.newSingleThreadExecutor(sThreadFactory);
        initHistory();
    }

    public static synchronized AppController getmInstance(){
        return mInstance;
    }

    private void initHistory() {
        File file = new File(getFilesDir().getAbsolutePath() + File.separator
                + Constants.HISTORY_FILE_NAME);
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String s = null;
            while (!TextUtils.isEmpty((s = reader.readLine()))) {
                mHistorySet.add(s);
            }
        } catch (FileNotFoundException e) {
//            EasyTracker.getTracker().sendException("History file not found!", e, false);
        } catch (IOException e) {
//            EasyTracker.getTracker().sendException("Read History file error!", e, false);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void storeHistory() {
        if (!mHistorySet.isEmpty()) {
            Iterator<String> iterator = mHistorySet.iterator();
            StringBuilder builder = new StringBuilder();
            while (iterator.hasNext()) {
                builder.append(iterator.next()).append("\n");
            }
            File file = new File(getFilesDir().getAbsolutePath() + File.separator
                    + Constants.HISTORY_FILE_NAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Create history file error!");
//                    EasyTracker.getTracker().sendException("Create history file error!", e, false);
                }
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
                writer.write(builder.toString());
                writer.close();
            } catch (FileNotFoundException e) {
                Log.e(LOG_TAG, "History file not found!");
//                EasyTracker.getTracker().sendException("History file not found!", e, false);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    /**
     * 增加访问历史记录
     *
     * @param url
     */
    public void addHistory(String url) {
        if (!TextUtils.isEmpty(url) && !mHistorySet.contains(url)) {
            mHistorySet.add(url);
            mExecutorService.submit(new Runnable() {

                @Override
                public void run() {
                    storeHistory();
                }
            });
        }
    }

    /**
     * 清空历史记录
     */
    public void clearHistory() {
        mHistorySet.clear();
    }

    public boolean isHistoryContains(String url) {
        return mHistorySet.contains(url);
    }
}
