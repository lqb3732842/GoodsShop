package goods.lqb.com.goodsshop.interfac;

import android.webkit.WebView;

/**
 * Created by 05 on 2016/12/10.
 */

public interface WebViewListener {

    void onPageFinished(WebView view, String url);

    //  public boolean shouldOverrideUrlLoading(WebView view, String url);
    void onReceivedError(WebView view, int errorCode, String description, String failingUrl);
}
