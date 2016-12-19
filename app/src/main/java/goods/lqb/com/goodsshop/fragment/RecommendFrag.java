package goods.lqb.com.goodsshop.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.OvershootInterpolator;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.base.Constant;
import goods.lqb.com.goodsshop.comutils.JavaBeanHelper;
import goods.lqb.com.goodsshop.comutils.ToastHelper;
import goods.lqb.com.goodsshop.javabean.MainlistItemBean;
import goods.lqb.com.goodsshop.wedige.recyclerview.MainAdapter;
import goods.lqb.com.goodsshop.base.BaseAct;
import goods.lqb.com.goodsshop.base.MhttpUrls;
import goods.lqb.com.goodsshop.base.OkHttpHelper;
import goods.lqb.com.goodsshop.wedige.slidetablayout.TabViewPagerAdapter;
import goods.lqb.com.goodsshop.wedige.swipyrefreshlib.SwipyRefreshLayout;
import goods.lqb.com.goodsshop.wedige.swipyrefreshlib.SwipyRefreshLayoutDirection;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecommendFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommendFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFrag extends Fragment implements OkHttpHelper.OkHttpRespon, SwipyRefreshLayout.OnRefreshListener {

    private OnFragmentInteractionListener mListener;

    private SwipyRefreshLayout swipeRefreshLayout;
    private List<MainlistItemBean> mDatas = new ArrayList<>();
    private Boolean mPullDown = true;
    private MainAdapter adapter;
    private long mLast_id = Constant.MAX_LONG;
    private int LIST_SUCC = 10001;
    private int LIST_FAIL = 10101;
    private String mType = TabViewPagerAdapter.mTabTitle[0];

    public RecommendFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFrag.
     */
    public static RecommendFrag newInstance(String param1, String param2) {
        RecommendFrag fragment = new RecommendFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle augs = this.getArguments();
        if (augs != null) {
            mType = augs.getString(Constant.Type);
        }
        View baseView = inflater.inflate(R.layout.fragment_recommend, container, false);
        swipeRefreshLayout = (SwipyRefreshLayout) baseView.findViewById(R.id.sw);
        //设置加载圈的背景颜色
        BaseAct.setSwReflashColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);

        RecyclerView rcv = (RecyclerView) baseView.findViewById(R.id.recy);
        // rcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        adapter = new MainAdapter(getActivity(), mDatas);
        //设置item动画
        setRecyViewAnimat(rcv, adapter);
        //已经有数据不加载
        if (mDatas.size() == 0)
            startGetData(true);
        return baseView;
    }

    /**
     * 设置 recyView 的动画
     *
     * @param rcv
     * @param adapter
     */
    public static void setRecyViewAnimat(RecyclerView rcv, RecyclerView.Adapter adapter) {
        rcv.setItemAnimator(new SlideInLeftAnimator(new OvershootInterpolator(1f)));
        //设置动画adapter
        SlideInLeftAnimationAdapter slf = new SlideInLeftAnimationAdapter(adapter);
        slf.setFirstOnly(true);
        slf.setDuration(500);
        slf.setInterpolator(new OvershootInterpolator(.5f));
        rcv.setAdapter(slf);
    }

    private void startGetData(Boolean isDown) {
        if (mDatas == null || mDatas.size() == 0) {
            mPullDown = true;
        }
        BaseAct.setSwipReflashStart(swipeRefreshLayout);
        OkHttpHelper.okHttpPost(MhttpUrls.getMainList(), this, getJs(isDown), new Handler(), LIST_SUCC, LIST_FAIL);
    }

    private JSONObject getJs(Boolean isDown) {
        JSONObject object = new JSONObject();
        try {
            if (!isDown)
                object.put(Constant.LAST_ID, mLast_id);
            if (!mType.equals(TabViewPagerAdapter.mTabTitle[0])) {
                object.put(Constant.Type, mType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    //act通信回調
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //网络回调
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
        } else {

            if (!mPullDown)
                ToastHelper.toastShort("暂时没有更多宝贝咯", true, getActivity());
            else
                ToastHelper.toastShort(mType+"分类下暂时没有宝贝..", true, getActivity());

        }
    }

    @Override
    public void netFail(int rcode, String resData, int failTag) {
        BaseAct.setSwipReflashEnd(swipeRefreshLayout);
    }



    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            mPullDown = false;
        } else {
            mPullDown = true;
        }
        startGetData(mPullDown);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
