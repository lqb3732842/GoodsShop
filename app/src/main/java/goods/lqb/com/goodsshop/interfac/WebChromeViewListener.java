package goods.lqb.com.goodsshop.interfac;

import android.view.View;
import android.webkit.WebChromeClient;

/**
 * Created by 05 on 2016/12/10.
 */

public interface WebChromeViewListener {
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback);

    public void onHideCustomView();
}
