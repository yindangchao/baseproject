package heyhou.com.baseproject.glidetolls;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by lky on 2017/6/7.
 */

public class GlideConfiguration implements GlideModule{


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
