package goods.lqb.com.goodsshop.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;

public class LoadingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceHelp.setStatiuBarClor(this, 0xffFF6537);
        setContentView(R.layout.load_act);
        initData();
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMain();
            }
        }, 2000);
    }
    private void goMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    // 展示时执行
    @Override
    protected void onResume() {
        super.onResume();
    }
}
