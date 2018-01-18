package reactnative.ng.smc.rxjava2demo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/24.
 */

public class MessengerService extends Service {
    private static final String tag = "信息";
    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_Service = 0;

    /**
     * 用于接收客户端发送来的信息
     */
    private static class MessengerHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    //收到客户端发送来的信息
                    Log.i(tag, "来自客户端:" + msg.getData().getString("msg"));
                    //往客户端回复信息
                    Messenger cliengMessengr=  msg.replyTo;
                    Message message=Message.obtain(null,MSG_FROM_Service);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","嘿，你的信息我已经收到，稍候回复您.");
                    message.setData(bundle);
                    try {
                        cliengMessengr.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    /**
     * 使用此方法进行通信的 需要在androidManifest 中配置
     * android:exported="false"
     * 这个属性用于指示该服务是否能够被其他应用程序组件调用或跟它交互。如果设置为true，则能够被调用或交互，否则不能。设置为false时，
     * 只有同一个应用程序的组件或带有相同用户ID的应用程序才能启动或绑定该服务。
     * 它的默认值依赖与该服务所包含的过滤器。没有过滤器则意味着该服务只能通过指定明确的类名来调用，
     * 这样就是说该服务只能在应用程序的内部使用（因为其他外部使用者不会知道该服务的类名），因此这种情况下，这个属性的默认值是false。
     * 另一方面，如果至少包含了一个过滤器，则意味着该服务可以给外部的其他应用提供服务，因此默认值是true。
     * 这个属性不是限制把服务暴露给其他应用程序的唯一方法。还可以使用权限来限制能够跟该服务交互的外部实体。
     */
    public class _Binder extends Binder {
        public String getStringFromBinder() {
            return "from Service";
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(new MessengerHander()).getBinder();
//        return  new _Binder();
    }
}
