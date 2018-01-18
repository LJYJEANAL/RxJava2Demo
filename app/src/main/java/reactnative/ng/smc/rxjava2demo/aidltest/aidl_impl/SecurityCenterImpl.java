package reactnative.ng.smc.rxjava2demo.aidltest.aidl_impl;

import android.os.RemoteException;

import reactnative.ng.smc.rxjava2demo.aidl.ISecurityCenter;

/**
 * Created by Administrator on 2017/11/13.
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE='^';
    //加密方法
    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars=content.toCharArray();
        for (int i = 0; i <chars.length ; i++) {
            chars[i]^=SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String dencrypt(String content) throws RemoteException {
        return encrypt(content);
    }
}