package goods.lqb.com.goodsshop.wedige.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.act.DetailAct;
import goods.lqb.com.goodsshop.comutils.DeviceHelp;
import goods.lqb.com.goodsshop.javabean.MainlistItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabeef on 2015/01/03.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private Context mContext;
  private List<MainlistItemBean> mDataSet;

  public MainAdapter(Context context, List<MainlistItemBean> dataSet) {
    mContext = context;
    mDataSet = dataSet;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.item_mainlist, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {

    final MainlistItemBean bean=mDataSet.get(position);
    Picasso.with(mContext).load(bean.imgUrl).into(holder.ivMain);
    holder.tvTittle.setText(bean.tittle);
    holder.tvRealPrice.setText("¥"+bean.realPrice);
    holder.tvDownPrice.setText(bean.downPrice);
    holder.getTvDownPriceFull.setText(bean.downPriceFull);
    holder.tvbuyNums.setText(bean.sellNums);
//    if ("0".endsWith(bean.downPrice)||"减0元".equals(bean.downPrice)){
//
//    }else {
//
//    }
    if (bean.canUseYHQ){
      holder.tvDiJia.setText("券后");
      holder.tvDiJia.setTextColor(0xff6ee31c);
      holder.vDownPrice.setVisibility(View.VISIBLE);
    }else {
      holder.tvDiJia.setText("底价");
      holder.vDownPrice.setVisibility(View.GONE);
      holder.tvDiJia.setTextColor(0xff666666);
    }
    holder.vBg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, DetailAct.class);
        intent.putExtra("url", bean.goodsUrl);
        intent.putExtra("yhq_kl", bean.kl_yhq);
        intent.putExtra("yhq_goods", bean.kl_goods);
        intent.putExtra("money", bean.downPrice);
        mContext.startActivity(intent);
        DeviceHelp.setSystemCopy("", mContext);
      }
    });
  }

  @Override
  public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }

  @Override public int getItemCount() {
    return mDataSet.size();
  }

  public void remove(int position) {
    mDataSet.remove(position);
    notifyItemRemoved(position);
  }

  public void add(ArrayList<MainlistItemBean> list) {
    mDataSet.addAll(list);
    notifyDataSetChanged();
    //notifyItemInserted(mDataSet.size()-1);
  }
  static class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivMain;
    public TextView tvTittle,tvRealPrice,tvDownPrice,tvbuyNums,getTvDownPriceFull,tvDiJia;
    public View vBg,vDownPrice;
    public ViewHolder(View itemView) {
      super(itemView);
      ivMain = (ImageView) itemView.findViewById(R.id.iv_main);
      tvTittle = (TextView) itemView.findViewById(R.id.item_tittle);
      tvRealPrice = (TextView) itemView.findViewById(R.id.item_price);
      tvDownPrice = (TextView) itemView.findViewById(R.id.down_price);
      getTvDownPriceFull = (TextView) itemView.findViewById(R.id.down_price_full);
      tvbuyNums = (TextView) itemView.findViewById(R.id.item_sell_nums);
      tvDiJia = (TextView) itemView.findViewById(R.id.item_dijia);
      vBg=itemView.findViewById(R.id.item_bg);
      vDownPrice=itemView.findViewById(R.id.down_price_lay);
    }
  }
}
