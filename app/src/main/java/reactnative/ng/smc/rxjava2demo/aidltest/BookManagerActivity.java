package reactnative.ng.smc.rxjava2demo.aidltest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.aidl.Book;
import reactnative.ng.smc.rxjava2demo.aidl.IBookManager;
import reactnative.ng.smc.rxjava2demo.aidl.IOnNewBookArrivedListener;

/**
 * aidi 客户端的实现
 */
public class BookManagerActivity extends AppCompatActivity {
    private TextView bookMess_tv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i("信息", "handleMessage:" + msg.obj.toString());
                    Book book = (Book) msg.obj;
                    stringBuilder.append(" id:" + book.getBookId() + "-->name:" + book.getBookName() + "\n");
                    bookMess_tv.setText(stringBuilder.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private IBookManager bookManager;
    private StringBuilder stringBuilder;
// final   private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
//        @Override
//        public void binderDied() {
//            if (bookManager == null)
//                return;
//            bookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
//            bookManager = null;
//            // 这里重新绑定远程Service
//        }
//    };
    private ServiceConnection mConnection = new ServiceConnection() {


        //服务连接成功时回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                //向服务端新增一本书
                Book newBook = new Book(5, "android 艺术开发探索");
                bookManager.addBook(newBook);
                List<Book> list = bookManager.getBookList();
                stringBuilder = new StringBuilder();
                for (Book book : list) {
                    Log.e("信息", "name:" + book.getBookName() + "  id:" + book.getBookId());
                    stringBuilder.append(" id:" + book.getBookId() + "-->name:" + book.getBookName() + "\n");
                }
                bookMess_tv.setText(stringBuilder.toString());

                bookManager.registerListener(mOnNewBookArrivedListener);
//                bookManager.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        //断开服务时回调
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //注册新书到来的回调
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            //因为运行在客户端的binder线程池中 所以要切换线程
            handler.obtainMessage(1, newBook).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        bookMess_tv = (TextView) findViewById(R.id.bookMess_tv);
        bindService(new Intent(this, BookManagerService.class), mConnection, Service.BIND_AUTO_CREATE);
        /**
         * 测试同一个应用中ContentProvider
         *
         */
        Uri uri=Uri.parse("content://smc.contentprovider.BookProvider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
    }

    @Override
    protected void onDestroy() {
        if (bookManager != null && bookManager.asBinder().isBinderAlive()) {
            try {
                bookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
