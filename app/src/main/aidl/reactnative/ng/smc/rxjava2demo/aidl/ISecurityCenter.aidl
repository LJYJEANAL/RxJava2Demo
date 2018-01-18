// ISecurityCenter.aidl
package reactnative.ng.smc.rxjava2demo.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //加密方法
   String encrypt(String content);
   //解密方法
   String dencrypt(String content);
}
