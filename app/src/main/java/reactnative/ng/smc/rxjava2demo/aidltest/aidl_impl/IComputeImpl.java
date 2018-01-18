package reactnative.ng.smc.rxjava2demo.aidltest.aidl_impl;

import android.os.RemoteException;

import reactnative.ng.smc.rxjava2demo.aidl.ICompute;

/**
 * Created by Administrator on 2017/11/13.
 */

public class IComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
