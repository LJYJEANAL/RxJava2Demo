package reactnative.ng.smc.rxjava2demo.messenger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.aidltest.BookManagerActivity;

public class MessengerActivity extends AppCompatActivity {

    private Messenger mGetReplyMessenger;
    private TextView mess_tv;

    /**
     * 用于接受服务端的回复信息
     */
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessengerService.MSG_FROM_Service:
                    String reply = msg.getData().getString("reply");
                    Log.i("信息", "来自服务端：" + reply);
                    mess_tv.setText("来自服务端：" + reply);
                    break;
            }
        }
    }

    private Messenger mSerivce;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//           String thingData= ((MessengerService._Binder) service).getStringFromBinder();
//            Log.e("信息","onServiceConnected:"+thingData);
            mSerivce = new Messenger(service);
            Message msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "客户端发送信息给你 请问收到吗？");

            msg.setData(data);
            //注意： 当客户端发送消息的时候  需要把接收服务端回复的Messenger通过message的replyTo参数传递给服务端
            msg.replyTo = mGetReplyMessenger;
            try {
                mSerivce.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        mess_tv = (TextView) findViewById(R.id.mess_tv);
        findViewById(R.id.mess_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessengerActivity.this, BookManagerActivity.class));
            }
        });
        mGetReplyMessenger = new Messenger(new MessengerHandler());
        bindService(new Intent(this, MessengerService.class), mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
