package com.zhuoxin.videonews.ui.news;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.videonews.R;
import com.zhuoxin.videonews.UserManager;
import com.zhuoxin.videonews.bombapi.entity.NewsEntity;
import com.zhuoxin.videonews.commons.CommonUtils;
import com.zhuoxin.videonews.ui.base.BaseItemView;
import com.zhuoxin.videonews.ui.news.comments.CommentsActivity;
import com.zhuoxin.videonews.videoplayernews.list.MediaPlayerManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新闻列表的单项视图，将使用MediaPlayer播放视频，TextureView来显示视频
 * Created by Administrator on 2016/12/26.
 */

public class NewsItemView extends BaseItemView<NewsEntity> implements MediaPlayerManager.OnPlaybackListener, TextureView.SurfaceTextureListener {
    @BindView(R.id.textureView)
    TextureView textureView;//用来展示视频的TextureView
    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ivPlay)
    ImageView ivPlay;


    private NewsEntity newsEntity;
    private MediaPlayerManager mediaPlayerManager;
    private Surface surface;

    public NewsItemView(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_news, this, true);
        ButterKnife.bind(this);
        //添加列表视频播放控制相关监听
        mediaPlayerManager = MediaPlayerManager.getsInstance(getContext());
        mediaPlayerManager.addPlayerBackListener(this);
        //textureView->surface监听
        textureView.setSurfaceTextureListener(this);

    }

    @Override
    protected void bindModel(NewsEntity newsEntity) {
        this.newsEntity = newsEntity;
        //初始化视图状态
        tvNewsTitle.setVisibility(View.VISIBLE);
        ivPreview.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ivPlay.setVisibility(View.VISIBLE);
        //设置标题,时间，预览图
        tvNewsTitle.setText(newsEntity.getNewsTitle());
        tvCreatedAt.setText(CommonUtils.format(newsEntity.getCreatedAt()));
        //设置预览图
        String url = CommonUtils.encodeUrl(newsEntity.getPreviewUrl());
        Picasso.with(getContext()).load(url).into(ivPreview);
    }

    //点击事件，跳转到评论页面
    @OnClick(R.id.tvCreatedAt)
    public void navigateToComments() {
        CommentsActivity.open(getContext(),newsEntity);
    }

    //点击预览图，开始播放
    @OnClick(R.id.ivPreview)
    public void startPlayer() {
        if (surface == null) return;
        //因为viewpager有缓存机制，需要控制视频停止
        UserManager.getInstance().setPlay(true);
        String path = newsEntity.getVideoUrl();
        String videoId = newsEntity.getObjectId();
        mediaPlayerManager.startPlayer(surface, path, videoId);

    }

    @OnClick(R.id.textureView)
    public void stopPlayer() {
        mediaPlayerManager.stopPlayer();
    }

    //判断是否操作当前视频
    private boolean isCurrentVideo(String videoId) {
        if (videoId == null || newsEntity == null) return false;
        return videoId.equals(newsEntity.getObjectId());
    }

    //------------------------------------------------- start PlayerBack
    @Override
    public void onStartBuffering(String videoId) {
        if (isCurrentVideo(videoId)) {
            //将当前视频的prograssBar显示出来
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStopBuffering(String videoId) {
        if (isCurrentVideo(videoId)) {
            //将当前视频的prograssBar隐藏
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStartPlay(String videoId) {
        if (isCurrentVideo(videoId)) {
            tvNewsTitle.setVisibility(View.INVISIBLE);
            ivPreview.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            ivPlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStopPlay(String videoId) {
        if (isCurrentVideo(videoId)) {
            tvNewsTitle.setVisibility(View.VISIBLE);
            ivPreview.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            ivPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSizeMeasured(String videoId, int width, int height) {
        if (isCurrentVideo(videoId)) {
            //无需求，不作处理
        }
    }
    //------------------------------------------------- end

    //------------------------------------------------- start Texture
    //textureView->surface相关监听
    //拿到surface
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = new Surface(surface);
    }

    //当SurfaceTexture缓冲区大小的改变
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    //当Surface销毁时，停止播放
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.surface.release();
        this.surface = null;
        //停止自己
        if (newsEntity.getObjectId().equals(mediaPlayerManager.getVideoId())) {
            mediaPlayerManager.stopPlayer();
        }
        return false;
    }

    //当SurfaceTexture通过updateTexImage()更新
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    //------------------------------------------------- end
}
