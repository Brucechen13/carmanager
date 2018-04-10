package com.cc.carmanager;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.cc.carmanager.util.LoginHelper;
import com.cc.carmanager.util.ScreenUtil;
import com.lzy.ninegrid.NineGridView;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by chenc on 2017/10/24.
 */
public class MyApplication extends Application {
    public static int width = 0;
    public static int height = 0;
    public static float density = 0;

    private static Context context;

    /**
     * 静态方法获取上下文
     * @return
     */
    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        width = ScreenUtil.getWidth(this);
        height = ScreenUtil.getHeight(this);
        density = ScreenUtil.getDensity(this);
        VolleyLog.DEBUG = true;
//        Config.DEBUG = true;
//        QueuedWork.isUseThreadPool = false;
//        UMShareAPI.get(this);
        initGridImage();
    }

    private void initGridImage() {
        NineGridView.setImageLoader(new VolleyImageLoader());
    }

    /**
     * Picasso 加载
     */
    private class VolleyImageLoader implements NineGridView.ImageLoader {

        private RequestQueue queue;
        private ImageLoader imageLoader;

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            if (imageLoader == null) {
                queue = Volley.newRequestQueue(context);
                //创建ImageLoader对象，参数（加入请求队列，自定义缓存机制）
                imageLoader = new ImageLoader(queue, new BitmapCache());
            }
            //获取图片监听器 参数（要显示的ImageView控件，默认显示的图片，加载错误显示的图片）
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                    R.drawable.ic_default_image,
                    R.drawable.ic_search);
            imageLoader.get(url, listener);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    //自定义图片缓存BitmapCache
    class BitmapCache implements ImageLoader.ImageCache {

        //使用安卓提供的缓存机制
        private LruCache<String, Bitmap> mCache;

        //重写构造方法
        private BitmapCache() {
            int maxSize = 10 * 1024 * 1024;  //缓存大小为10兆
            mCache = new LruCache<String, Bitmap>(maxSize);

        }
        @Override
        public Bitmap getBitmap(String s) {
            return mCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mCache.put(s, bitmap);

        }
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("4230751943", "592a09c4f4c9573fdd6d67a7672cf7b3", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106433615", "EyF8HXWSu3ciLP1X");
    }

}

