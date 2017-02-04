package com.zhuoxin.videonews.bombapi.entity;

import com.zhuoxin.videonews.bombapi.other.AuthorPointer;
import com.zhuoxin.videonews.bombapi.other.NewsPointer;

/**
 * Created by Administrator on 2016/12/27.
 */

public class PublishEntity {
    private String content;
    private AuthorPointer author;
    private NewsPointer news;

    public PublishEntity(String content, String userId, String newsId) {
        this.content = content;
        this.author = new AuthorPointer(userId);
        this.news = new NewsPointer(newsId);
    }


}
