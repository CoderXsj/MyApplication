package com.cydai.cncx.network;

import android.content.Context;

import com.cydai.cncx.common.Constants;
import com.cydai.cncx.util.SharedPreUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 薛世君
 * Date : 2016/10/9
 * Email : 497881309@qq.com
 */
public class NetworkClient {
    private static NetworkClient mNetworkClient;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private Context mContext;

    private NetworkClient(Context context,String url) {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggerInterceptor("network"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mContext = context;
    }

    public static void init(Context context,String url){
        if(context == null)
            throw new IllegalArgumentException("context 对象不能为 null");

        if(url == null)
            throw new IllegalArgumentException("url 地址不能为 null");

        mNetworkClient = new NetworkClient(context,url);
    }

    private Retrofit getRetrofit(){
        return mNetworkClient.retrofit;
    }

    public static <T> T getService(Class<T> clazz){
        T mService = mNetworkClient.getRetrofit().create(clazz);
        return mService;
    }

    class HeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Map<String,String> headers = new HashMap<>();
            headers.put(Constants.KEY_ACCESS_TOKEN, SharedPreUtils.getString(Constants.KEY_ACCESS_TOKEN,"",mContext));

            request.newBuilder().headers(Headers.of(headers));

            return null;
        }
    }
}