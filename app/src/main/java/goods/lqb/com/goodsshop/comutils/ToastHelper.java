package goods.lqb.com.goodsshop.comutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by 05 on 2016/12/10.
 */

public class ToastHelper {
    private static Toast toastS;
    private static Toast toastL;

    public static void toastShort(String cont, boolean isCenter, Context c) {
        try {
            if (toastS == null) {
                toastS = Toast.makeText(c, cont,
                        Toast.LENGTH_SHORT);
            } else {
                toastS.setText(cont);
            }
            if (isCenter) {
                toastS.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toastS.setGravity(Gravity.BOTTOM, 0, 0);
            }
            toastS.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toastLong(String cont, boolean isCenter, Context c) {
        try {
            if (toastL == null) {
                toastL = Toast.makeText(c, cont,
                        Toast.LENGTH_LONG);
            } else {
                toastL.setText(cont);
            }
            if (isCenter) {
                toastL.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toastL.setGravity(Gravity.BOTTOM, 0, 0);
            }
            toastL.show();
        } catch (Exception e) {
        }
    }
}
