package heyhou.com.baseproject.glidetolls;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.Constant;
import heyhou.com.baseproject.utils.AppUtil;

/**
 * Created by 1 on 2016/10/10.
 */
public class GlideImgManager {

    private static final int DEFAULT_SIZE = 660;

    private GlideImgManager() {
    }

    /**
     * 加载图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param iv      图片控件
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        loadImage(context, url, iv, new GlideConfigInfo(GlideConfigType.IMG_TYPE_DEFAULT, 0));
    }


    /**
     * 加载图片
     *
     * @param context         上下文
     * @param url             图片链接
     * @param iv              图片控件
     * @param glideConfigInfo
     * @see GlideConfigInfo
     */
    public synchronized static void loadImage(final Context context, final String url, final ImageView iv, final GlideConfigInfo glideConfigInfo) {
        if (iv == null) {
            return;
        }
        if (iv.getWidth() <= 0 && iv.getParent() != null) {
            final ViewTreeObserver finalObserver = iv.getViewTreeObserver();
            finalObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
//                    DebugTool.warn("onPreDraw:" + iv.getWidth());
                    if (finalObserver.isAlive()) {
                        finalObserver.removeOnPreDrawListener(this);
                    }
                    ViewTreeObserver observer = iv.getViewTreeObserver();
                    if (observer.isAlive()) {
                        observer.removeOnPreDrawListener(this);
                    }
                    load(context, url, iv, iv.getWidth(), glideConfigInfo);
                    return true;
                }
            });
        } else {
//        int width = measureView(iv);
//        DebugTool.warn("GlideImgManager:width:" + width);
            load(context, url, iv, iv.getWidth(), glideConfigInfo);
        }
    }

    private static int measureView(ImageView iv) {
        int viewWidth = iv.getWidth();
        if (viewWidth > 0) {
            return viewWidth;
        }
        if (iv.getMeasuredWidth() > 0) {
            return iv.getMeasuredWidth();
        }
        if (iv.getLayoutParams() != null) {
            int paramsWidth = iv.getLayoutParams().width;
            if (paramsWidth > 0) {
                return paramsWidth;
            }
        }
//        View.MeasureSpec.UNSPECIFIED

        if (viewWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (iv.getParent() == null) {
                return DEFAULT_SIZE;
            }
            int parentWidth = ((ViewGroup) iv.getParent()).getLayoutParams().width;
            if (parentWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                return DEFAULT_SIZE;
            } else if (parentWidth > 0) {
                return parentWidth;
            } else {
                int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                iv.measure(width, height);
//                DebugTool.warn("GlideImgManager:Parent():WRAP_CONTENT:" + iv.getMeasuredWidth());
                return iv.getMeasuredWidth();
            }
        } else {
            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            iv.measure(width, height);
//            DebugTool.warn("GlideImgManager:View():WRAP_CONTENT:" + iv.getMeasuredWidth());
            return iv.getMeasuredWidth();
        }
    }


    private static void load(Context context, String url, ImageView iv, int viewWidth, GlideConfigInfo glideConfigInfo) {
        load(context, url, iv, viewWidth, glideConfigInfo, 0, 0);
    }

    private static void load(Context context, String url, final ImageView iv, int viewWidth, GlideConfigInfo glideConfigInfo, int placeholderRes, int errorRes) {
//        if (glideConfigInfo != null && !glideConfigInfo.isUseSelfScaleType()) {
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        }

        RequestManager rm = null;
        if (context instanceof Activity) {
            rm = Glide.with((Activity)(context));
        } else {
            rm = Glide.with(context);
        }

        DrawableRequestBuilder drawableRequestBuilder = commLoadFactory(iv, rm, url, glideConfigInfo, viewWidth, placeholderRes, errorRes);
        switch (glideConfigInfo.getConfigType()) {
            case IMG_TYPE_CIRCLE:
                if (glideConfigInfo.isCenterCrop()) {
                    drawableRequestBuilder = drawableRequestBuilder.transform(new CenterCrop(context), new GlideCircleTransform(context));
                } else {
                    drawableRequestBuilder = drawableRequestBuilder.transform(new GlideCircleTransform(context));
                }
                break;
            case IMG_TYPE_DEFAULT_ROUND:
            case IMG_TYPE_LANDSCAPE_ROUND:
            case IMG_TYPE_PORTRAIT_ROUND:
            case IMG_TYPE_HEAD_ROUND:
                if (glideConfigInfo.isCenterCrop()) {
                    drawableRequestBuilder = drawableRequestBuilder.transform(new CenterCrop(context), new GlideRoundTransform(context, glideConfigInfo.getRoundSize()));
                } else {
                    drawableRequestBuilder = drawableRequestBuilder.transform(new GlideRoundTransform(context, glideConfigInfo.getRoundSize()));
                }
                break;
            default:
                if (glideConfigInfo.isCenterCrop()) {
                    drawableRequestBuilder = drawableRequestBuilder.transform(new CenterCrop(context));
                }
                break;
        }

        if (iv != null) {
            drawableRequestBuilder.into(iv);
        }
    }

    private static DrawableRequestBuilder commLoadFactory(ImageView iv, RequestManager glide, String url, GlideConfigInfo glideConfigInfo, int viewWidth, int placeholderRes, int errorRes) {
        if (!TextUtils.isEmpty(url) && !url.contains(Constant.BITMAP_THUMBNAIL_STR_DEFAULT) && url.contains("http://res.heyhou.com/")) {
            if (viewWidth > 0) {
                url += AppUtil.getApplicationContext().getString(R.string.glide_thumbnail, viewWidth + "x");
            } else {
                url += Constant.BITMAP_THUMBNAIL_STR_660X;
            }
        }
//        DebugTool.warn("GlideImgManager:loadImage:url:" + url);
        int emptyImg = 0;
        int erroImg = 0;
//        DebugTool.warn("commLoadFactory:" + url);
        switch (glideConfigInfo.getConfigType()) {
            case IMG_TYPE_HEAD:
                emptyImg = R.drawable.bg_default_drawable;
                erroImg = R.drawable.bg_default_drawable;
                break;
            case IMG_TYPE_LANDSCAPE:
                emptyImg = R.drawable.bg_default_drawable;
                erroImg = R.drawable.bg_default_drawable;
                break;
            case IMG_TYPE_PORTRAIT:
                emptyImg = R.drawable.bg_default_drawable;
                erroImg = R.drawable.bg_default_drawable;
                break;
            case IMG_TYPE_CIRCLE:
                emptyImg = R.mipmap.img_default_head;
                erroImg = R.mipmap.img_default_head;
                break;
            case IMG_TYPE_LANDSCAPE_ROUND:
                emptyImg = R.drawable.bg_default_round_drawable;
                erroImg = R.drawable.bg_default_round_drawable;
                break;
            case IMG_TYPE_PORTRAIT_ROUND:
                emptyImg = R.drawable.bg_default_round_drawable;
                erroImg = R.drawable.bg_default_round_drawable;
                break;
            case IMG_TYPE_HEAD_ROUND:
                emptyImg = R.drawable.bg_default_round_drawable;
                erroImg = R.drawable.bg_default_round_drawable;
                break;
            case IMG_TYPE_TIDAL_PLAY:
                emptyImg = R.drawable.bg_default_drawable;
                erroImg = R.drawable.bg_default_drawable;
                break;

        }
        if (errorRes != 0) {
            emptyImg = errorRes;
        }
        if (placeholderRes != 0) {
            emptyImg = placeholderRes;
        }

        if (glideConfigInfo.getDefaultImgRes() != -1) {
            emptyImg = glideConfigInfo.getDefaultImgRes();
            erroImg = emptyImg;
        }

        return glide.load(url).placeholder(emptyImg).error(erroImg);
    }



    /**
     * 加载图片
     *
     * @param context        上下文
     * @param url            图片链接
     * @param iv             图片控件
     * @param placeholderRes 默认加载图片
     */
    public static void loadImage(Context context, String url, ImageView iv, @DrawableRes int placeholderRes) {
        loadImage(context, url, iv, new GlideConfigInfo(GlideConfigType.IMG_TYPE_DEFAULT), placeholderRes, 0);
    }


    /**
     * 加载图片
     *
     * @param context        上下文
     * @param url            图片链接
     * @param iv             图片控件
     * @param placeholderRes 默认加载图片
     * @param errorRes       默认失败加载图片
     */
    public static void loadImage(final Context context, final String url, final ImageView iv, @DrawableRes final int placeholderRes, @DrawableRes final int errorRes) {
        loadImage(context, url, iv, new GlideConfigInfo(GlideConfigType.IMG_TYPE_DEFAULT), placeholderRes, errorRes);
    }


    /**
     * 加载图片
     *
     * @param context         上下文
     * @param url             图片链接
     * @param iv              图片控件
     * @param glideConfigInfo 参数
     * @param placeholderRes  默认加载图片
     * @see GlideConfigInfo
     */
    public static void loadImage(Context context, String url, ImageView iv, GlideConfigInfo glideConfigInfo, @DrawableRes int placeholderRes) {
        loadImage(context, url, iv, glideConfigInfo, placeholderRes, 0);
    }

    /**
     * 加载图片
     *
     * @param context         上下文
     * @param url             图片链接
     * @param iv              图片控件
     * @param glideConfigInfo 参数
     * @param placeholderRes  默认加载图片
     * @param errorRes        默认失败加载图片
     * @see GlideConfigInfo
     */
    public static void loadImage(final Context context, final String url, final ImageView iv, final GlideConfigInfo glideConfigInfo, @DrawableRes final int placeholderRes, @DrawableRes final int errorRes) {
        if (iv.getWidth() == 0 && iv.getParent() != null) {
            final ViewTreeObserver finalObserver = iv.getViewTreeObserver();
            finalObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
//                    DebugTool.warn("onPreDraw:" + iv.getWidth());
                    if (finalObserver.isAlive()) {
                        finalObserver.removeOnPreDrawListener(this);
                    }
                    ViewTreeObserver observer = iv.getViewTreeObserver();
                    if (observer.isAlive()) {
                        observer.removeOnPreDrawListener(this);
                    }
                    load(context, url, iv, iv.getWidth(), glideConfigInfo, placeholderRes, errorRes);
                    return true;
                }
            });
        } else {
            load(context, url, iv, iv.getWidth(), glideConfigInfo, placeholderRes, errorRes);
        }
    }


}