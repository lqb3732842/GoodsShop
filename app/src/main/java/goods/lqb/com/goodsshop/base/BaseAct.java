package goods.lqb.com.goodsshop.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;
import goods.lqb.com.goodsshop.comutils.DialogHelper;
import goods.lqb.com.goodsshop.netUtils.DownLoadApkReceiver;
import goods.lqb.com.goodsshop.wedige.swipyrefreshlib.SwipyRefreshLayout;

/**
 * Created by sks on 2016/12/9.
 */

public abstract class BaseAct extends AppCompatActivity {
    private Dialog updateDialog;
    public Activity baseAct;
    private InputMethodManager mInputMethodManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏颜色(5.0之后有效)
        DeviceHelp.setStatiuBarClor(this, 0xffFF6537);
        baseAct = this;
    }

    /**
     * 刷新
     */
    public static void setSwipReflashStart(final SwipeRefreshLayout swipReflash) {
        if (swipReflash == null) return;
        swipReflash.post(new Runnable() {
            @Override
            public void run() {
                swipReflash.setRefreshing(true);
            }
        });
    }

    /**
     * 刷新
     */
    public static void setSwipReflashStart(final SwipyRefreshLayout swipReflash) {
        if (swipReflash == null) return;
        swipReflash.post(new Runnable() {
            @Override
            public void run() {
                swipReflash.setRefreshing(true);
            }
        });
        swipReflash.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipReflash.setRefreshing(false);
            }
        },Constant.MAX_WEB_LOAD);

    }

    /**
     * 刷新
     */
    public static void setSwipReflashEnd(final SwipeRefreshLayout swipReflash) {
        if (swipReflash == null) return;
        swipReflash.post(new Runnable() {
            @Override
            public void run() {
                swipReflash.setRefreshing(false);
            }
        });
    }

    /**
     * 刷新
     */
    public static void setSwipReflashEnd(final SwipyRefreshLayout swipReflash) {
        if (swipReflash == null) return;
        swipReflash.post(new Runnable() {
            @Override
            public void run() {
                swipReflash.setRefreshing(false);
            }
        });
    }

    /**
     * 统一设置下拉进度条的颜色 监听
     *
     * @param sw
     * @param
     */
    public static void setSwReflashColor(SwipeRefreshLayout sw, int Bsckcolor, int scheColor1, int scheColor2, int scheColor3) {
        if (sw == null) return;
        //设置加载圈的背景颜色
        sw.setProgressBackgroundColor(Bsckcolor);
        //设置加载圈圈的颜色
        sw.setColorSchemeResources(scheColor1, scheColor2, scheColor3);
    }

    /**
     * 统一设置下拉进度条的颜色 监听
     *
     * @param sw
     * @param
     */
    public static void setSwReflashColor(SwipyRefreshLayout sw, int Bsckcolor, int scheColor1, int scheColor2, int scheColor3) {
        if (sw == null) return;
        //设置加载圈的背景颜色
        sw.setProgressBackgroundColor(Bsckcolor);
        //设置加载圈圈的颜色
        sw.setColorSchemeResources(scheColor1, scheColor2, scheColor3);
    }

    /**
     * 设置默认下拉颜色
     *
     * @param sw
     */
    public static void setSwReflashColor(SwipeRefreshLayout sw) {
        setSwReflashColor(sw, R.color.globe_blue, R.color.white, R.color.white, R.color.white);
    }

    /**
     * 设置默认下拉颜色,使用颜色代码
     *
     * @param sw
     */
    public static void setSwReflashColor(SwipyRefreshLayout sw) {
        setSwReflashColor(sw, 0xff398eff, 0xffffffff, 0xffffffff, 0xffffffff);
    }

    //显示更新dialog
    public void showUadateDialog(String tips, boolean isFocuse) {
        if (isFocuse) {
            updateDialog = DialogHelper.getOneClickDia(this, "发现新版本",
                    tips, "下载", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DownLoadApkReceiver.startRequest(BaseAct.this, Constant.down_url);
                            if (updateDialog != null)
                                updateDialog.dismiss();
                        }
                    }, false);
        } else {
            updateDialog = DialogHelper.getConfirmDialog(this, "发现新版本", tips, "下载", "稍后再说", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownLoadApkReceiver.startRequest(BaseAct.this, Constant.down_url);
                    if (updateDialog != null)
                        updateDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (updateDialog != null)
                        updateDialog.dismiss();
                }
            }, true);
        }
    }

    public void hideInput(View v) {
        mInputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

}
