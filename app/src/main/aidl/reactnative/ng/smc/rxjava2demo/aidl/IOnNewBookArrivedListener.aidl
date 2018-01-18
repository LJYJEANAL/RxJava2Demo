// IOnNewBookArrivedListener.aidl
package reactnative.ng.smc.rxjava2demo.aidl;

// Declare any non-default types here with import statements
import reactnative.ng.smc.rxjava2demo.aidl.Book;
interface IOnNewBookArrivedListener {
         void onNewBookArrived(in Book newBook);
}
