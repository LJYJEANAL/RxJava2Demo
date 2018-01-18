package reactnative.ng.smc.rxjava2demo.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/9.
 */

public class TCPServerService extends Service {
    private boolean mIsServerDestory = false;
    private String[] mDefinedMessages = new String[]{
            "01 ",
            "02",
            "03",
            "04",
            "001 ",
            "002",
            "003",
            "004",
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();

    }

    private void responseClient(Socket client) throws IOException {
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到！");
        while (!mIsServerDestory) {
            String str = in.readLine();
            Log.i("信息", "来自客户端消息：" + str);
            if (str == null) {//客户端断开
                out.close();
                in.close();
                client.close();
                break;
            }
            //服务端随机回复
            int i = new Random().nextInt(mDefinedMessages.length);
            out.println(mDefinedMessages[i]);
            Log.i("信息", "来自服务端回复消息：" + mDefinedMessages[i]);
            if (mIsServerDestory){
                out.close();
                in.close();
                client.close();
            }
//
        }
    }


    @Override
    public void onDestroy() {
        mIsServerDestory = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("信息", "失败");
                return;
            }
            while (!mIsServerDestory) {
                try {
                    final Socket client = serverSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("信息", "TcpServer：" + mIsServerDestory);
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
