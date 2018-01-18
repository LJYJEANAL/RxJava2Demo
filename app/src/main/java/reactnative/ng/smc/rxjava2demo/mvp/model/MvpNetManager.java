package reactnative.ng.smc.rxjava2demo.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import reactnative.ng.smc.rxjava2demo.mvp.data.SectionInfo;
import reactnative.ng.smc.rxjava2demo.network.FromJsonType;
import reactnative.ng.smc.rxjava2demo.network.data.ContentInfo;
import reactnative.ng.smc.rxjava2demo.network.data.ContentTabInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/10/12.
 */

public class MvpNetManager {
    /**
     * s使用volatile 关键字修饰  利用volatile的禁止指令重排序优化特性解决JVM重排序指令
     */
    private volatile static MvpNetManager mvpNetManager;
    private Call<ContentInfo> getSecationContentCall;
    private Call<ResponseBody> secationBannerCall;
    private static Gson gson;
    private static MvpDataApi mvpDataApi;

    /**
     * 双重检查锁模式
     *
     * @return
     */
    public static MvpNetManager getInstance() {
        if (mvpNetManager == null) {
            synchronized (MvpNetManager.class) {
                if (mvpNetManager == null) {
                    mvpNetManager = new MvpNetManager();
                    gson = new Gson();
                    mvpDataApi = MVPHttpClient.getInstance().getDataApi();

                }
            }
        }
        return mvpNetManager;
    }

    public void _getRecommendCall(int id, int start, int limit, final Listener<Boolean,  List<SectionInfo>> listener) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);

        Call<ResponseBody> recommendCall = mvpDataApi.getRecommendCall(map);
        recommendCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                       String result= response.body().string();
                        List<SectionInfo> sectionInfoList=gson.fromJson(result, new TypeToken<List<SectionInfo>>() {
                        }.getType());
                        if (listener != null) {
                            listener.onCallBack(true, sectionInfoList);
                        }
//                        for (int i = 0; i <sectionInfoList.size() ; i++) {
//                            Log.i("信息", "SectionName:" + sectionInfoList.get(i).getSectionName());
//                            List<SectionContents> sectionContentsList = sectionInfoList.get(i).getSectionContents();
//                                for (int j = 0; j < sectionContentsList.size(); j++) {
//                                    Log.e("信息", "name:" + sectionContentsList.get(j).getName());
//                                }
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (listener != null) {
                            listener.onCallBack(false, null);
                        }
                        Log.e("信息", "IOException:" + e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("信息", "onFailure:" + t.toString());
                if (listener != null) {
                listener.onCallBack(false, null);
                }
            }
        });
    }

    /**
     * 手动解析
     *
     * @param id
     * @param start
     * @param limit
     */
    public void getSecationBannerCall(int id, int start, int limit, final Listener<Boolean, List<ContentTabInfo>> listener) {
        if (secationBannerCall != null) {
            secationBannerCall.cancel();
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);
        secationBannerCall = mvpDataApi.getSecationBannerCall(map);
//        try {//同步
//            Response<ResponseBody> response=  secationBannerCall.execute();
//            String result= response.body().string();
////          List<ContentTabInfo> infoList=  gson.fromJson(result,new TypeToken<List<ContentTabInfo>>(){}.getType());
//            List<ContentTabInfo> infoList=  new FromJsonType<ContentTabInfo>().gettList(result);
//            if (infoList!=null&& infoList.size()>0){
//                listener.onCallBack(true,infoList);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        secationBannerCall.enqueue(new Callback<ResponseBody>() {//异步
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
//                       List<ContentTabInfo> infoList=  gson.fromJson(result,new TypeToken<List<ContentTabInfo>>(){}.getType());
                        List<ContentTabInfo> infoList = new FromJsonType<ContentTabInfo>().gettList(result);
                        if (infoList != null && infoList.size() > 0) {
                            listener.onCallBack(true, infoList);
                        }
                        for (ContentTabInfo info : infoList) {
                            Log.i("信息", "ContentTabInfo:" + info.getSectionName());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.onCallBack(false, null);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onCallBack(false, null);
            }
        });

    }

    /**
     * gosn 自动转换
     *
     * @param id
     * @param start
     * @param limit
     */
    public void getSecationContentCall(int id, int start, int limit, final Listener<Boolean, ContentInfo> listener) {
        if (getSecationContentCall != null) {
            getSecationContentCall.cancel();
        }
        getSecationContentCall = mvpDataApi.getSecationContentCall(id, start, limit);
        getSecationContentCall.enqueue(new Callback<ContentInfo>() {
            @Override
            public void onResponse(Call<ContentInfo> call, Response<ContentInfo> response) {
                if (response.isSuccessful()) {
                    ContentInfo contentInfo = response.body();
                    if (contentInfo != null) {
                        listener.onCallBack(true, contentInfo);

                        for (int i = 0; i < contentInfo.getResults().size(); i++) {
                            Log.e("信息", contentInfo.getResults().get(i).getName());
                        }

                    } else {
                        listener.onCallBack(false, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContentInfo> call, Throwable t) {
                listener.onCallBack(false, null);
            }
        });
    }

    /**
     * rxJava操作
     *
     * @param start
     * @param limit
     */
    public void getSecationContentCall2(int id, int start, int limit, final Listener<Disposable, ContentInfo> listener) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);
        Observable<ContentInfo> contentInfoObservable = mvpDataApi.getSecationContentCall(map);
        contentInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//主线程运行
                .subscribe(new Observer<ContentInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("信息", "onSubscribe:" + d.toString());
                        //需要在onDestroy方法中切断水管  d.dispose();
                        listener.onCallBack(d, null);
                    }

                    @Override
                    public void onNext(ContentInfo value) {
                        if (value != null) {
                            listener.onSuccess(value);
                        } else {
                            listener.onFailed("服务器故障");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("信息", "onError:" + e.toString());
                        listener.onFailed(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("信息", "onComplete:");
                    }
                });
    }
}
