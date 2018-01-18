package reactnative.ng.smc.rxjava2demo.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import reactnative.ng.smc.rxjava2demo.R;

/**
 * Created by Administrator on 2017/11/9.
 */

public class TCPClientActivity extends AppCompatActivity {

    private TextView mMessageTextView;
    private Button mSendButton;
    private EditText mMessageEditText;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mMessageTextView.setText(mMessageTextView.getText()+(String)msg.obj);
                    break;
                case 1:
                    mSendButton.setEnabled(true);
                    break;
            }
        }
    };
    private Socket mClientSocket;
    private PrintWriter printStream;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = (TextView) findViewById(R.id.msg_container);
        mSendButton = (Button) findViewById(R.id.send);
        mMessageEditText = (EditText) findViewById(R.id.msg);
        Intent intent = new Intent(this, TCPServerService.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }


        }).start();
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String msg= mMessageEditText.getText().toString();
               if (!TextUtils.isEmpty(msg)&&printStream!=null){
                   printStream.println(msg);
                   mMessageEditText.setText("");
                   String time=formatDateTime(System.currentTimeMillis());
                   String showedMsg="self01 "+time+":"+msg+"\n";
                   mMessageTextView.setText(mMessageTextView.getText()+showedMsg);

               }
            }
        });

    }
    private String formatDateTime(long time){
        return  new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));

    }
    //链接
    private void connectTCPServer() {
        Socket socket=null;
        while (socket==null){

            try {
                socket = new Socket("localhost",8688);
                mClientSocket=socket;
                printStream = new PrintWriter(new BufferedWriter
                        (new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(1);
                Log.e("信息","客户端链接成功");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }
        //接收服务端信息
        try {
            BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()){
                String  msg=bf.readLine();
                if (msg!=null){
                    String time=formatDateTime(System.currentTimeMillis());
                    String showedMsg="server "+time+":"+msg+"\n";
                    mHandler.obtainMessage(0,showedMsg).sendToTarget();
                }
            }
            printStream.close();
            bf.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket!=null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
