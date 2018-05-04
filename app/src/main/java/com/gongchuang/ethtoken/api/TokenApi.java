package com.gongchuang.ethtoken.api;

import com.gongchuang.ethtoken.base.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 统一管理网络请求的API类
 * <p>
 * Created by dwq on 2018/3/13/013.
 * e-mail:lomapa@163.com
 */

public class TokenApi {
    public static TokenApi instance;

    private TokenApiService service;

    public TokenApi(OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.PATH_DATA)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(TokenApiService.class);

    }

    public static TokenApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new TokenApi(okHttpClient);
        return instance;
    }

}
