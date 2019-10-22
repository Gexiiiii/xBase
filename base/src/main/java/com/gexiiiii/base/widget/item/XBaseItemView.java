package com.gexiiiii.base.widget.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gexiiiii.base.R;
import com.gexiiiii.base.utils.XDensityUtil;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:13
 * description :
 */
public abstract class XBaseItemView extends LinearLayout {
    private boolean isShowUnderLine;
    private View view;
    private RelativeLayout baseLayout;

    protected int dipZero = XDensityUtil.dip2px(getContext(), 0);
    protected int dipNormal = XDensityUtil.dip2px(getContext(), 10);
    protected int dipLarge = XDensityUtil.dip2px(getContext(), 16);
    protected int dipLargest = XDensityUtil.dip2px(getContext(), 25);
    protected int dipMinHeight = XDensityUtil.dip2px(getContext(), 50);

    private int lineColor;
    private float lineHeight, lineMargin;

    public XBaseItemView(Context context) {
        this(context, null);
    }

    public XBaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    /**
     * 自定义分割线
     * 可替代isShowUnderLine
     *
     * @param colorResId 颜色
     * @param dipHeight  高度
     * @param dipMargin  左右距离
     * @param isShow     是否显示
     */
    public void setUnderLine(int colorResId, float dipHeight, int dipMargin, boolean isShow) {
        lineColor = colorResId;
        if (dipHeight <= 0) {
            lineHeight = XDensityUtil.dip2px(getContext(), 0.5f);
        } else {
            lineHeight = XDensityUtil.dip2px(getContext(), dipHeight);
        }
        if (dipMargin <= 0) {
            lineMargin = dipLarge;
        } else {
            lineMargin = XDensityUtil.dip2px(getContext(), dipMargin);
        }
        removeView(view);
        buildUnderLine();
        view.setVisibility(isShow ? VISIBLE : INVISIBLE);
    }

    private void initViews() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        setMinimumHeight(dipMinHeight);

        TypedArray ta = getContext().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        Drawable drawable = ta.getDrawable(0);
        setBackground(drawable);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, dipZero);
        params.weight = 1;
        baseLayout = new RelativeLayout(getContext());
        baseLayout.setPadding(dipLarge, dipNormal, dipLarge, dipNormal);
        addView(baseLayout, params);
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBaseItemView);
        isShowUnderLine = typedArray.getBoolean(R.styleable.XBaseItemView_isShowUnderLine, false);
        lineColor = typedArray.getResourceId(R.styleable.XBaseItemView_line_color, R.color.colorGray_50);
        lineHeight = typedArray.getDimension(R.styleable.XBaseItemView_line_height, XDensityUtil.dip2px(getContext(), 0.5f));
        lineMargin = typedArray.getDimension(R.styleable.XBaseItemView_line_margin, dipLarge);
    }

    /**
     * 创建View
     * 用于自定义ItemView
     * 1、继承BaseItemView
     * 2、重写initAttrs方法
     * 3、设置好必要参数后调用该方法
     */
    public void buildView() {
        buildUnderLine();

        buildRightView(baseLayout);
        buildMiddleView(baseLayout);
        buildLeftView(baseLayout);
    }

    /**
     * 创建分割线
     */
    private void buildUnderLine() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) lineHeight);
        params.leftMargin = (int) lineMargin;
        params.rightMargin = (int) lineMargin;
        view = new View(getContext());
        view.setBackgroundColor(getResources().getColor(lineColor));
        view.setVisibility(isShowUnderLine ? VISIBLE : INVISIBLE);
        addView(view, params);
    }

    /**
     * 显示default分割线
     */
    public void isShowUnderLine(boolean isShow) {
        view.setVisibility(isShow ? VISIBLE : INVISIBLE);
    }

    public abstract void buildLeftView(ViewGroup parent);

    public abstract void buildMiddleView(ViewGroup parent);

    public abstract void buildRightView(ViewGroup parent);
}
