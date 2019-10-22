package com.gexiiiii.base.widget.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.gexiiiii.base.R;
import com.gexiiiii.base.utils.XDensityUtil;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:13
 * description :
 */
public class XItemView extends XBaseItemView {

    private int leftIcon, rightIcon;
    private int titleStyle, leftContentStyle, rightContentStyle;
    private String title, leftContent, rightContent;
    private float leftIconDimen, rightIconDimen;
    private LinearLayout leftLayout, rightLayout, textLayout, contentLayout;

    private TextView tvTitle, tvLeftContent, tvRightContent;
    private ImageView ivRightIcon, ivLeftIcon, ivLeft, ivRight;

    private int leftPadding = 0;
    private int rightPadding = 0;

    @IdRes
    private int rightLayoutId = Integer.MAX_VALUE - 1000;

    public XItemView(Context context) {
        this(context, null);
    }

    public XItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        buildView();
    }

    public void setLeftIcon(int resId, int dipIconSize) {
        int dimen = XDensityUtil.dip2px(getContext(), dipIconSize);
        if (dipIconSize <= 0) {
            dimen = dipLargest;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dimen, dimen);
        if (ivLeft.getVisibility() == VISIBLE) {
            params.rightMargin = dipLarge;
        }
        ivLeftIcon.setLayoutParams(params);
        ivLeftIcon.setImageResource(resId);
        if (ivLeftIcon.getDrawable() != null) {
            ivLeftIcon.setVisibility(VISIBLE);
            leftPadding = dipLarge;
        } else {
            ivLeftIcon.setVisibility(GONE);
            leftPadding = 0;
        }
        textLayout.setPadding(leftPadding, 0, rightPadding, 0);
    }

    public void setRightIcon(int resId, int dipIconSize) {
        int dimen = XDensityUtil.dip2px(getContext(), dipIconSize);
        if (dipIconSize <= 0) {
            dimen = dipLargest;
        }
        ivRightIcon.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen));
        ivRightIcon.setImageResource(resId);
        if (ivRightIcon.getDrawable() != null) {
            ivRightIcon.setVisibility(VISIBLE);
            rightPadding = dipLarge;
        } else {
            ivRightIcon.setVisibility(GONE);
            rightPadding = 0;
        }
        textLayout.setPadding(leftPadding, 0, rightPadding, 0);
        contentLayout.setPadding(0, 0, rightPadding, 0);
    }

    public ImageView getRightImageView(int dipViewSize) {
        int dimen = XDensityUtil.dip2px(getContext(), dipViewSize);
        if (dipViewSize <= 0) {
            dimen = dipLargest;
        }
        ivRight.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen));
        contentLayout.setVisibility(VISIBLE);
        ivRight.setVisibility(VISIBLE);
        rightPadding = dipLarge;
        textLayout.setPadding(leftPadding, 0, rightPadding, 0);
        return ivRight;
    }

    public ImageView getLeftImageView(int dipViewSize) {
        int dimen = XDensityUtil.dip2px(getContext(), dipViewSize);
        if (dipViewSize <= 0) {
            dimen = dipLargest;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dimen, dimen);
        params.rightMargin = dipLarge;
        if (ivLeftIcon.getDrawable() != null) {
            params.leftMargin = dipLarge;
        }
        ivLeft.setLayoutParams(params);
        ivLeft.setVisibility(VISIBLE);
        return ivLeft;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(VISIBLE);
        }
    }

    public void setTitleStyle(int resId) {
        tvTitle.setTextAppearance(getContext(), resId);
    }

    public void setLeftContent(String content) {
        tvLeftContent.setText(content);
        if (!TextUtils.isEmpty(content)) {
            tvLeftContent.setVisibility(VISIBLE);
        }
    }

    public void setLeftContentStyle(int resId) {
        tvLeftContent.setTextAppearance(getContext(), resId);
    }

    public void setRightContent(String content) {
        tvRightContent.setText(content);
        if (!TextUtils.isEmpty(content)) {
            rightPadding = dipLarge;
            tvRightContent.setVisibility(VISIBLE);
            contentLayout.setVisibility(VISIBLE);
        } else {
            rightPadding = 0;
            tvRightContent.setVisibility(GONE);
        }
        textLayout.setPadding(leftPadding, 0, rightPadding, 0);
    }

    public void setRightContentStyle(int resId) {
        tvRightContent.setTextAppearance(getContext(), resId);
    }

    @Override
    public void initAttrs(Context context, AttributeSet attrs) {
        super.initAttrs(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XItemView);
        leftIcon = typedArray.getResourceId(R.styleable.XItemView_left_icon, -1);
        leftIconDimen = typedArray.getDimension(R.styleable.XItemView_left_icon_dimen, dipLargest);
        title = typedArray.getString(R.styleable.XItemView_title);
        titleStyle = typedArray.getResourceId(R.styleable.XItemView_title_style, -1);
        leftContent = typedArray.getString(R.styleable.XItemView_left_content);
        leftContentStyle = typedArray.getResourceId(R.styleable.XItemView_left_content_style, -1);
        rightContent = typedArray.getString(R.styleable.XItemView_right_content);
        rightContentStyle = typedArray.getResourceId(R.styleable.XItemView_right_content_style, -1);
        rightIcon = typedArray.getResourceId(R.styleable.XItemView_right_icon, -1);
        rightIconDimen = typedArray.getDimension(R.styleable.XItemView_right_icon_dimen, dipLargest);
    }

    @Override
    public void buildLeftView(ViewGroup parent) {
        int dimen = (int) leftIconDimen;
        //定义左侧布局
        leftLayout = new LinearLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.LEFT_OF, rightLayout.getId());
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftLayout.setOrientation(HORIZONTAL);
        leftLayout.setGravity(Gravity.CENTER_VERTICAL);
        //添加 左侧 ImageIcon
        ivLeftIcon = new ImageView(getContext());
        ivLeftIcon.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen));
        ivLeftIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ivLeftIcon.setVisibility(GONE);
        if (leftIcon > 0) {
            ivLeftIcon.setImageResource(leftIcon);
            ivLeftIcon.setVisibility(VISIBLE);
        }
        leftLayout.addView(ivLeftIcon);
        //添加 左侧 ImageView
        ivLeft = new ImageView(getContext());
        ivLeft.setVisibility(GONE);
        leftLayout.addView(ivLeft);
        //定义左侧文字布局
        textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayout.setOrientation(VERTICAL);
        if (leftIcon > 0) {
            leftPadding = dipLarge;
        }
        if (rightIcon > 0) {
            rightPadding = dipLarge;
        }
        textLayout.setPadding(leftPadding, 0, rightPadding, 0);
        //添加 左侧 上方 TextView
        tvTitle = new TextView(getContext());
        tvTitle.setSingleLine(true);
        tvTitle.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        tvTitle.setTextAppearance(getContext(), titleStyle);
        tvTitle.setVisibility(GONE);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(VISIBLE);
        }
        textLayout.addView(tvTitle);
        //添加 左侧 下方 TextView
        tvLeftContent = new TextView(getContext());
        tvLeftContent.setSingleLine(true);
        tvLeftContent.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        tvLeftContent.setTextAppearance(getContext(), leftContentStyle);
        tvLeftContent.setVisibility(GONE);
        if (!TextUtils.isEmpty(leftContent)) {
            tvLeftContent.setText(leftContent);
            tvLeftContent.setVisibility(VISIBLE);
        }
        textLayout.addView(tvLeftContent);
        //添加左侧文字布局
        leftLayout.addView(textLayout, layoutParams);
        //添加左侧布局
        parent.addView(leftLayout, params);
    }

    @Override
    public void buildMiddleView(ViewGroup parent) {

    }

    @Override
    public void buildRightView(ViewGroup parent) {
        int dimen = (int) rightIconDimen;
        //定义右侧布局
        rightLayout = new LinearLayout(getContext());
        rightLayout.setId(rightLayoutId);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        rightLayout.setOrientation(HORIZONTAL);
        rightLayout.setGravity(Gravity.CENTER_VERTICAL);
        //定义右侧图文布局
        contentLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentLayout.setOrientation(VERTICAL);
        contentLayout.setVisibility(GONE);
        //添加 右侧 可加载图片
        ivRight = new ImageView(getContext());
        ivRight.setVisibility(GONE);
        //添加 右侧 TextView
        tvRightContent = new TextView(getContext());
        tvRightContent.setVisibility(GONE);
        tvRightContent.setSingleLine(true);
        tvRightContent.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        tvRightContent.setTextAppearance(getContext(), rightContentStyle);
        if (!TextUtils.isEmpty(rightContent)) {
            rightPadding = dipLarge;
            tvRightContent.setText(rightContent);
            tvRightContent.setVisibility(VISIBLE);
            contentLayout.setVisibility(VISIBLE);
        }
        contentLayout.addView(ivRight);
        contentLayout.addView(tvRightContent);
        rightLayout.addView(contentLayout, lp);
        //添加 右侧 ImageView
        ivRightIcon = new ImageView(getContext());
        ivRightIcon.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen));
        ivRightIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ivRightIcon.setVisibility(GONE);
        if (rightIcon > 0) {
            ivRightIcon.setVisibility(VISIBLE);
            ivRightIcon.setImageResource(rightIcon);
            contentLayout.setPadding(0, 0, dipLarge, 0);
        }
        rightLayout.addView(ivRightIcon);
        //添加右侧
        parent.addView(rightLayout, params);
    }
}
