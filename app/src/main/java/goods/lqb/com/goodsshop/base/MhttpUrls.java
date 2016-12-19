package goods.lqb.com.goodsshop.base;

/**
 * Created by 05 on 2016/12/10.
 */

public class MhttpUrls {
    public static String HTTP_HEAD_APP ="http://115.28.13.203/app/controller/";
    public static String HTTP_HEAD ="http://115.28.13.203/";

    public  static String getMainList(){
        return HTTP_HEAD_APP +"Select.php";
    }

    public  static String check_version(){
        return HTTP_HEAD_APP +"check_version.php";
    }

}
