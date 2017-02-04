package com.zhuoxin.videonews.bombapi;

import com.zhuoxin.videonews.bombapi.entity.UserEntity;
import com.zhuoxin.videonews.bombapi.result.UserResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/22.
 * 用户相关网络接口
 */

public interface UserApi {
    //用户注册
    @POST("1/users")
    Call<UserResult> register(@Body UserEntity userEntity);

    //baseUrl="www.baidu.com"
    // Url=www.baidu.com/1/users?username=dkd123&password=123456

    @GET("1/login")
    Call<UserResult> login(@Query("username") String username, @Query("password") String password);

}
