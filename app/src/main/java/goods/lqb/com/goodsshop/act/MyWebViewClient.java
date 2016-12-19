package goods.lqb.com.goodsshop.act;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import goods.lqb.com.goodsshop.interfac.WebViewListener;

public class MyWebViewClient extends WebViewClient {
	private WebViewListener webViewListener;
	private Context context;
	private  UrlChangeListen urlListen;

	public  void setUrlListen(UrlChangeListen urlListen){
		this.urlListen=urlListen;

	}
	public MyWebViewClient() {
	}
	public MyWebViewClient(Context context) {
		this.context=context;
	}
	public WebViewListener getWebViewListener() {
		return webViewListener;
	}

	public  interface  UrlChangeListen{
		void onUrlChange(String url);
	}

	public void setWebViewListener(WebViewListener webViewListener) {
		this.webViewListener = webViewListener;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// super.shouldOverrideUrlLoading(view, url);
		if (urlListen!=null)
			urlListen.onUrlChange(url);
		if (isFileUrl(url)) {
			if (this.context!=null) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				context.startActivity(intent);
			}
			return true;
		} else {
			view.loadUrl(url);
			return false;
		}
		// return webViewListener == null ? false :
		// webViewListener.shouldOverrideUrlLoading(view, url);
	}

	public static boolean isFileUrl(String url) {
		if (url==null) {
			return false;
		}
		for (String end : FileEnd) {
			if (url.endsWith(end.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static String[] FileEnd = { ".pdf", ".exe", ".apk", ".doc", ".ppt",
			".avi", ".mp4", ".3gp", ".gif", ".jpg", ".png", ".txt",".exl","mp3",".wm"};

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		if (webViewListener != null) {
			webViewListener.onPageFinished(view, url);
		}
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) { // 这个事件就是开始载入页面调用的，通常我们可以在这设定一个loading的页面，告诉用户程序在等待网络响应。
		super.onPageStarted(view, url, favicon);
//		String cookieString = MyGloble.getInstance().getCookieStr();
//		//System.out.println("sssID="+cookieString);
//		if (cookieString != null) {
//			CookieManager.getInstance().setCookie(url,
//					cookieString);
//			if (Constant.isLogcat) {
//				System.out.println("SET Cookie = "
//						+ MyGloble.getInstance().getCookieStr());
//				System.out.println("SET Cookie url = " + url);
//			}
//		}
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {// (报告错误信息)
		super.onReceivedError(view, errorCode, description, failingUrl);
		if (webViewListener != null) {
			webViewListener.onReceivedError(view, errorCode, description,
					failingUrl);
		}
	}

	@Override
	public void onLoadResource(WebView view, String url) {// 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
		super.onLoadResource(view, url);
		if (url.contains("php")) { // 针对Ajax请求新的php页面
//			String cookieString = MyGloble.getInstance().getCookieStr();
//			if (cookieString != null) {
//				CookieManager.getInstance().setCookie(url, cookieString);
//				if (Constant.isLogcat) {
//					System.out.println("Load Cookie = " + cookieString);
//					System.out.println("Load Cookie URL=>" + url);
//				}
//			}
		}
	}

	// onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String
	// host,String realm)//（获取返回信息授权请求）

	// onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
	// //重写此方法可以让webview处理https请求。

	// onScaleChanged(WebView view, float oldScale, float newScale) //
	// (WebView发生改变时调用)

	// onUnhandledKeyEvent(WebView view, KeyEvent event) //（Key事件未被加载时调用）

	// shouldOverrideKeyEvent(WebView view, KeyEvent
	// event)//重写此方法才能够处理在浏览器中的按键事件。

	// shouldOverrideUrlLoading(WebView view, String url)
	// 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
}
