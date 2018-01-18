package reactnative.ng.smc.rxjava2demo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/20.
 */

public class MyRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Title");
        builder.setMessage("message");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });


        AlertDialog dialog=builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        Toast.makeText(context,"onReceive",Toast.LENGTH_SHORT).show();
    }
}
