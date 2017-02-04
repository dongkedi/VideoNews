package com.zhuoxin.videonews.bombapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/21.
 */

public class BombClient {
    private static BombClient bombClient;

    public static BombClient getInstance() {
        if (bombClient == null) {
            bombClient = new BombClient();
        }
        return bombClient;
    }

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private UserApi userApi;
    protected NewsApi newsApi;
    private Retrofit retrofit_cloud;//用于新接口
    private NewsApi newsApi_cloud;

    private BombClient() {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //构建OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BombInterceptor())//添加Bomb必要请求头的拦截器
                .addInterceptor(httpLoggingInterceptor)//添加拦截器
                .build();

        //让Gson能够将Bomb返回时间戳自动转换为Date对象
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        //构建Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //bomb服务器
                .baseUrl("https://api.bmob.cn/")
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //
        retrofit_cloud = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://cloud.bmob.cn/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //拿到UserApi
    public UserApi getUserApi() {
        if (userApi == null) {
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }

    //拿到UserApi
    public NewsApi getNewsApi() {
        if (newsApi == null) {
            newsApi = retrofit.create(NewsApi.class);
        }
        return newsApi;
    }

    //拿到newsApi—_cloud
    public NewsApi getNewsApi_cloud() {
        if (newsApi_cloud == null) {
            newsApi_cloud = retrofit_cloud.create(NewsApi.class);
        }
        return newsApi_cloud;
    }

//    //注册请求
//    public Call register(String username, String password) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("username", username);
//            jsonObject.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody requestBody = RequestBody.create(null, jsonObject.toString());
//
//        Request request = new Request.Builder()
//                .url("https://api.bmob.cn/1/users")
//                .post(requestBody)
//                .build();
//
//        return okHttpClient.newCall(request);
//    }
}
