package com.gexiiiii.base.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

/**
 * author : Gexiiiii
 * date : 2019/10/22 9:58
 * description :
 */
public class XLoadingDialog {

    private ProgressDialog progressDialog;
    private LoadingCallback loadingCallback;
    private Handler handler = new Handler();

    private XLoadingDialog(Context ctx) {
        progressDialog = new ProgressDialog(ctx);
    }

    private void setMessage(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            progressDialog.setMessage(msg);
        }
    }

    private long showTime;

    private void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public void show() {
        progressDialog.show();
        if (showTime > 0) {
            handler.postDelayed(cancelRunnable, showTime);
        }
    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        handler.removeCallbacks(cancelRunnable);
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }

    private Runnable cancelRunnable = new Runnable() {
        @Override
        public void run() {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (loadingCallback != null) {
                    loadingCallback.onTimeOut();
                }
            }
        }
    };

    private void setLoadingCallback(LoadingCallback loadingCallback) {
        this.loadingCallback = loadingCallback;
    }

    public static class Builder {

        private Context context;
        private XLoadingDialog loadingDialog;

        public Builder(Context ctx) {
            context = ctx;
            loadingDialog = new XLoadingDialog(context);
        }

        public Builder setMessage(String msg) {
            loadingDialog.setMessage(msg);
            return this;
        }

        public Builder setShowTime(long time) {
            if (time < 0) {
                return this;
            }
            loadingDialog.setShowTime(time);
            return this;
        }

        public Builder setLoadingCallback(LoadingCallback loadingCallback) {
            loadingDialog.setLoadingCallback(loadingCallback);
            return this;
        }

        public XLoadingDialog build() {
            return loadingDialog;
        }

        public void show() {
            loadingDialog.show();
        }
    }

    public interface LoadingCallback {
        void onTimeOut();
    }
}
