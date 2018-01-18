package reactnative.ng.smc.rxjava2demo.bitmapcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/9/18.
 */

public class LocalCacheUtils {
    private final MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
        this.memoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 根据url获取图片
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5.encode(imageUrl.getBytes());
                File file = new File(Environment.getExternalStorageDirectory() + "news", fileName);
                if (file.exists()) {

                    FileInputStream is = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if (bitmap != null) {
                        //把本地保存到内存中
                        memoryCacheUtils.putBitmap(imageUrl, bitmap);
                    }
                    return bitmap;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 根据url保存图片
     */

    public void putBitmap(String imageUrl, Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5.encode(imageUrl.getBytes());
                File file = new File(Environment.getExternalStorageDirectory() + "news", fileName);
                File parentFile=file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();

                }
                if (!file.exists()){
                    file.createNewFile();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
