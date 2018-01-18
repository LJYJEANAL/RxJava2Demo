package reactnative.ng.smc.rxjava2demo.mvp.model;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MVPHttpClient {
    private static final String URL_HOST = " http://u1.3gtv.net:2080/";
    private  static MVPHttpClient mvpHttpClient;
    private MvpDataApi mvpDataApi;
    private final Retrofit retrofit;

    public static MVPHttpClient getInstance() {
        if (mvpHttpClient ==null){
            synchronized (MVPHttpClient.class){
                if (mvpHttpClient ==null){
                    mvpHttpClient =new MVPHttpClient();
                }
            }
        }
        return mvpHttpClient;
    }

    public MVPHttpClient() {
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().baseUrl(URL_HOST).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))//gson转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava适配
                .build();
    }
    public MvpDataApi getDataApi(){
        if (mvpDataApi ==null){
           mvpDataApi = retrofit.create(MvpDataApi.class);
        }
        return mvpDataApi;
    }
}
