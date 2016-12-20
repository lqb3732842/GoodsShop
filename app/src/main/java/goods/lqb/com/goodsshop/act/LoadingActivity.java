package goods.lqb.com.goodsshop.act;

import android.app.Activity;
import android.os.Bundle;

import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;

public class LoadingActivity extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceHelp.setStatiuBarClor(this, 0xffFF6537);
        setContentView(R.layout.load_act);
        getWindow().getDecorView().postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (isFinishing())
            return;
        MainActivity.start(this);
        finish();
    }
}
