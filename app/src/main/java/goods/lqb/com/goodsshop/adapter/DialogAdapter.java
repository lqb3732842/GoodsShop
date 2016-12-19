package goods.lqb.com.goodsshop.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import goods.lqb.com.goodsshop.R;


public class DialogAdapter extends BaseAdapter{

	private String arr[];
	private Context context;
	private TextView tv;
	private Dialog dialog;
	
	/**
	 * 弹出listview 选择
	 * @param context
	 * @param arr 显示的字符
	 * @param tv 要赋值的控件
	 * @param dialog
	 */
	public DialogAdapter(Context context, String arr[], TextView tv, Dialog dialog) {
		this.arr = arr;
		this.context = context;
		this.tv = tv;
		this.dialog = dialog;
	}

	@Override
	public int getCount() {
		return arr.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arr[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		v = View.inflate(context,R.layout.item_dialog, null);
		
		RadioButton rb = (RadioButton) v.findViewById(R.id.rb);
		rb.setText(arr[arg0]);
		rb.setOnCheckedChangeListener(new onClickL(arr[arg0],tv));
		return v;
	}
	
	class onClickL implements OnCheckedChangeListener{
		private TextView tva;
		private String array;
		public onClickL(String array,TextView tva) {
			this.tva = tva;
			this.array = array;
		}
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//			Toast.makeText(context, "aaaaaaaaa", 1).show();
			tva.setText(array);
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

}
