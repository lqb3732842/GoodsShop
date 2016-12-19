package goods.lqb.com.goodsshop.wedige.slidetablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import goods.lqb.com.goodsshop.base.Constant;
import goods.lqb.com.goodsshop.fragment.RecommendFrag;

/**
 * 简单实例
 *
 * @author fyales
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    public static String mTabTitle[] = new String[]{"推荐", "五元购","生活","男士","女士","数码"};
    private Context mContext;

    public TabViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        RecommendFrag frag=new RecommendFrag();
        Bundle bundle=new Bundle();
        bundle.putString(Constant.Type, getType(position));
        frag.setArguments(bundle);
        return frag;

    }

    public static String getType(int position) {
        return mTabTitle[position];
    }

    @Override
    public int getCount() {
        return mTabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }


}
