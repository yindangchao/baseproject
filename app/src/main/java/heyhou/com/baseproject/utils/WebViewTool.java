package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 1 on 2016/7/7.
 */
public class WebViewTool {
    public static void init(Activity activity, WebView webView, String pageUrl) {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放


        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.loadDataWithBaseURL(null, pageUrl, "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }
//    WebViewInitTool.init(activity, webHtmlDetail);
//    WebSettings settings = webHtmlDetail.getSettings();
//    settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//    settings.setUseWideViewPort(true);
//    settings.setLoadWithOverviewMode(true);
//    // settings.setDefaultZoom(zoom)
//    // settings.setBuiltInZoomControls(true);
//    // webHtmlDetail.setInitialScale(0);
//
//    try {
//        webHtmlDetail.loadDataWithBaseURL(
//                null,
//                BasicTool.mapToJsonObj(proDetailMap)
//                        .getJSONObject("productDetail")
//                        .getString("specifications"), "text/html", "utf-8",
//                null);
//        webHtmlDetail.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webHtmlDetail.setWebChromeClient(new WebChromeClient());
//    } catch (JSONException e) {
//        e.printStackTrace();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}
