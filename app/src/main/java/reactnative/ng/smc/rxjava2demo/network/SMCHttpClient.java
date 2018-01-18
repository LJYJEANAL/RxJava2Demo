package reactnative.ng.smc.rxjava2demo.network;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SMCHttpClient {
    private static final String URL_HOST = " http://u1.3gtv.net:2080/";
    private  static SMCHttpClient smcHttpClient;
    private MainDataApi mainDataApi;
    private final Retrofit retrofit;

    public static  SMCHttpClient getInstance() {
        if (smcHttpClient==null){
            synchronized (SMCHttpClient.class){
                if (smcHttpClient==null){
                    smcHttpClient=new SMCHttpClient();
                }
            }
        }
        return  smcHttpClient;
    }

    public SMCHttpClient() {
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
    public MainDataApi getDataApi(){
        if (mainDataApi==null){
           mainDataApi= retrofit.create(MainDataApi.class);
        }
        return  mainDataApi;
    }
}
