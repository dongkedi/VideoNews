package com.zhuoxin.videonews.bombapi.result;

import com.zhuoxin.videonews.bombapi.entity.NewsEntity;

/**
 * Created by Administrator on 2016/12/27.
 */

public class CollectResult {
    private boolean success;
    private String error;
    private NewsEntity data;

    public boolean isSuccess() {
        return success;
    }

    public NewsEntity getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
