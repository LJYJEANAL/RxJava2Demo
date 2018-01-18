package reactnative.ng.smc.rxjava2demo.aidltest.aidl_impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

import reactnative.ng.smc.rxjava2demo.aidl.IBinderPool;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BinderPool {
    public static final int BINDER_NONE=-1;
    public static final int BINDER_COMPUTE=0;
    public static final int BINDER_SECURITY_CENTER=1;
    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile  BinderPool sInstance;
    /**把binderServer 的异步操作转换成同步操作 可能存在耗时*/
    private CountDownLatch mConnectBinderPoolCountDownLatch;


    public BinderPool(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        connectBinderPoolService();
    }
    public static BinderPool getsInstance(Context context){
        if (sInstance==null){
            synchronized (BinderPool.class){
                if (sInstance==null){
                    sInstance=new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 启动链接服务
     */
    private synchronized void  connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service=new Intent(mContext,BinderPoolService.class);
        mContext.bindService(service,mBinderPoolConnection,Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public IBinder queryBinder(int binderCode){
        IBinder binder=null;

            try {
                if (mBinderPool!=null){
                binder=mBinderPool.queryBinder(binderCode);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
       return binder;

    }
    private ServiceConnection mBinderPoolConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool=IBinderPool.Stub.asInterface(service);
            try {
                //防止链接过程中 中断
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    /**
     * 重连
     */
    private IBinder.DeathRecipient mBinderPoolDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            mBinderPool=null;
            connectBinderPoolService();
        }
    };
    public static class IBinderPoolImpl extends IBinderPool.Stub {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder=null;
            switch (binderCode){
                case BINDER_SECURITY_CENTER:
                    binder=new SecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder=new IComputeImpl();
                    break;
            }
            return binder;
        }
    }

}
