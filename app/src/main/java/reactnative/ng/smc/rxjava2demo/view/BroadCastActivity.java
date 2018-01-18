package reactnative.ng.smc.rxjava2demo.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import reactnative.ng.smc.rxjava2demo.R;

public class BroadCastActivity extends AppCompatActivity {

    private MyRecevier receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        Button send = (Button) findViewById(R.id.send);
        // 接收注册广播
        IntentFilter intentFilter = new IntentFilter("WXPayEntryActivityTOPaymentActivity");
        receiver = new MyRecevier();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("WXPayEntryActivityTOPaymentActivity");
                LocalBroadcastManager.getInstance(BroadCastActivity.this).sendBroadcast(intent);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
