package reactnative.ng.smc.rxjava2demo.bitmapcache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2017/9/18.
 */

public class MemoryCacheUtils {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils() {
        /**
         * 把系统分配给应用程序的八分之一内存作为缓存大小
         */
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return (value.getRowBytes() * value.getHeight()) / 1024;
            }
        };
    }

    /**
     * 根据url从内存中获取图片
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        return lruCache.get(imageUrl);
    }

    /**
     * 根据url保存图片到lrucache集合中
     *
     * @param imageUrl
     * @param bitmap
     */
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl, bitmap);
    }
}
