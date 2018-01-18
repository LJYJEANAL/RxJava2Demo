// IBookManager.aidl
package reactnative.ng.smc.rxjava2demo.aidl;


// Declare any non-default types here with import statements
import reactnative.ng.smc.rxjava2demo.aidl.Book;
import reactnative.ng.smc.rxjava2demo.aidl.IOnNewBookArrivedListener;
interface IBookManager {
//获取所有book对象
   List<Book> getBookList();
   //添加Book对象
   void addBook(in Book book);
   //新增方法注册,用户新书到来提醒接口
   void registerListener(IOnNewBookArrivedListener listener);
   //新增方法取消注册,用户取消新书到来提醒接口
   void unregisterListener( IOnNewBookArrivedListener listener);
}
