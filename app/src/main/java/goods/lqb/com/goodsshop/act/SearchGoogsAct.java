package goods.lqb.com.goodsshop.act;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.base.BaseAct;
import goods.lqb.com.goodsshop.base.Constant;
import goods.lqb.com.goodsshop.base.MhttpUrls;
import goods.lqb.com.goodsshop.base.OkHttpHelper;
import goods.lqb.com.goodsshop.comutils.JavaBeanHelper;
import goods.lqb.com.goodsshop.comutils.ToastHelper;
import goods.lqb.com.goodsshop.fragment.RecommendFrag;
import goods.lqb.com.goodsshop.javabean.MainlistItemBean;
import goods.lqb.com.goodsshop.wedige.recyclerview.MainAdapter;
import goods.lqb.com.goodsshop.wedige.swipyrefreshlib.SwipyRefreshLayout;
import goods.lqb.com.goodsshop.wedige.swipyrefreshlib.SwipyRefreshLayoutDirection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2016/12/15.
 */

public class SearchGoogsAct extends BaseActionBarAct implements SwipyRefreshLayout.OnRefreshListener, View.OnClickListener, OkHttpHelper.OkHttpRespon {

    private EditText mSearchEt;
    private Button mSearchBt;
    private SwipyRefreshLayout swipeRefreshLayout;
    private MainAdapter adapter;
    private List<MainlistItemBean> mDatas = new ArrayList<>();
    private int SEARCH_SUCC = 2391;
    private int SEARCH_FAIL = 2392;
    private boolean mPullDown = true;
    private long mLast_id = Constant.MAX_LONG;
    private String mLastKey = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inintView();
    }

    private void inintView() {
        setTitleMSG("搜索");
        setLeftDarw(R.mipmap.back_arro);
        mSearchEt = (EditText) findViewById(R.id.et_search);
        mSearchEt.setHint("输入关键字搜索");
        mSearchBt = (Button) findViewById(R.id.tv_search);
        mSearchBt.setOnClickListener(this);
        swipeRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.sw);
        //设置加载圈的背景颜色
        BaseAct.setSwReflashColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
        RecyclerView rcv = (RecyclerView) findViewById(R.id.recy);
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        adapter = new MainAdapter(this, mDatas);
        //设置item动画
        RecommendFrag.setRecyViewAnimat(rcv, adapter);

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_searchgoods;
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    //刷新
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        String keyWorld = mSearchEt.getText().toString();
        if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            mPullDown = false;
        } else {
            mPullDown = true;
        }
        doSearch(keyWorld);
    }

    //点击搜索
    @Override
    public void onClick(View view) {
        if (view == mSearchBt) {
            hideInput(mSearchEt);
            mPullDown = true;
            doSearch(mSearchEt.getText().toString());
        }
    }

    private void doSearch(String keyword) {
        mLastKey = keyword;
        setSwipReflashStart(swipeRefreshLayout);
        OkHttpHelper.okHttpPost(MhttpUrls.getMainList(), this, getJs4Search(keyword), new Handler(), SEARCH_SUCC, SEARCH_FAIL);
    }

    private final int SIZE = 30;

    private JSONObject getJs4Search(String keyWord) {
        JSONObject object = new JSONObject();
        try {
            object.put(Constant.KEY_WORD, keyWord);
            object.put(Constant.SIZE, SIZE);
            if (mDatas.size() >= SIZE && mLastKey.equals(keyWord)) {
                object.put(Constant.LAST_ID, mLast_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public void netSucc(JSONObject json, int succTag) {
        BaseAct.setSwipReflashEnd(swipeRefreshLayout);
        JSONArray array = JavaBeanHelper.getArray(json);
        if (array != null && array.length() > 0) {
            if (mDatas == null)
                mDatas = new ArrayList<>();
            if (mPullDown) mDatas.clear();
            for (int i = 0; i < array.length(); i++) {
                MainlistItemBean b = new MainlistItemBean((JSONObject) array.opt(i));
                mDatas.add(b);
                if (b.id < mLast_id)
                    mLast_id = b.id;
            }
            adapter.notifyDataSetChanged();
            setRefalshFlase();
        } else {
            if (mPullDown)
                ToastHelper.toastShort("暂没搜索内容哦", true, this);
            else
                ToastHelper.toastShort("没有更多搜索内容哦", true, this);
        }
    }

    @Override
    public void netFail(int rcode, String resData, int failTag) {
        mLastKey = "";
        BaseAct.setSwipReflashEnd(swipeRefreshLayout);
        setRefalshFlase();
    }

    private void setRefalshFlase() {
        if (mDatas.size() >= SIZE)
            swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        else
            swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
    }
}
