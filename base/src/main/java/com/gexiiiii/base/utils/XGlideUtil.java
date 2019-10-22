package com.gexiiiii.base.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:02
 * description :
 */
public class XGlideUtil {

    public final static RoundedCornersTransformation.CornerType TYPR_ALL = RoundedCornersTransformation.CornerType.ALL;
    public final static RoundedCornersTransformation.CornerType TYPR_TOP = RoundedCornersTransformation.CornerType.TOP;
    public final static RoundedCornersTransformation.CornerType TYPR_BOTTOM = RoundedCornersTransformation.CornerType.BOTTOM;
    public final static RoundedCornersTransformation.CornerType TYPR_LEFT = RoundedCornersTransformation.CornerType.LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_RIGHT = RoundedCornersTransformation.CornerType.RIGHT;
    public final static RoundedCornersTransformation.CornerType TYPR_TOP_LEFT = RoundedCornersTransformation.CornerType.TOP_LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_TOP_RIGHT = RoundedCornersTransformation.CornerType.TOP_RIGHT;
    public final static RoundedCornersTransformation.CornerType TYPR_BOTTOM_LEFT = RoundedCornersTransformation.CornerType.BOTTOM_LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_BOTTOM_RIGHT = RoundedCornersTransformation.CornerType.BOTTOM_RIGHT;
    public final static RoundedCornersTransformation.CornerType TYPR_OTHER_TOP_LEFT = RoundedCornersTransformation.CornerType.OTHER_TOP_LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_OTHER_TOP_RIGHT = RoundedCornersTransformation.CornerType.OTHER_TOP_RIGHT;
    public final static RoundedCornersTransformation.CornerType TYPR_OTHER_BOTTOM_LEFT = RoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_OTHER_BOTTOM_RIGHT = RoundedCornersTransformation.CornerType.OTHER_BOTTOM_RIGHT;
    public final static RoundedCornersTransformation.CornerType TYPR_DIAGONAL_FROM_TOP_LEFT = RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT;
    public final static RoundedCornersTransformation.CornerType TYPR_DIAGONAL_FROM_TOP_RIGHT = RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT;

    public static class Builder<T> {

        private ImageView imageView;
        private T img;
        private int radius = 0;
        private int margin = 0;
        private RoundedCornersTransformation.CornerType cornerType = XGlideUtil.TYPR_ALL;
        private int blur = 0;
        private boolean isGray = false;

        private MultiTransformation multi;

        public Builder(ImageView imageView, T img) {
            this.imageView = imageView;
            this.img = img;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setCornerType(RoundedCornersTransformation.CornerType cornerType) {
            this.cornerType = cornerType;
            return this;
        }

        public Builder setBlur(int blur) {
            this.blur = blur;
            return this;
        }

        public Builder setGray(boolean isGray) {
            this.isGray = isGray;
            return this;
        }

        public Builder build() {
            int r = XDensityUtil.dip2px(imageView.getContext(), radius);
            int m = XDensityUtil.dip2px(imageView.getContext(), margin);
            if (isGray) {
                if (blur == 0) {
                    multi = new MultiTransformation<>(
                            new GrayscaleTransformation(),
                            new RoundedCornersTransformation(r, m, cornerType)
                    );
                } else {
                    multi = new MultiTransformation<>(
                            new BlurTransformation(blur),
                            new GrayscaleTransformation(),
                            new RoundedCornersTransformation(r, m, cornerType)
                    );
                }
            } else {
                if (blur == 0) {
                    multi = new MultiTransformation<>(
                            new RoundedCornersTransformation(r, m, cornerType)
                    );
                } else {
                    multi = new MultiTransformation<>(
                            new BlurTransformation(blur),
                            new RoundedCornersTransformation(r, m, cornerType)
                    );
                }
            }
            return this;
        }

        public MultiTransformation getTransformation() {
            if (multi == null) {
                Log.e("XGlideUtil", "ErrorMsg：null,you should build first!!");
            }
            return multi;
        }

        public void load() {
            if (multi == null) {
                Glide.with(imageView)
                        .load(img)
                        .into(imageView);
            } else {
                Glide.with(imageView)
                        .load(img)
                        .apply(bitmapTransform(multi))
                        .into(imageView);
            }
        }
    }
}
