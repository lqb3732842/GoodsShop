package goods.lqb.com.goodsshop.comutils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 获取网络返回结果的对应值
 * Created by 05 on 2016/12/10.
 */

public class JavaBeanHelper {
    public static JSONObject getObj(JSONObject data) {
        if (data == null) return null;
        return data.optJSONObject("obj");
    }

    public static JSONArray getArray(JSONObject data) {
        if (data == null) return null;
        return data.optJSONArray("array");
    }

    public static int getRes(JSONObject data) {
        if (data == null) return 0;
        return data.optInt("res");
    }

    public static String getMsg(JSONObject data) {
        if (data == null) return "";
        return data.optString("msg");
    }
}
