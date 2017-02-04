package com.zhuoxin.videonews.ui.local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.zhuoxin.videonews.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LocalVideoAdapter extends CursorAdapter {

    //用来加载视频预览图的线程池
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    //用来缓存已经加载过的预览图
    private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(5 * 1024 * 1024) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return super.sizeOf(key, value);
        }
    };

    public LocalVideoAdapter(Context context) {
        super(context, null, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return new LoaclVideoItem(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final LoaclVideoItem item = (LoaclVideoItem) view;
        item.bind(cursor);

        //拿到文件路径
        final String filePath = item.getFilePath();
        //缓存中那预览图
        Bitmap bitmap = lruCache.get(filePath);
        if (bitmap != null) {
            item.setIvPreview(bitmap);
            return;
        }
        //后台线程获取预览图
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //加载视频预览图像
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MICRO_KIND);

                //缓存当前预览图，文件路径为key
                lruCache.put(filePath, bitmap);
                //将预览图设置到控件上（后台线程）
                item.setIvPreview(filePath, bitmap);
            }
        });
    }

    //关闭线程池的方法
    public void release() {
        executorService.shutdown();
    }
}
