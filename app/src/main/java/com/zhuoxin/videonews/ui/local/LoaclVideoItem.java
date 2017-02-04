package com.zhuoxin.videonews.ui.local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuoxin.videonews.R;
import com.zhuoxin.videonews.videoplayernews.full.VideoViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LoaclVideoItem extends FrameLayout {
    public LoaclVideoItem(Context context) {
        this(context, null);
    }

    public LoaclVideoItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoaclVideoItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.tvVideoName)
    TextView tvVideoName;
    private String filePath;//文件路径

    public String getFilePath() {
        return filePath;
    }

    public void setIvPreview(Bitmap bitmap) {
        ivPreview.setImageBitmap(bitmap);
    }

    //设置预览图，可以在后台执行
    public void setIvPreview(String filePath, final Bitmap bitmap) {
        if (!filePath.equals(this.filePath)) return;
        post(new Runnable() {
            @Override
            public void run() {
                ivPreview.setImageBitmap(bitmap);
            }
        });
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_local_video, this, true);
        ButterKnife.bind(this);
    }

    public void bind(Cursor cursor) {
        //视频名称
        String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        tvVideoName.setText(videoName);
        //取出文件路径
        filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

        //获取视频的预览图是一个费时操作
        //------到后台线程执行------

        //同时获取多张图片的时候,即同时多个线程
        //-----线程池-----

        //获取过的图片，做缓存出来
        //-----LruCache(最近最少使用原则)


        //获取预览图
//        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MICRO_KIND);
        //设置预览图
//        ivPreview.setImageBitmap(bitmap);
    }

    @OnClick
    public void onClick() {
        VideoViewActivity.open(getContext(), filePath);
    }
}
