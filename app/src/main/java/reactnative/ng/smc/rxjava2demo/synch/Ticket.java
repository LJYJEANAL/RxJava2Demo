package reactnative.ng.smc.rxjava2demo.synch;

import android.util.Log;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Ticket implements Runnable {
    private int num = 10;
    @Override
    public void run() {
        // 当前拥有的票数

            while (true) {
                if (num > 0) {
                    try {
                        Thread.sleep(10);

                    } catch (InterruptedException e) {
                    }
                    // 输出卖票信息
                    synchronized (this) {
                        // 输出卖票信息
                        if(num>0){
                            Log.e("信息",Thread.currentThread().getName() + ".....saleNum：" + num--);
                        }

                    }

                }
        }
    }
}
