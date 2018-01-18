package reactnative.ng.smc.rxjava2demo.network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import reactnative.ng.smc.rxjava2demo.network.data.ContentInfo;
import reactnative.ng.smc.rxjava2demo.network.data.ContentTabInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MainManager {
    /**
     * s使用volatile 关键字修饰  利用volatile的禁止指令重排序优化特性解决JVM重排序指令
     */
    private volatile static MainManager mainManager;
    private Call<ContentInfo> getSecationContentCall;
    private Call<ResponseBody> secationBannerCall;
    private static  Gson gson;

    /**
     * 双重检查锁模式
     * @return
     */
    public static MainManager getInstance() {
        if (mainManager == null) {
            synchronized (MainManager.class){
                if (mainManager==null){
                    mainManager = new MainManager();
                    gson = new Gson();
                }
            }
        }
        return mainManager;
    }

    /**
     * 手动解析
     * @param id
     * @param start
     * @param limit
     */
    public void getSecationBannerCall(int id, int start, int limit) {
        if (secationBannerCall != null) {
            secationBannerCall.cancel();
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);
        secationBannerCall = SMCHttpClient.getInstance().getDataApi().getSecationBannerCall(map);
        secationBannerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result= response.body().string();
//                       List<ContentTabInfo> infoList=  gson.fromJson(result,new TypeToken<List<ContentTabInfo>>(){}.getType());
                        List<ContentTabInfo> infoList=  new FromJsonType<ContentTabInfo>().gettList(result);
                        for (ContentTabInfo info:infoList) {
                            Log.i("信息","ContentTabInfo:"+info.getSectionName());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * gosn 自动转换
     * @param id
     * @param start
     * @param limit
     */
    public void getSecationContentCall(int id, int start, int limit) {
        if (getSecationContentCall != null) {
            getSecationContentCall.cancel();
        }
        getSecationContentCall = SMCHttpClient.getInstance().getDataApi().getSecationContentCall(id, start, limit);
        getSecationContentCall.enqueue(new Callback<ContentInfo>() {
            @Override
            public void onResponse(Call<ContentInfo> call, Response<ContentInfo> response) {
                if (response.isSuccessful()) {
                    ContentInfo contentInfo = response.body();
                    if (contentInfo != null) {
                        for (int i = 0; i < contentInfo.getResults().size(); i++) {
                            Log.e("信息", contentInfo.getResults().get(i).getName());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ContentInfo> call, Throwable t) {

            }
        });
    }
    /**
     * rxJava操作
     * @param start
     * @param limit
     */
    public void getSecationContentCall2(int id, int start, int limit) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);
        Observable<ContentInfo>  contentInfoObservable = SMCHttpClient.getInstance().getDataApi().getSecationContentCall(map);
        contentInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//主线程运行
                .subscribe(new Observer<ContentInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("信息","onSubscribe:"+d.toString());
                        //需要在onDestroy方法中切断水管  d.dispose();
                    }

                    @Override
                    public void onNext(ContentInfo value) {
                        if (value != null) {
                            for (int i = 0; i < value.getResults().size(); i++) {
                                Log.e("信息", value.getResults().get(i).getName());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("信息","onError:"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("信息","onComplete:");
                    }
                });
    }
}
