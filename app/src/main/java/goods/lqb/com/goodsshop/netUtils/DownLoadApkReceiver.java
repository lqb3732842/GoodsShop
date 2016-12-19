package goods.lqb.com.goodsshop.netUtils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * 调用系统的DownloadManager 下载apk文件,并能打开
 * 静态注册广播	android.intent.action.DOWNLOAD_COMPLETE
 * @author Administrator
 *
 */
public class DownLoadApkReceiver extends BroadcastReceiver {
	private static long enqueue;
	private static DownloadManager dm;
	/**
	 * 路径名
	 */
	private static String dir = "/download/";
	/**
	 * 文件名
	 */
	private static String fileName = "goods.apk";
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
		String action = intent.getAction();
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
			Query query = new Query();
			query.setFilterById(enqueue);
			if (dm == null) {
				dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			}
			Cursor c = dm.query(query);
			if (c.moveToFirst()) {
				int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
				if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
					String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
					Uri fileUri = Uri.parse(uriString);
					uriString = fileUri.getPath();
					File f;
					if(uriString == null || uriString == "") {
						f = new File(Environment.getExternalStoragePublicDirectory(dir), fileName);
					} else {
						f = new File(uriString);
					}
					openFile(context,f);
				}
			}
		}
		} catch (Exception e) {
		}
	}
	
	
	
	@SuppressLint("NewApi")
	/**
	 * 开始下载
	 * @param url
	 */
	public static void startRequest(Context ctx, String url) {
		//Log.v(TAG, "download url = " + url);
		if (dm == null) {
			dm = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
		}
		File f = new File(dir, fileName);
		if (f.exists()) {
			f.delete();
		}
		Request request = new Request(Uri.parse(url));
		// 设置允许使用的网络类型，这里是移动网络和wifi都可以
		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);
		// 向界面发出通知
		request.setShowRunningNotification(true);
		// 设置标题
		request.setTitle("下载更新");
		// 显示下载界面
		request.setVisibleInDownloadsUi(true);
		
		File old = new File(Environment.getExternalStoragePublicDirectory(dir), fileName);
		try {
			if (old.exists()) {
				old.delete();
			}
		} catch (Exception e) {
			
		}
		
		// 设置下载路径 /storage/sdcard0/download
		request.setDestinationInExternalPublicDir(dir,fileName);
		enqueue = dm.enqueue(request);
	}
	
	/**
	 * 打开APK程序代码
	 * @param ctx
	 * @param file 文件
	 */
	public static void openFile(Context ctx, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	} 

}
