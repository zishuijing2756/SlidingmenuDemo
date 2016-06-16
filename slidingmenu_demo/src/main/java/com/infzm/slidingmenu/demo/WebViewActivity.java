package com.infzm.slidingmenu.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG                = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout  swipeRefreshLayout = null;
    private WebView             webView            = null;
    private static String       url                = null;
    private ProgressBar         progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** 设置全屏 */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_webview);
        swipeRefreshLayout.setOnRefreshListener(this);
        /** 顶部刷新的样式 */
        swipeRefreshLayout.setColorScheme(R.color.light_blue, R.color.green, R.color.orange, R.color.red);

        url = getIntent().getData().toString();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(url)) {
            initWebView(url);
        }
    }

    private void initWebView(String url) {
        try {
            if (webView == null) {
                webView = (WebView) findViewById(R.id.wv_web);
                webView.setWebChromeClient(new WebChromeClient() {

                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        if (newProgress == 100) {
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            if (View.INVISIBLE == progressBar.getVisibility()) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            progressBar.setProgress(newProgress);
                        }
                        super.onProgressChanged(view, newProgress);
                    }
                });
                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        return super.shouldOverrideUrlLoading(view, url);
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                        view.loadUrl("file:///android_asset/error.html");
                    }

                });
                WebSettings setting = webView.getSettings();
                setting.setJavaScriptEnabled(true);
                setting.setDomStorageEnabled(true);
                /** 支持通过JS打开新窗口 */
                setting.setJavaScriptCanOpenWindowsAutomatically(true);
                /** 设置满屏 */
                setting.setLoadWithOverviewMode(true);
                setting.setUseWideViewPort(true);

                /** 设置布局方式 */
                setting.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
                /** 允许访问文件 */
                setting.setAllowFileAccess(true);
                /** 设置显示缩放按钮 */
                setting.setBuiltInZoomControls(true);
                /** 支持缩放 */
                setting.setSupportZoom(true);
                setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
                /** 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上(View.SCROLLBARS_INSIDE_OVERLAY) */
                webView.setScrollBarStyle(0);
                /** 设置webView的浏览器标识User-Agent */
                String ua = setting.getUserAgentString();
                setting.setUserAgentString(ua + ";joycool");

            }
            webView.loadUrl(url);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {

            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                if (!TextUtils.isEmpty(url)) {
                    initWebView(url);
                }
            }
        }, 500);
    }

    @Override
    public void finish() {
        super.finish();
    }

}
