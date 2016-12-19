package goods.lqb.com.goodsshop.act;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import goods.lqb.com.goodsshop.R;
import goods.lqb.com.goodsshop.base.BaseAct;

/**
 * Created by sks on 2016/12/13.
 */

public abstract class BaseActionBarAct extends BaseAct {
    private ViewGroup mActionBar;
    private TextView tvActionBarTitle;
    private TextView tvActionBarLeft;
    private TextView tvActionBarRight;
    private ImageView imgActionBarLeft;
    private ImageView imgActionBarRight;
    private InputMethodManager imm;
    private ViewGroup mLeft;
    private ViewGroup mRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActionBar = (ViewGroup) findViewById(R.id.actionBar_main);
        if (mActionBar != null) {
            tvActionBarTitle = (TextView) mActionBar
                    .findViewById(R.id.actionbar_tvTitle);
            tvActionBarLeft = (TextView) mActionBar
                    .findViewById(R.id.actionbar_tvLeft);
            tvActionBarRight = (TextView) mActionBar
                    .findViewById(R.id.actionbar_tvRight);
            imgActionBarLeft = (ImageView) mActionBar
                    .findViewById(R.id.actionbar_imgLeft);
            imgActionBarRight = (ImageView) mActionBar
                    .findViewById(R.id.actionbar_imgRight);
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            mLeft = (ViewGroup) mActionBar.findViewById(R.id.actionbar_layLeft);
            mRight = (ViewGroup) mActionBar
                    .findViewById(R.id.actionbar_layRight);
            // 1秒后才可以点击返回
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onLeftClick();
                        }
                    });
                    mRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRightClick();
                        }
                    });
                }
            }, 500);

        }

    }

    public abstract int getLayoutId();

    public abstract void onLeftClick();

    public abstract void onRightClick();

    public void setActionBarBackGroundColor(int color) {
        if (mActionBar != null) {
            mActionBar.setBackgroundColor(color);
        }
    }
    public void setLeftInVisibile() {
        if (tvActionBarLeft != null) {
            tvActionBarLeft.setVisibility(View.INVISIBLE);
        }
        if (imgActionBarLeft != null) {
            imgActionBarLeft.setVisibility(View.GONE);
        }
    }

    public void setRightInVisibile() {
        if (tvActionBarRight != null) {
            tvActionBarRight.setVisibility(View.INVISIBLE);
        }
        if (imgActionBarRight != null) {
            imgActionBarRight.setVisibility(View.GONE);
        }
    }

    public void setRightGone() {
        if (tvActionBarRight != null) {
            tvActionBarRight.setVisibility(View.GONE);
        }
        if (imgActionBarRight != null) {
            imgActionBarRight.setVisibility(View.GONE);
        }
    }

    public void setNoTitleBar() {
        if (mActionBar != null) {
            mActionBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setTitleMSG(String text) {
        if (tvActionBarTitle != null) {
            tvActionBarTitle.setText(text);
        }
    }

    public void setTitleMSG(int text) {
        if (tvActionBarTitle != null) {
            tvActionBarTitle.setText(text);
        }
    }

    public void setActionBGColor(int resId) {
        if (mActionBar != null) {
            mActionBar.setBackgroundColor(getResources().getColor(resId));
        }
    }

    public void setActionBGDraw(int resId) {
        if (mActionBar != null) {
            mActionBar.setVisibility(View.VISIBLE);
            mActionBar.setBackgroundResource(resId);
        }
    }

    public void setActionVisibility(int resId) {
        if (mActionBar != null) {
            mActionBar.setVisibility(resId);
        }
    }

    public void setRightText(String text) {
        if (tvActionBarRight != null) {
            tvActionBarRight.setVisibility(View.VISIBLE);
            tvActionBarRight.setText(text);
        }
        if (imgActionBarRight != null) {
            imgActionBarRight.setVisibility(View.GONE);
        }
    }

    public void setRightText(int text) {
        if (tvActionBarRight != null) {
            tvActionBarRight.setVisibility(View.VISIBLE);
            tvActionBarRight.setText(text);
        }
        if (imgActionBarRight != null) {
            imgActionBarRight.setVisibility(View.GONE);
        }

    }

    /**
     * 设置action右侧图片
     *
     * @param text
     */
    public void setRightDarw(int text) {
        if (tvActionBarRight != null) {
            tvActionBarRight.setVisibility(View.INVISIBLE);
        }

        if (imgActionBarRight != null) {
            imgActionBarRight.setVisibility(View.VISIBLE);
            imgActionBarRight.setImageResource(text);
        }
    }

    /**
     * 设置action左侧图片
     *
     * @param text
     */
    public void setLeftDarw(int text) {
        if (imgActionBarLeft != null) {
            imgActionBarLeft.setVisibility(View.VISIBLE);
            imgActionBarLeft.setImageResource(text);

        }
        if (tvActionBarLeft != null) {
            tvActionBarLeft.setVisibility(View.INVISIBLE);
        }
    }

    public void setLeftText(int text) {
        if (imgActionBarLeft != null) {
            imgActionBarLeft.setVisibility(View.INVISIBLE);
        }
        if (tvActionBarLeft != null) {
            tvActionBarLeft.setVisibility(View.VISIBLE);
            tvActionBarLeft.setText(text);
        }
    }

    public void setLeftText(String text) {
        if (imgActionBarLeft != null) {
            imgActionBarLeft.setVisibility(View.INVISIBLE);
        }
        if (tvActionBarLeft != null) {
            tvActionBarLeft.setVisibility(View.VISIBLE);
            tvActionBarLeft.setText(text);
        }
    }

    protected void setGoneAction() {
        if (mActionBar != null) {
            mActionBar.setVisibility(View.GONE);
        }
    }

    protected void setVisibAction() {
        if (mActionBar != null) {
            mActionBar.setVisibility(View.VISIBLE);
        }
    }

}
