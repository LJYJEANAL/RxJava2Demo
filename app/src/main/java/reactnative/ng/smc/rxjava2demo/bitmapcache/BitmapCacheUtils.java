package reactnative.ng.smc.rxjava2demo.bitmapcache;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 *Created by Administrator on 2017/9/18.
 */

public class BitmapCacheUtils {
    /**
     * 在listview的类中 用handler接收消息
     */
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case NetCacheUtils.SUCESS:
//                    int position=msg.arg1;
//                    Bitmap b= (Bitmap) msg.obj;
//
//                    break;
//                case NetCacheUtils.FAIL:
//                    break;
//            }
//        }
//    };
            //viewHolder.iv_icon.setTag(position);
//            Bitmap bitmap=bitmapCacheUtils.getBitmap(imageUrl,positon);


    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;
    public BitmapCacheUtils(Handler handler){
        memoryCacheUtils=new MemoryCacheUtils();
        localCacheUtils=new LocalCacheUtils(memoryCacheUtils);
        netCacheUtils=new NetCacheUtils(handler,localCacheUtils,memoryCacheUtils);

    }

    /***
     * 三级缓存策略 ：
     *   先从内存中取
     *   内存没有再从本地中取，向内存中保存一份
     *   本地没有 直接网络获取 向内存保存一份 本地保存一份
     * @param imageUlr
     * @param position
     * @return
     */
    public Bitmap getBitmap(String imageUlr,int position){
        // 先从内存中取图片
        if (memoryCacheUtils!=null){
            Bitmap bitmap=memoryCacheUtils.getBitmapFromUrl(imageUlr);
            if (bitmap!=null)
                return bitmap;
        }
        // 从本地取
        if (localCacheUtils!=null){
          Bitmap bitmap=  localCacheUtils.getBitmapFromUrl(imageUlr);
            if (bitmap!=null)
                return bitmap;
        }
        //从网络取
        netCacheUtils.getBitmapFomNet(imageUlr,position);
        return null;
    }


}
