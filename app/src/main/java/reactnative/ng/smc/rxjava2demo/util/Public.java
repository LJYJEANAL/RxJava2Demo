package reactnative.ng.smc.rxjava2demo.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/30.
 */

public class Public {
    /**
     * 方法说明：返回屏幕宽度，单位：像素
     */
    public static int getScreenWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int  statusBarHeight=context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
       if (statusBarHeight<0){
           statusBarHeight= (int) Math.ceil(25 *context. getResources().getDisplayMetrics().density);
       }
        Log.e("信息","状态栏高度："+statusBarHeight);
        return statusBarHeight;
    }
}
