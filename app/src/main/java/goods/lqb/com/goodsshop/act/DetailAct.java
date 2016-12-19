package goods.lqb.com.goodsshop.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.webkit.*;
import android.widget.TextView;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.base.BaseAct;
import goods.lqb.com.goodsshop.base.Constant;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;
import goods.lqb.com.goodsshop.comutils.ToastHelper;

/**
 * 显示商品详情界面
 *
 * @author Administrator
 */
public class DetailAct extends BaseActionBarAct implements OnClickListener {
    private WebView webview0;
    private boolean loadCompleted = false;
    private String url = "";//
    private Handler handler = new Handler();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private String mUHM_kl;
    private String mGood_kl;
    private final String TAO_BAO_SHOP = "taobao://shop.m.taobao.com/";
    private final String TAO_BAO_PAG = "com.taobao.taobao";
    private TextView goBuy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitleMSG("商品详细");
        setLeftDarw(R.mipmap.back_arro);
        url = getIntent().getStringExtra("url");
        mUHM_kl = getIntent().getStringExtra("yhq_kl");
        mGood_kl = getIntent().getStringExtra("yhq_goods");
        String money=getIntent().getStringExtra("money");
        //下拉
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw);
        //设置加载圈的背景颜色
        setSwReflashColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doReflash();
            }
        });

        goBuy = (TextView) findViewById(R.id.go_buy);
        goBuy.setOnClickListener(this);
//        if (money!=null&&money.length()>0&&!"0".equals(money))
//            goBuy.setText("领券购买");
//        else
//            goBuy.setText("去购买");
        findViewById(R.id.copy).setOnClickListener(this);


        webview0 = (WebView) findViewById(R.id.webview0);
        webview0.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                          super.onPageStarted(view, url, favicon);
                                      }

                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                          super.shouldOverrideUrlLoading(view, url);
                                          //点击默认购物车图标跳转淘宝
                                          if ((url.contains("https://h5.m.taobao.com/")&&!url.contains("detail"))||url.endsWith("icart")) {
                                              clickBuy();
                                              return true;
                                          } else
                                              return false;
                                      }
                                  }

        );
        WebSettings webSettings = webview0.getSettings();//支持js
        webSettings.setJavaScriptEnabled(true);        //支持对网页缩放
        webSettings.setBuiltInZoomControls(true);        //默认缩放模式
        //设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);

        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小，我设的是8M
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置去消弹窗
        WebChromeClient webchromeclient = new WebChromeClient() {
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }
        };
        webview0.setWebChromeClient(webchromeclient);

        doReflash();
    }

    private void startPage() {
        loadCompleted = false;
        if (webview0.getUrl() == null || webview0.getUrl().length() < 5)
            webview0.loadUrl(url);
        else
            webview0.loadUrl(webview0.getUrl());
        // 加载本地html
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadCompleted == false) {
                    setLoadingComplet();
                }
            }
        }, Constant.MAX_WEB_LOAD);
    }

    private void setLoadingComplet() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mOnScrollChangedListener = setWebSwipRefresh(webview0, mSwipeRefreshLayout);
    }


    @Override
    public void onStop() {
        super.onStop();
        //移除listener
        mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
    }

    /**
     * //设置webVeiw只有到顶才可以下拉
     *
     * @param web webView
     * @param sw
     * @return
     */
    public static ViewTreeObserver.OnScrollChangedListener setWebSwipRefresh(final WebView web, final SwipeRefreshLayout sw) {
        if (web == null || sw == null)
            return null;
        //设置webVeiw只有到顶才可以下拉
        ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (web.getScrollY() == 0)
                    sw.setEnabled(true);
                else
                    sw.setEnabled(false);
            }
        };
        sw.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener);
        return mOnScrollChangedListener;

    }

    @Override
    public void onBackPressed() {
        if (webview0.canGoBack()) {
            webview0.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.go_buy:
                clickBuy();
                break;
            case R.id.copy:
                setKouLing();
                ToastHelper.toastLong("优惠券口令已经复制,打开淘宝能自动跳转领取界面哦", true, this);
                break;
        }
    }

    private void clickBuy() {
            setKouLing();
        if (DeviceHelp.checkPackage(TAO_BAO_PAG, this)) {
            startBuy(TAO_BAO_SHOP);
        } else {
            ToastHelper.toastLong("安装手机淘宝可免登陆一键购物哦", true, this);
            startBuy(url);
        }
    }

    private void setKouLing() {
        if (mUHM_kl!=null&&mUHM_kl.length()>3)
            DeviceHelp.setSystemCopy(mUHM_kl, this);
        else
            DeviceHelp.setSystemCopy(mGood_kl, this);
    }

    private void startBuy(String url) {
        if (TAO_BAO_SHOP.equals(url)){
            ToastHelper.toastLong("正在打开淘宝,请稍后...",true,this);
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview0 != null) {
            webview0.removeAllViews();
            webview0.destroy();
        }
    }

    private void doReflash() {
        BaseAct.setSwipReflashStart(mSwipeRefreshLayout);
        if (DeviceHelp.isNetworkAvai(this) == false) {
            ToastHelper.toastShort("网络不可用,请检查网络", true, this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setLoadingComplet();
                    loadCompleted = true;
                }
            }, Constant.MAX_WEB_LOAD);
            return;
        }
        startPage();
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_about;
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {
    }
}
