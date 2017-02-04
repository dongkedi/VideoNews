package com.zhuoxin.videonews.bombapi.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/26.
 */

public class NewsEntity implements Serializable {

    /*"objectId":新闻id,
            "newsTitle":新闻标题,
            "videoUrl":视频地址,
            "previewUrl":预览图地址,
            "createAt":创建日期,
            "updateAt":更新日期*/

    private String objectId;//新闻id
    private String newsTitle; // 新闻标题
    private String videoUrl; // 视频地址
    private String previewUrl; // 视频预览图地址
    private Date createdAt;//创建日期
    private Date updatedAt;//更新日期

    public String getObjectId() {
        return objectId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
