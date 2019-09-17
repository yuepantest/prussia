package com.example.prussia.http;

import com.example.prussia.common.manage.Constant;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by QD on 2017/7/18.
 */

public class Api {
    //设置网络连接时间
    private static OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private static class SingletonHolder {
        //初始化retrofit
        public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        //初始化apiService
        public static ApiService apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return SingletonHolder.apiService;
    }

}
