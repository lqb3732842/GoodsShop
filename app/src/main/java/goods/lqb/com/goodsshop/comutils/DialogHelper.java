package goods.lqb.com.goodsshop.comutils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.adapter.DialogAdapter;

/**
 * Created by sks on 2016/12/12.
 */

public class DialogHelper {



    /**
     * 输入框dialog 回调接口
     */
    public interface OnEditDialogClick {
        void okClick(String content);

        void cancleClick();
    }


    /**
     * 有一个输入框的dialog
     *
     * @param contxt
     * @param enterText
     * @param cancelText
     * @param
     * @param
     */
    public static Dialog getEditDialog(Context contxt, String titleText, String enterText,
                                       String cancelText, String hint, final OnEditDialogClick etClick) {
        Dialog dialog = new Dialog(contxt, R.style.MyDialog);
        View layout = View.inflate(contxt, R.layout.layout_material_dialog_et, null);
        dialog.addContentView(layout, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        final EditText et = (EditText) layout
                .findViewById(R.id.et);
        TextView title = (TextView) layout.findViewById(R.id.tittle);
        TextView btn_enter = (TextView) layout.findViewById(R.id.btn_enter);
        if (titleText != null) {
            title.setText(titleText);
        }
        et.setHint(hint);
        btn_enter.setText(enterText);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etClick.okClick(et.getText().toString().trim());
            }
        });
        TextView btn_cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        btn_cancel.setText(cancelText);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etClick.cancleClick();
            }
        });
        dialog.show();
        return dialog;
    }

    /**
     * 显示 dialog (里面有个普通的listview)
     *
     * @param arr   显示的字符数组(填入适配器可以不用这个)
     * @param tv    控件
     * @param title 标题
     * @return
     * @param adapter  s适配器,默认用 字符串适配器
     */
    private static Dialog getListDialog(Context c, String arr[], TextView tv,
                                        String title, BaseAdapter adapter, AdapterView.OnItemClickListener itemClickListener) {
        View dView = LayoutInflater.from(c).inflate(R.layout.dialog_material_list, null);
        TextView dvTitle = (TextView) dView.findViewById(R.id.tittle);
        TextView cancle = (TextView) dView.findViewById(R.id.btn_enter);
        cancle.setText("取消");
        dvTitle.setText(title);
        final Dialog b = new Dialog(c, R.style.MyDialog);
        b.setContentView(dView);
        b.setCanceledOnTouchOutside(true);
        b.setCancelable(true);
        ListView mListView = (ListView) dView.findViewById(R.id.list);
        if (adapter == null) {
            DialogAdapter mAdapter = new DialogAdapter(c, arr, tv, b);
            mListView.setAdapter(mAdapter);
        }else {
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(itemClickListener);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b != null)
                    b.dismiss();
            }
        };
        cancle.setOnClickListener(listener);
        dView.findViewById(R.id.m_bg).setOnClickListener(listener);
        return b;
    }
    /**
     * 显示 dialog (里面有个普通的listview)
     * @param arr   显示的字符数组
     * @param tv    要返回显示的text控件
     * @param title 标题
     * @return
     */
    public static Dialog getListDialog(Context c, String arr[], TextView tv, String title) {
        return  getListDialog(c,arr,tv,title,null,null);
    }

    /**
     * 返回一个listDialog
     * @param c
     * @param title
     * @param adapter
     * @param itemClickListener
     * @return
     */
    public static Dialog getListDialog(Context c, String title,BaseAdapter adapter,AdapterView.OnItemClickListener itemClickListener) {
        return  getListDialog(c,null,null,title,adapter,itemClickListener);
    }


    /**
     * 返回可以取消的dialog
     *
     * @param context
     * @param title
     * @param msg
     * @param okStr
     * @param cancelStr
     * @param okListener
     * @param calcelListener
     * @return
     */
    public static Dialog getConfirmDialog(Context context, String title,
                                          String msg, String okStr, String cancelStr,
                                          View.OnClickListener okListener, View.OnClickListener calcelListener) {
        return getConfirmDialog(context, title, msg, okStr, cancelStr, okListener, calcelListener, true);
    }

    /**
     * 返回双按钮dialog
     *
     * @param context
     * @param title
     * @param msg
     * @param okStr
     * @param cancelStr
     * @param okListener
     * @param calcelListener
     * @param isCancle
     * @return
     */
    public static Dialog getConfirmDialog(Context context, String title,
                                          String msg, String okStr, String cancelStr,
                                          View.OnClickListener okListener, View.OnClickListener calcelListener, boolean isCancle) {
        Dialog dialog = new Dialog(context, R.style.MyDialog);
        View layout = View.inflate(context, R.layout.layout_material_dialog, null);
        dialog.addContentView(layout, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        TextView txt_cont = (TextView) layout.findViewById(R.id.message);
        txt_cont.setText(msg);
        TextView btn_enter = (TextView) layout.findViewById(R.id.btn_enter);
        btn_enter.setText(okStr);
        TextView btn_cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        btn_cancel.setText(cancelStr);
        TextView tittle = (TextView) layout.findViewById(R.id.title);
        tittle.setText(title);
        btn_enter.setOnClickListener(okListener);
        btn_cancel.setOnClickListener(calcelListener);
        dialog.setCanceledOnTouchOutside(isCancle);
        dialog.setCancelable(isCancle);
        dialog.show();
        return dialog;
    }

    public static Dialog getOneClickDia(Context c, String tittle,
                                        String content, String okStr, View.OnClickListener sureListener, boolean isCancle) {
        Dialog oneClickDia = new Dialog(c, R.style.MyDialog);
        View layout = View.inflate(c, R.layout.layout_material_dialog, null);
        oneClickDia.addContentView(layout, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        TextView txt_cont = (TextView) layout.findViewById(R.id.message);
        txt_cont.setText(content);
        TextView btn_enter = (TextView) layout.findViewById(R.id.btn_enter);
        btn_enter.setText(okStr);
        TextView btn_cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        btn_cancel.setVisibility(View.GONE);
        TextView tittleView = (TextView) layout.findViewById(R.id.title);
        tittleView.setText(tittle);
        btn_enter.setOnClickListener(sureListener);
        oneClickDia.setCanceledOnTouchOutside(isCancle);
        oneClickDia.show();
        return oneClickDia;
    }

    public static Dialog getOneClickDia(Context c, String tittle,
                                        String content, String okStr, View.OnClickListener sureListener) {
        return getOneClickDia(c, tittle, content, okStr, sureListener, true);
    }


    public static Dialog getOneClickDia(Context c, String tittle, String content, View.OnClickListener sureListener) {
        return getOneClickDia(c, tittle, content, "确定", sureListener);
    }

}
