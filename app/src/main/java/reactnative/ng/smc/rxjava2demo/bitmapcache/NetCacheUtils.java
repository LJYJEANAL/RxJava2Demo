package reactnative.ng.smc.rxjava2demo.bitmapcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.R.attr.bitmap;

/**
 * Created by Administrator on 2017/9/18.
 */

public class NetCacheUtils {
    public static final int SUCESS = 1;
    public static final int FAIL = 2;
    private final Handler handler;
    private final LocalCacheUtils localCacheUtils;
    private final MemoryCacheUtils memoryCacheUtils;
    private ExecutorService service;

    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.handler = handler;
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
        service = Executors.newFixedThreadPool(10);

    }

    public void getBitmapFomNet(String imageUrl, int position) {
        service.execute(new MyRunnable(imageUrl,position));
    }

    class MyRunnable implements Runnable {
        private final String imageUrl;
        private final int position;

        public MyRunnable(String imageUrl, int position) {
            this.imageUrl = imageUrl;
            this.position = position;
        }

        @Override
        public void run() {
            try {
                URL url=new URL(imageUrl);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                connection.connect();
                int code=connection.getResponseCode();
                if (code==200){
                    InputStream is=connection.getInputStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    Message msg=Message.obtain();
                    msg.what=SUCESS;
                    msg.arg1=position;
                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                    memoryCacheUtils.putBitmap(imageUrl,bitmap);
                    localCacheUtils.putBitmap(imageUrl,bitmap);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Message msg=Message.obtain();
                msg.what=FAIL;
                msg.arg1=position;
                msg.obj=bitmap;
                handler.sendMessage(msg);
            }

        }
    }
}
