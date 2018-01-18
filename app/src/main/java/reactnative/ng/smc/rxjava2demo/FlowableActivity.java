package reactnative.ng.smc.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlowableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowable);
//        FlowableTest();
        FlowableTest128();
    }

    /**
     * BackpressureStrategy.ERROR:选择背压,也就是出现上下游流速不均衡的时候应该怎么处理的办法, 这里我们直接用BackpressureStrategy.ERROR这种方式,
     * 这种方式会在出现上下游流速不均衡的时候直接抛出一个异常,这个异常就是著名的MissingBackpressureException
     */
    private void FlowableTest() {
        Flowable<Integer> upstrem = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);//增加一个参数
        /**
         * 下游的onSubscribe方法中传给我们的不再是Disposable了, 而是Subscription,
         * 首先它们都是上下游中间的一个开关, 之前我们说调用Disposable.dispose()方法可以切断水管, 同样的调用Subscription.cancel()也可以切断水管,
         * 不同的地方在于Subscription增加了一个void request(long n)方法,
         */
        Subscriber<Integer> downStream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e("信息", "onSubscribe" + s.toString());
                s.request(Long.MAX_VALUE);//注意  必须 不然出现异常 MissingBackpressureException

                /**
                 * 把request当做是一种能力, 当成下游处理事件的能力, 下游能处理几个就告诉上游我要几个, 这样只要上游根据下游的处理能力来决定发送多少事件,
                 * 就不会造成一窝蜂的发出一堆事件来, 从而导致OOM. 这也就完美的解决之前我们所学到的两种方式的缺陷, 过滤事件会导致事件丢失, 减速又可能导致性能损失.
                 * 而这种方式既解决了事件丢失的问题
                 */
            }

            @Override
            public void onNext(Integer integer) {
                Log.e("信息", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("信息", "onError" + t.toString());
            }

            @Override
            public void onComplete() {
                Log.e("信息", "onComplete");
            }
        };
        upstrem.subscribe(downStream);
    }

    /**
     * 这次我们直接让上游发送了1000个事件,下游仍然不调用request去请求, 与之前不同的是,
     * 这次我们用的策略是BackpressureStrategy.BUFFER, 这就是我们的新水缸啦, 这个水缸就比原来的水缸牛逼多了,如果说原来的水缸是95式步枪,
     * 那这个新的水缸就好比黄金AK , 它没有大小限制, 因此可以存放许许多多的事件.
     */
    private void FlowableTest128(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1000; i++) {
                    Log.e("信息", "emit " + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e("信息", "onSubscribe");
//                        mSubscription = s;
                        s.request(Long.MAX_VALUE);//下游处理上游分发下来的事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("信息", "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("信息", "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("信息", "onComplete");
                    }
                });

     
    }
}
