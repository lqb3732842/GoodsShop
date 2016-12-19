package goods.lqb.com.goodsshop.act;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.base.Constant;
import goods.lqb.com.goodsshop.base.MhttpUrls;
import goods.lqb.com.goodsshop.base.OkHttpHelper;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;
import goods.lqb.com.goodsshop.comutils.JavaBeanHelper;
import goods.lqb.com.goodsshop.fragment.RecommendFrag;
import goods.lqb.com.goodsshop.wedige.slidetablayout.SlidingTabLayout;
import goods.lqb.com.goodsshop.wedige.slidetablayout.TabViewPagerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActionBarAct implements RecommendFrag.OnFragmentInteractionListener, OkHttpHelper.OkHttpRespon {

    private final  int VERSION_SUCC=10023;
    private final int VERSION_FAIL=10123;
    private Dialog searcDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(getLayoutId());
        setTitleMSG("好购");
        setRightDarw(R.mipmap.search_icon);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(), this));
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(false); //是否填充满屏幕的宽度
        slidingTabLayout.setViewPager(viewPager);
        //自定义下划线颜色
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return 0xffFF6537;
            }
        });
        //检查版本更新
        startCheckVersion();
    }


    private void startCheckVersion() {
        OkHttpHelper.okHttpPost(MhttpUrls.check_version(), this, getJs(), new Handler(), VERSION_SUCC, VERSION_SUCC);
    }

    private JSONObject getJs() {
        JSONObject object = new JSONObject();
        try {
            object.put("v_code", DeviceHelp.getVersionCode(this));
            object.put("platform", Constant.ANDROID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    //
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void netSucc(JSONObject json, int succTag) {
        switch (succTag){
            case VERSION_SUCC:
                dealUpdate(json);
        }
    }

    //检测升级
    private void dealUpdate(JSONObject json) {
        int res= JavaBeanHelper.getRes(json);
        if (res==1){
            JSONObject object=JavaBeanHelper.getObj(json);
            String tip=object.optString("ver_desc");
            int isFocus=object.optInt("is_force");
            Constant.down_url=MhttpUrls.HTTP_HEAD+object.optString("url");
            showUadateDialog(tip, isFocus == 1);
        }
    }

    @Override
    public void netFail(int rcode, String resData, int failTag) {
      //  System.out.println(resData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void onLeftClick() {
    }
    @Override
    public void onRightClick() {
        Intent intent=new Intent(this,SearchGoogsAct.class);
        startActivity(intent);
    }

//    private void showSearchDialog() {
//        searcDialog = DialogHelper.getEditDialog(baseAct, "搜索好物", "确定", "取消", "输入关键字", new DialogHelper.OnEditDialogClick() {
//            @Override
//            public void okClick(String content) {
//                if (searcDialog!=null){
//                    searcDialog.dismiss();
//                    searcDialog=null;
//                }
//                doSearch(content);
//            }
//            @Override
//            public void cancleClick() {
//                if (searcDialog!=null){
//                    searcDialog.dismiss();
//                    searcDialog=null;
//                }
//            }
//        });
//    }
}