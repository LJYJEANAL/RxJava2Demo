package reactnative.ng.smc.rxjava2demo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.aesthetic.AestheticActivity;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import reactnative.ng.smc.rxjava2demo.activity_model.ButtonActivity;
import reactnative.ng.smc.rxjava2demo.aidltest.aidl_impl.BinderPoolActivity;
import reactnative.ng.smc.rxjava2demo.contentprovider.ProviderActivity;
import reactnative.ng.smc.rxjava2demo.ipc.SerializableTest;
import reactnative.ng.smc.rxjava2demo.lrecycerview.MulActivity;
import reactnative.ng.smc.rxjava2demo.material_design.MaterialViewActivity;
import reactnative.ng.smc.rxjava2demo.material_design.TestScrollActivity;
import reactnative.ng.smc.rxjava2demo.mvp.model.MvpNetManager;
import reactnative.ng.smc.rxjava2demo.mvp.view.MvpActivity;
import reactnative.ng.smc.rxjava2demo.socket.TCPClientActivity;
import reactnative.ng.smc.rxjava2demo.view.BroadCastActivity;

/**
 * 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
 */
public class MainActivity extends AestheticActivity {

    private TextView text;
    private String TAG = "信息";
    private List<AdInfo> advList;
    private AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // If we haven't set any defaults, do that now
      /*  if (Aesthetic.isFirstTime()) {
            Aesthetic.get()
                    .activityTheme(R.style.AppTheme)
                    .colorPrimaryRes(R.color.colorPrimarys)
                    .colorAccentRes(R.color.indicatorSelectColor)
                    .textColorPrimaryRes(R.color.colorAccents)//字体设置 android:textColor="?android:textColorPrimary"生效
                    .textColorPrimaryInverseRes(R.color.indicatorSelectColor)
                    .textColorSecondaryRes(R.color.navigationBarColor)//设置字体颜色 android:textColor="?android:textColorSecondary"
                    .textColorSecondaryInverseRes(R.color.md_purple)
//                    .colorStatusBarAuto()
//                    .colorNavigationBarAuto()
//                    .navigationViewMode(NavigationViewMode.SELECTED_ACCENT)
//                    .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
//                    .bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                    .apply();
        }*/

        text = (TextView) findViewById(R.id.text);
        SerializableTest serializableTest = new SerializableTest();
        serializableTest.setSerialzable();
        serializableTest._setSerialzable();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.languages);
//                Toast.makeText(MainActivity.this, "你点击的是:"+languages[pos],Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

//        MainManager.getInstance().getSecationContentCall2(5055, 0, 10);
        MvpNetManager.getInstance()._getRecommendCall(5042,0,4,null);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MvpActivity.class));

/**
 * 执行弹窗的显示操作
 * 这里我内置了三种ViewPager的滑动动画效果：
 DepthPageTransformer；
 RotateDownPageTransformer；
 ZoomOutPageTransformer；
 */
//                adManager.setPageTransformer(new RotateDownPageTransformer());
//                adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, CoordinatorActvity.class));
                startActivity(new Intent(MainActivity.this, BroadCastActivity.class));

            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MessengerActivity.class));
                startActivity(new Intent(MainActivity.this, ButtonActivity.class));
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MulActivity.class));
//                startActivity(new Intent(MainActivity.this, NetDrawableActivity.class));
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ProviderActivity.class));
            }
        });
         findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, TCPClientActivity.class));
             }
         });
         findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, BinderPoolActivity.class));
             }
         });
         findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, DragActivity.class));
             }
         });
         findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 startActivity(new Intent(MainActivity.this, YoutubeMainActivity.class));
                 startActivity(new Intent(MainActivity.this, TestScrollActivity.class));
             }
         });
         findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, MaterialViewActivity.class),
                         ActivityOptions.makeSceneTransitionAnimation(MainActivity.this)
                                 .toBundle());
//                 startActivity(new Intent(MainActivity.this, MaterialViewActivity.class));
             }
         });


//        Course_Three();
//        Course_Three_flatMap();
//        Course_Three_zip();

        Course_five();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        advList = new ArrayList<>();
        AdInfo adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage1.png");
        advList.add(adInfo);

        adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage2.png");
        advList.add(adInfo);
        /**
         * 创建广告活动管理对象
         */
        adManager = new AdManager(MainActivity.this, advList);
        adManager.setOverScreen(true)
                .setPageTransformer(new DepthPageTransformer());
    }

    /**
     * 教程一 ：基础
     * ObservableEmitter： Emitter是发射器的意思，这个就是用来发出事件的，它可以发出三种类型的事件，
     * 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
     * 注: 关于onComplete和onError唯一并且互斥这一点, 是需要自行在代码中进行控制, 如果你的代码逻辑中违背了这个规则, 并不一定会导致程序崩溃.
     * 比如发送多个onComplete是可以正常运行的, 依然是收到第一个onComplete就不再接收了, 但若是发送多个onError, 则收到第二个onError事件会导致程序会崩溃.
     *
     * @throws Exception
     * , 如果在请求的过程中Activity已经退出了, 这个时候如果回到主线程去更新UI, 那么APP肯定就崩溃了, Disposable 调用它的dispose()方法时就会切断水管+
     * 如果有多个Disposable 时就调用CompositeDisposable.add()  用CompositeDisposable.clear() 即可切断所有的水管.
     */
    private CompositeDisposable compositeDisposable;

    private void Course_One() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {


            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                compositeDisposable = new CompositeDisposable();
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("信息", "onSubscribe:" + d.toString());
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Integer value) {
                Log.e("信息", "onNext:" + value);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("信息", "onComplete");
            }
        };
        //建立关系
        observable.subscribe(observer);
    }

    /**
     * 教程二：改变上游发送事件的线程, 让它去子线程中发送事件, 然后再改变下游的线程, 让它去主线程接收事件. 通过RxJava内置的线程调度器
     * 在RxJava中, 已经内置了很多线程选项供我们选择, 例如有
     * <p>
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread() 代表Android的主线程
     */
    private void Course_Tow() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("first");
                e.onNext("Tow");
                e.onNext("three");

            }
        });
        //得到的结果
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.i("信息", s);
                text.setText(s + "--->当前线程：" + Thread.currentThread().getName() + "\n");
            }

        };
        //设置绑定与线程
        observable.subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(consumer);

    }

    /**
     * 教程三：最简单的变换操作符map
     * 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化.
     * 在上游我们发送的是数字类型, 而在下游我们接收的是String类型, 中间起转换作用的就是map操作符
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合
     * map 基本作用就是将一个 Observable 通过某种函数关系，转换为另一种 Observable
     */
    private void Course_Three() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "result:" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("信息", s);
            }
        });

    }

    /**
     * flatMap的作用是将圆形的事件转换为一个发送矩形事件和三角形事件的新的上游Observable.
     * flatMap 并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的 ConcatMap
     * concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序，所以，我们就直接把 flatMap 替换为 concatMap
     */
    private void Course_Three_flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("varal:" + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i("信息", s);
            }
        });

    }


    /**
     * 学习了Zip的基本用法, 那么它在Android有什么用呢, 其实很多场景都可以用到Zip. 举个例子.
     * <p>
     * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取, 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了:
     * 专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
     */
    private void Course_Three_zip() {

        Observable<Integer> observable1 =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Log.e(TAG, "emit 1");
                        emitter.onNext(1);
                        Thread.sleep(1000);

                        Log.e(TAG, "emit 2");
                        emitter.onNext(2);
                        Thread.sleep(1000);

                        Log.e(TAG, "emit 3");
                        emitter.onNext(3);
                        Thread.sleep(1000);

                        Log.e(TAG, "emit 4");
                        emitter.onNext(4);


                        Log.e(TAG, "emit complete1");
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "emit A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.e(TAG, "emit B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.e(TAG, "emit C");
                emitter.onNext("C");
                Thread.sleep(1000);

                Log.e(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }

    /**
     * 通过减少进入水缸的事件数量的确可以缓解上下游流速不均衡的问题
     * <p>
     * 因此我们总结一下, 本节中的治理的办法就两种:
     * <p>
     * 一是从数量上进行治理, 减少发送进水缸里的事件   .sample(2, TimeUnit.SECONDS)
     * 二是从速度上进行治理, 减缓事件发送进水缸的速度
     */
    private void Course_five() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
//                    Thread.sleep(2000);  //每次发送完事件延时2秒  二是从速度上进行治理, 减缓事件发送进水缸的速度
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)  //sample取样  每隔指定的时间就从上游中取出一个事件发送给下游. 这里我们让它每隔2秒取一个事件给下游
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "" + integer);
                    }
                });
    }

    /**
     * 先来减少进入水缸的事件的数量:
     */
    private void Course_five_sample() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).sample(2, TimeUnit.SECONDS); //进行sample采样

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.toString());
            }
        });

    }

    /**
     * 减缓速度:
     */
    private void Course_five_sleep() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                    Thread.sleep(2000);  //发送事件之后延时2秒
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.w(TAG, throwable);
            }
        });

    }

    /**
     * Concat
     * 对于单一的把两个发射器连接成一个发射器
     * 可以做到不交错的发射两个甚至多个 Observable 的发射事件，并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable。
     */
    private void concat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "concat : " + integer + "\n");
                        //输出为123456
                    }
                });

    }

    /**
     * distinct
     * 这个操作符非常的简单、通俗、易懂，就是简单的去重
     * 输出结果为  12345
     */
    private void distinct() {
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "distinct : " + integer + "\n");
                    }
                });

    }

    /**
     * Filter:
     * 它的作用也很简单，过滤器嘛。可以接受一个参数，让其过滤掉不符合我们条件的值
     * 过滤器舍去了小于 10 的值，所以最好的输出只有 20, 65, 19。
     */
    private void Filter() {
        Observable.just(1, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "filter : " + integer + "\n");
            }
        });
    }

    /**
     * buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长)
     * 分成最大不超过 count 的 buffer
     * ，然后生成一个  Observable
     */
    private void buffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        Log.e(TAG, "buffer size : " + integers.size() + "\n");
                        Log.e(TAG, "buffer value : ");
                        for (Integer i : integers) {
                            Log.e(TAG, i + "");
                        }
                        Log.e(TAG, "\n");
                    }
                });
    }

    /**
     * timer 很有意思，相当于一个定时任务。在 1.x 中它还可以执行间隔逻辑，但在 2.x 中此功能被交给了 interval，
     * 下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。
     */
    private void timer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        //延时2s执行
                    }
                });

    }

    /**
     * interval
     * interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     * <p>
     * 如同 Log 日志一样，第一次延迟了 3 秒后接收到，后面每次间隔了 2 秒。
     * 然而，心细的小伙伴可能会发现，由于我们这个是间隔执行，所以当我们的Activity 都销毁的时候，实际上这个操作还依然在进行，所以，我们得花点小心思让我们在不需要它的时候干掉它。查看源码发现，我们subscribe(Cousumer<? super T> onNext)返回的是Disposable，我们可以在这上面做文章。
     *
     * @Override protected void onDestroy() {
     * super.onDestroy();
     * if (mDisposable != null && !mDisposable.isDisposed()) {
     * mDisposable.dispose();
     * }
     * }
     */
    private void interval() {
        Observable.interval(3, 2, TimeUnit.SECONDS)//延迟3s开始发送 间隔事件2s 单位为s
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
//                        Log.e(TAG, "interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");

                    }
                });

    }

    /**
     * 其实觉得 doOnNext 应该不算一个操作符，但考虑到其常用性，
     * 我们还是咬咬牙将它放在了这里。它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它，无疑我们可以这样实现。
     */
    private void doOnNext() {
        Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "doOnNext :" + integer + "\n");
            }
        });
    }

    /**
     * skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
     * 跳过2个数开始接收  输出 3 4 5
     */
    private void skip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "skip : " + integer + "\n");
                    }
                });
    }

    /**
     * take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
     * 指的是最多接受2个数据  输出结果为： 1 ， 2
     */
    private void take() {
        Flowable.fromArray(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "accept: take : " + integer + "\n");
                    }
                });

    }

    /**
     * just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用 onNext() 方法。
     */
    private void just() {
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "accept : onNext : " + s + "\n");
                    }
                });
    }

    /**
     * 顾名思义，Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()
     */
    private void Single() {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.e(TAG, "single : onSuccess : " + integer + "\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "single : onError : " + e.getMessage() + "\n");
                    }
                });

    }

    /**
     * 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
     */
    private void defer() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });


        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "defer : " + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "defer : onComplete\n");
            }
        });

    }

    /**
     * last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    private void last() {
        Observable.just(1, 2, 3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "last : " + integer + "\n");
                    }
                });
    }

    /**
     * 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，
     * 也支持迭代器集合。注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
     */
    private void merge() {
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "accept: merge :" + integer + "\n");
                    }
                });

    }
}
