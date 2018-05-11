package com.hfxief.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hfxief.app.BaseApplication;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * LongFor
 * com.hfxief.utils.cache
 *
 * @Author: xie
 * @Time: 2017/11/1 10:08
 * @Description: 大量读取本地图标，每次都去读取图片，由于读取文件需要硬件操作，速度较慢，会导致性能较低，所以我们考虑将图片缓存起来
 */


public class CacheImage {
    private Map<String, SoftReference<Bitmap>> imgCache = new HashMap<>();

    public void addBitmapToCache(String path) {
        //强引用Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        SoftReference<Bitmap> softBitmap = new SoftReference<>(bitmap);
        imgCache.put(path, softBitmap);
    }

    public void addBitmapToCache(int resourse) {
        //强引用Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), resourse);
        SoftReference<Bitmap> softBitmap = new SoftReference<>(bitmap);
        imgCache.put(resourse + "", softBitmap);
    }

    public Bitmap getBitmapByCache(String path) {
        SoftReference<Bitmap> softBitmap = imgCache.get(path);
        if (softBitmap == null) return null;
        Bitmap bitmap = softBitmap.get();
        return bitmap;
    }

   public void clear(){
       imgCache.clear();
       imgCache=null;
   }
}
