package com.example.user.moviecatalogappv2.API;

import com.example.user.moviecatalogappv2.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIResponder {

    private APICall apiCall;

    public APIResponder(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request authentication = chain.request();
                        HttpUrl httpUrl = authentication.url()
                                .newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build();

                        authentication = authentication.newBuilder()
                                .url(httpUrl)
                                .build();

                        return chain.proceed(authentication);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCall = retrofit.create(APICall.class);
    }

    public APICall getService() {
        return apiCall;
    }
}
