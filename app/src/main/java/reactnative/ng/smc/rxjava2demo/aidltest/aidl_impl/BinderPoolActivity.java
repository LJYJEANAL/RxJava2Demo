package reactnative.ng.smc.rxjava2demo.aidltest.aidl_impl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.aidl.ICompute;
import reactnative.ng.smc.rxjava2demo.aidl.ISecurityCenter;

public class BinderPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
               doWork();
            }
        }).start();
    }

    private void doWork() {
      BinderPool binderPool=  BinderPool.getsInstance(BinderPoolActivity.this);
     IBinder securityBinder= binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
     IBinder computeBinder= binderPool.queryBinder(BinderPool.BINDER_COMPUTE);

     ISecurityCenter msecurityCenter=SecurityCenterImpl.asInterface(securityBinder);
     ICompute computeCenter=IComputeImpl.asInterface(computeBinder);
        try {
          String password=  msecurityCenter.encrypt("hellow-android 安卓");
          String content=  msecurityCenter.dencrypt(password);
           int add= computeCenter.add(4,5);
            Log.i("信息","encrypt:"+password);
            Log.i("信息","dencrypt:"+content);
            Log.i("信息","add:"+add);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
