package reactnative.ng.smc.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * rxJava 结合retrofit 获取数据
 * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取, 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了:
 */

public class PracticalActivity extends AppCompatActivity {
    private int mCurrentPage = 1;
    private NewsAdapter mNewsAdapter;
    private List<NewsResultEntity> mNewsResultEntities = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical);
        initView();
    }

    private void initView() {
        Button btRefresh = (Button) findViewById(R.id.bt_refresh);
        btRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refreshArticle(++mCurrentPage);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mNewsAdapter = new NewsAdapter(mNewsResultEntities);
//        recyclerView.setAdapter(mNewsAdapter);
        refreshArticle(++mCurrentPage);
    }

    private void refreshArticle(int page) {
        Observable<List<NewsResultEntity>> observable = Observable
                .just(page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<List<NewsResultEntity>>>() {

                    @Override
                    public ObservableSource<List<NewsResultEntity>> apply(Integer page) throws Exception {
                        Observable<NewsEntity> androidNews = getObservable("Android", page);
                        Observable<NewsEntity> iosNews = getObservable("iOS", page);
                        return Observable.zip(androidNews, iosNews, new BiFunction<NewsEntity, NewsEntity, List<NewsResultEntity>>() {

                            @Override
                            public List<NewsResultEntity> apply(NewsEntity androidEntity, NewsEntity iosEntity) throws Exception {
                                List<NewsResultEntity> result = new ArrayList<>();
                                result.addAll(androidEntity.getResults());
                                result.addAll(iosEntity.getResults());
                                return result;
                            }
                        });
                    }
                });
        /**
         * 如果在请求的过程中Activity已经退出了, 这个时候如果回到主线程去更新UI, 那么APP肯定就崩溃了, Disposable 调用它的dispose()方法时就会切断水管+
         * 如果有多个Disposable 时就调用CompositeDisposable.add()  用CompositeDisposable.clear() 即可切断所有的水管.
         */
        DisposableObserver<List<NewsResultEntity>> disposable = new DisposableObserver<List<NewsResultEntity>>() {

            @Override
            public void onNext(List<NewsResultEntity> value) {
                mNewsResultEntities.clear();
                mNewsResultEntities.addAll(value);
                for (int i = 0; i < value.size(); i++) {
                    Log.i("信息", "NewsResultEntity：" + value.get(i).toString());
                }

//                mNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(disposable);
        mCompositeDisposable.add(disposable);
    }

    /**
     * 实操
     *
     * @param category
     * @param page
     * @return 当我们需要请求数据时，就应当像下面这样构造一个Observable<NewsEntity>：
     * baseUrl：定义请求链接的前缀
     * addConverterFactory：将OKHttp返回的标准Response解析成我们所需要的数据类型NewsEntity
     * addCallAdapterFactory：将Call<NewsEntity>转换成Observable<NewsEntity>，这样才能真正将Retrofit和RxJava结合起来。
     */
    private Observable<NewsEntity> getObservable(String category, int page) {
        NewsApi api = new Retrofit.Builder()
                .baseUrl("http://gank.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(NewsApi.class);
        return api.getNews(category, 10, page);
    }
private void doSomeThing(){
//    Observable.create(new ObservableOnSubscribe<Response>() {
//        @Override
//        public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
//            Builder builder = new Builder()
//                    .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
//                    .get();
//            Request request = builder.build();
//            Call call = new OkHttpClient().newCall(request);
//            Response response = call.execute();
//            e.onNext(response);
//        }
//    }).map(new Function<Response, MobileAddress>() {
//        @Override
//        public MobileAddress apply(@NonNull Response response) throws Exception {
//            if (response.isSuccessful()) {
//                ResponseBody body = response.body();
//                if (body != null) {
//                    Log.e(TAG, "map:转换前:" + response.body());
//                    return new Gson().fromJson(body.string(), MobileAddress.class);
//                }
//            }
//            return null;
//        }
//    }).observeOn(AndroidSchedulers.mainThread())
//            .doOnNext(new Consumer<MobileAddress>() {
//                @Override
//                public void accept(@NonNull MobileAddress s) throws Exception {
//                    Log.e(TAG, "doOnNext: 保存成功：" + s.toString() + "\n");
//                }
//            }).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<MobileAddress>() {
//                @Override
//                public void accept(@NonNull MobileAddress data) throws Exception {
//                    Log.e(TAG, "成功:" + data.toString() + "\n");
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG, "失败：" + throwable.getMessage() + "\n");
//                    }
//                });
}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();//可切断所有的水管
    }
}

