package com.gexiiiii.base.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.gexiiiii.base.R;
import com.gexiiiii.base.utils.XDensityUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:14
 * description :
 */
public class XTabLayout extends RelativeLayout {

    protected TabLayout mTabLayout;
    private float iconDimen, selectLineHeight;
    private int textStyle, backgroundColor, selectLineColor, backgroundAlpha, rippleColor, selectLineStyle, tabMode;
    private boolean unboundedRipple;

    public XTabLayout(Context context) {
        this(context, null);
    }

    public XTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XTabLayout);
        textStyle = typedArray.getResourceId(R.styleable.XTabLayout_TextStyle, R.style.TabDefaultText);
        iconDimen = typedArray.getDimension(R.styleable.XTabLayout_IconDimen, XDensityUtil.dip2px(context, 25));
        backgroundAlpha = typedArray.getInt(R.styleable.XTabLayout_BackgroundAlpha, 255);
        backgroundColor = typedArray.getColor(R.styleable.XTabLayout_BackgroundColor, Color.WHITE);
        selectLineColor = typedArray.getColor(R.styleable.XTabLayout_SelectLineColor, Color.BLACK);
        selectLineHeight = typedArray.getDimension(R.styleable.XTabLayout_SelectLineHeight, XDensityUtil.dip2px(context, 0));
        selectLineStyle = typedArray.getResourceId(R.styleable.XTabLayout_SelectLineStyle, 0);
        unboundedRipple = typedArray.getBoolean(R.styleable.XTabLayout_UnboundedRipple, false);
        rippleColor = typedArray.getResourceId(R.styleable.XTabLayout_RippleColor, 0);
        tabMode = typedArray.getInt(R.styleable.XTabLayout_TabMode, 0);
        typedArray.recycle();
    }

    private void initView() {
        mTabLayout = new TabLayout(getContext());
        switch (tabMode) {
            case 0:
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                break;
            case 1:
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;
            default:
                break;
        }
        mTabLayout.setUnboundedRipple(unboundedRipple);
        if (rippleColor > 0) {
            mTabLayout.setTabRippleColorResource(rippleColor);
        }
        mTabLayout.setSelectedTabIndicatorHeight((int) selectLineHeight);
        if (selectLineStyle > 0) {
            mTabLayout.setSelectedTabIndicator(selectLineStyle);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mTabLayout.setPadding(5, 5, 5, 5);
        params.addRule(ALIGN_PARENT_BOTTOM);
        mTabLayout.setBackgroundColor(backgroundColor);
        if (backgroundAlpha > 255 || backgroundAlpha < 0) {
            backgroundAlpha = 255;
        }
        mTabLayout.getBackground().setAlpha(backgroundAlpha);
        mTabLayout.setSelectedTabIndicatorColor(selectLineColor);
        addView(mTabLayout, params);
    }

    public void addTabItems(List<XgxTabItem> xgxTabItems) {
        int i = 0;
        for (XgxTabItem xgxTabItem : xgxTabItems) {
            addTabItem(xgxTabItem, i);
            i++;
        }
    }

    public void addTabItem(XgxTabItem xgxTabItem, int position) {
        addTabItem(buildTabItemView(xgxTabItem), position);
    }

    public void addTabItem(View view, int position) {
        if (mTabLayout.getTabCount() > position) {
            TabLayout.Tab tab = mTabLayout.getTabAt(position);
            tab.setCustomView(view);
        } else {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(view);
            mTabLayout.addTab(tab);
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        mTabLayout.setupWithViewPager(viewPager);
    }

    private View buildTabItemView(XgxTabItem item) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(CENTER_IN_PARENT);

        //添加图标
        if (item.imageRes > 0) {
            ImageView imageView = new ImageView(getContext());
            if (!item.text.isEmpty()) {
                imageView.setAdjustViewBounds(true);
                imageView.setMaxWidth((int) iconDimen);
                imageView.setMaxHeight((int) iconDimen);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(item.imageRes);
            linearLayout.addView(imageView);
        }

        //添加文字
        if (!item.text.isEmpty()) {
            TextView textView = new TextView(getContext());
            textView.setText(item.text);
            textView.setTextAppearance(getContext(), textStyle);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
        }
        return linearLayout;
    }
}
