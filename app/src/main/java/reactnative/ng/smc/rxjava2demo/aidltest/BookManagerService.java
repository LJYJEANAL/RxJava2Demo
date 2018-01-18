package reactnative.ng.smc.rxjava2demo.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import reactnative.ng.smc.rxjava2demo.aidl.Book;
import reactnative.ng.smc.rxjava2demo.aidl.IBookManager;
import reactnative.ng.smc.rxjava2demo.aidl.IOnNewBookArrivedListener;

/**
 *AIDL  远程服务端Service的实现
 */

public class BookManagerService extends Service {
    private AtomicBoolean mIsServiceDestoryed=new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
//            if (!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            }else {
//                Log.i("信息","已经存在");
//            }
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
//            if (mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//                Log.i("信息","unregisterListener succeed");
//            }else {
//                Log.i("信息","not found ");
//            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1,"android"));
        mBookList.add(new Book(2,"java"));
        mBookList.add(new Book(3,"ios"));
        mBookList.add(new Book(4,"konil"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        int n=mListenerList.beginBroadcast();
        for (int i = 0; i <n ; i++) {
           IOnNewBookArrivedListener l= mListenerList.getBroadcastItem(i);
            if (l!=null){
                l.onNewBookArrived(book);
            }
        }
        mListenerList.finishBroadcast();
    }
    //创建线程 每隔5s添加一本书
    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                    int bookId=mBookList.size()+1;
                    Book book=new Book(bookId,"new Book#"+bookId);
                    onNewBookArrived(book);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
