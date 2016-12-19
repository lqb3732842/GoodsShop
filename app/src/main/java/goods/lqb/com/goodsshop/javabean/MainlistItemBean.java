package goods.lqb.com.goodsshop.javabean;

import goods.lqb.com.goodsshop.base.Constant;
import org.json.JSONObject;

/**
 * Created by 05 on 2016/12/10.
 */

public class MainlistItemBean {
    public String tittle, realPrice, downPrice, downPriceFull,
            sellNums, imgUrl, goodsUrl, kl_yhq, kl_goods;
    public double firstPrice, realDownPrice;
    public double realDownFull = 9999;
    public boolean canUseYHQ;
    public long id = Constant.MAX_LONG;

    public MainlistItemBean() {
    }

    public MainlistItemBean(JSONObject object) {
        if (object == null) return;
        id = object.optLong("goods_id");
        if (id == 0)
            id = Constant.MAX_LONG;
        tittle = object.optString("tittle");
        realPrice = object.optString("first_price");
        firstPrice = object.optDouble("first_price");
        downPrice = object.optString("yhq_money");
        dealUHQ(downPrice);
        sellNums = object.optString("sell_nums");
        imgUrl = object.optString("img_url");
        goodsUrl = object.optString("my_url");
        kl_yhq = object.optString("yhq_tkl");
        if (kl_yhq == null || kl_yhq.length() < 2)
            kl_yhq = "";
        kl_goods = object.optString("goods_tkl");
    }

    //处理 优惠券跟 真实价格
    private void dealUHQ(String downPrice) {
        if (downPrice != null && downPrice.length() > 0) {
            if (downPrice.contains("减")) {
                String[] arr = downPrice.split("减");
                if (arr != null && arr.length > 0) {
                    try {
                        String real = arr[arr.length - 1];//优惠券价值   5元
                        String down = arr[0];//满减   满60元
                        this.downPrice = "减" + real;
                        this.downPriceFull = down;

                        if (real.length() > 0) {
                            real = real.substring(0, real.length() - 1); //5
                        }
                        if (down.length() > 2) {
                            down = down.substring(1, down.length() - 1); //60
                        } else {
                            return;
                        }
                        this.realDownFull = Double.parseDouble(down); //60
                        this.realDownPrice = Double.parseDouble(real); //5
                        if (this.firstPrice >= this.realDownFull) {
                            this.canUseYHQ = true;
                            setQuanHouMoney();
                        }
                    } catch (Exception e) {
                        realDownPrice = 99999;
                    }
                    return;
                }
            } else if (downPrice.contains("无条件")) {
                String[] arr = downPrice.split("无");
                if (arr != null && arr.length > 1) {
                    String real = arr[0];//优惠券价值   5元
                    if (real.length() > 0) {
                        real = real.substring(0, real.length() - 1); //5
                    }
                    try {
                        this.realDownPrice = Double.parseDouble(real); //5.0
                    } catch (Exception e) {
                        this.realDownPrice = 0;
                    }
                    this.downPrice = "减" + real + "元";
                    this.realDownFull = 1;
                    this.downPriceFull = "无条件";
                    this.canUseYHQ = true;
                    setQuanHouMoney();
                    return;
                }
            }
        }
    }

    private void setQuanHouMoney() {
        String re = (firstPrice - realDownPrice) + "";
        if (re.contains(".")) {
            String[] format = re.split("\\.");
            if (format.length > 1 && format[1].length() > 2) {
                this.realPrice = format[0] + "." + format[1].substring(0, 2);
            } else {
                this.realPrice = re;
            }
        } else {
            this.realPrice = re;
        }
    }
}
