package goods.lqb.com.goodsshop.act;


import android.view.View;
import android.webkit.WebChromeClient;
import goods.lqb.com.goodsshop.interfac.WebChromeViewListener;


public class MyWebChromeClient extends WebChromeClient {
	private WebChromeViewListener listener = null;
	
	@Override
	public void onShowCustomView(View view, CustomViewCallback callback) {

		if (listener != null) {
			listener.onShowCustomView(view, callback);
		}
	}

	@Override
	public void onHideCustomView() {
		
		if (listener != null) {
			listener.onHideCustomView();
		}
	}


	public WebChromeViewListener getListener() {
		return listener;
	}

	public void setListener(WebChromeViewListener listener) {
		this.listener = listener;
	}


}
