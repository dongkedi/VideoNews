package com.zhuoxin.videonews.ui.likes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.videonews.R;
import com.zhuoxin.videonews.UserManager;
import com.zhuoxin.videonews.bombapi.entity.NewsEntity;
import com.zhuoxin.videonews.commons.CommonUtils;
import com.zhuoxin.videonews.ui.base.BaseItemView;
import com.zhuoxin.videonews.ui.news.comments.CommentsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Administrator on 2016/12/27.
 */

public class LikesItemView extends BaseItemView<NewsEntity> {

    public LikesItemView(Context context) {
        super(context);
    }

    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    private NewsEntity newsEntity;

    @Override
    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_likes, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void bindModel(NewsEntity newsEntity) {
        this.newsEntity = newsEntity;
        tvNewsTitle.setText(newsEntity.getNewsTitle());
        tvCreatedAt.setText(CommonUtils.format(newsEntity.getCreatedAt()));
        String url = CommonUtils.encodeUrl(newsEntity.getPreviewUrl());
        Picasso.with(getContext()).load(url).into(ivPreview);
    }

    //跳转到评论页面
    @OnClick
    public void navigateToComments() {
        CommentsActivity.open(getContext(), newsEntity);
    }

    //长按删除
    @OnLongClick
    public boolean unCollectNews() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("是否取消收藏")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onItemLongClick(newsEntity.getObjectId(), UserManager.getInstance().getObjectId());
                    }
                })
                .setNegativeButton("否", null)
                .create();
        dialog.show();
        return true;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(String newsId, String userId);
    }

    private OnItemLongClickListener listener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

}
