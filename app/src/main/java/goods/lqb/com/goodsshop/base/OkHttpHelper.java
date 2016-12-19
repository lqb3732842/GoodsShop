package goods.lqb.com.goodsshop.base;

import android.os.Handler;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 05 on 2016/12/10.
 */

public class OkHttpHelper {

    private static final int NET_CONNECT_TIMEOUT = 5;//链接超时
    private static final int NET_WRITE_TIMEOUT = 200;//上传超时
    private static final int NET_READ_TIMEOUT = 500;//下载超时
    private static OkHttpClient mOkHttpClient;
    public static int NET_ERR = 1001;
    public static int RESPONE_DATA_ERR = 1002;

    public interface OkHttpRespon {
        void netSucc(JSONObject json, int succTag);

        void netFail(int rcode, String resData, int failTag);
    }

    public static OkHttpClient getOkHttpInstant() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpHelper.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                    mOkHttpClient = mOkHttpClient.newBuilder().connectTimeout(NET_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(NET_WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(NET_READ_TIMEOUT, TimeUnit.SECONDS).build();
                }
            }
        }
        return mOkHttpClient;
    }

    public static void okHttpGet(String url, final OkHttpRespon callBack, final Handler mainThreadHandle, final int succTag, final int failTag) {
        mOkHttpClient = getOkHttpInstant();
        //创建一个Request
        final Request request = new Request.Builder().url(url).build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                dealFail(callBack, mainThreadHandle, failTag);
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                dealSucc(response, callBack, mainThreadHandle, succTag, failTag);
            }
        });
    }

    private static void dealFail(final OkHttpRespon callBack, Handler handler, final int failTag) {

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.netFail(NET_ERR, null, failTag);
                }
            });
        }
    }

    private static void dealSucc(final Response response, final OkHttpRespon callBack, Handler handler, final int succTag, final int failTag) throws IOException {
        final String res = response.body().string();

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject object = new JSONObject(res);
                                callBack.netSucc(object, succTag);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callBack.netFail(RESPONE_DATA_ERR, res, failTag);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void okHttpPost(String url, final OkHttpRespon callBack, JSONObject pmsJson, final Handler handler, final int succTag, final int failTag) {
        mOkHttpClient = getOkHttpInstant();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("pms", pmsJson.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dealFail(callBack, handler, failTag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dealSucc(response, callBack, handler, succTag, failTag);
            }
        });
    }
}
