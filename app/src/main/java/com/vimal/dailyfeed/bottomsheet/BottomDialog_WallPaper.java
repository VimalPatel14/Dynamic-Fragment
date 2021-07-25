/**
 * Created by Vimal on March-2021.
 */
package com.vimal.dailyfeed.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;

import com.vimal.dailyfeed.R;


public class BottomDialog_WallPaper {

    protected final Builder mBuilder;
    protected TextView vTitle;
    LinearLayout home_wallpaper, lock_wallpaper, home_n_lock;


    public final Builder getBuilder() {
        return mBuilder;
    }

    public final TextView getTitleTextView() {
        return vTitle;
    }


    protected BottomDialog_WallPaper(Builder builder) {
        mBuilder = builder;
        mBuilder.bottomDialog = initBottomDialog(builder);
    }

    @UiThread
    public void show() {
        if (mBuilder != null && mBuilder.bottomDialog != null)
            mBuilder.bottomDialog.show();
    }

    @UiThread
    public void dismiss() {
        if (mBuilder != null && mBuilder.bottomDialog != null)
            mBuilder.bottomDialog.dismiss();
    }

    @UiThread
    private Dialog initBottomDialog(final Builder builder) {
        final Dialog bottomDialog = new Dialog(builder.context, R.style.BottomDialogs);
        View view = LayoutInflater.from(builder.context).inflate(R.layout.wallpaper_bottom_dialog, null);

        vTitle = view.findViewById(R.id.bottomDialog_title);
        home_wallpaper = view.findViewById(R.id.home_wallpaper);
        lock_wallpaper = view.findViewById(R.id.lock_wallpaper);
        home_n_lock = view.findViewById(R.id.home_n_lock);

        home_wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.btn_home != null)
                    builder.btn_home.onClick(BottomDialog_WallPaper.this);
            }
        });

        lock_wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.btn_lock != null)
                    builder.btn_lock.onClick(BottomDialog_WallPaper.this);
            }
        });

        home_n_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.btn_both != null)
                    builder.btn_both.onClick(BottomDialog_WallPaper.this);
            }
        });

        if (builder.title != null) {
            vTitle.setText(builder.title);
        }


        if (builder.customView != null) {
            if (builder.customView.getParent() != null)
                ((ViewGroup) builder.customView.getParent()).removeAllViews();
        }

        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(builder.isCancelable);
        bottomDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);

        return bottomDialog;
    }

    public static class Builder {
        protected Context context;

        // Bottom Dialog
        protected Dialog bottomDialog;

        // Icon, Title and Content
        protected Drawable icon;
        protected CharSequence title, content;

        // Buttons
        protected ButtonCallback btn_negative_callback, btn_positive_callback,
                btn_home, btn_lock, btn_both;
        protected boolean isAutoDismiss;

        // Button text colors
        protected int btn_colorNegative, btn_colorPositive;

        // Button background colors
        protected int btn_colorPositiveBackground;

        // Custom View
        protected View customView;
        protected int customViewPaddingLeft, customViewPaddingTop, customViewPaddingRight, customViewPaddingBottom;

        // Other options
        protected boolean isCancelable;

        public Builder(@NonNull Context context) {
            this.context = context;
            this.isCancelable = true;
            this.isAutoDismiss = true;
        }

        public Builder setTitle(@StringRes int titleRes) {
            setTitle(this.context.getString(titleRes));
            return this;
        }

        public Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setContent(@StringRes int contentRes) {
            setContent(this.context.getString(contentRes));
            return this;
        }

        public Builder setContent(@NonNull CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder setIcon(@NonNull Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setIcon(@DrawableRes int iconRes) {
            this.icon = ResourcesCompat.getDrawable(context.getResources(), iconRes, null);
            return this;
        }

        public Builder setPositiveBackgroundColorResource(@ColorRes int buttonColorRes) {
            this.btn_colorPositiveBackground = ResourcesCompat.getColor(context.getResources(), buttonColorRes, null);
            return this;
        }

        public Builder setPositiveBackgroundColor(int color) {
            this.btn_colorPositiveBackground = color;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder autoDismiss(boolean autodismiss) {
            this.isAutoDismiss = autodismiss;
            return this;
        }

        public Builder onHomeScreen(@NonNull ButtonCallback buttonCallback) {
            this.btn_home = buttonCallback;
            return this;
        }

        public Builder onLockScreen(@NonNull ButtonCallback buttonCallback) {
            this.btn_lock = buttonCallback;
            return this;
        }

        public Builder onBothScreen(@NonNull ButtonCallback buttonCallback) {
            this.btn_both = buttonCallback;
            return this;
        }

        public Builder setCustomView(View customView) {
            this.customView = customView;
            this.customViewPaddingLeft = 0;
            this.customViewPaddingRight = 0;
            this.customViewPaddingTop = 0;
            this.customViewPaddingBottom = 0;
            return this;
        }

        public Builder setCustomView(View customView, int left, int top, int right, int bottom) {
            this.customView = customView;
            this.customViewPaddingLeft = UtilsLibrary_Ringtone.dpToPixels(context, left);
            this.customViewPaddingRight = UtilsLibrary_Ringtone.dpToPixels(context, right);
            this.customViewPaddingTop = UtilsLibrary_Ringtone.dpToPixels(context, top);
            this.customViewPaddingBottom = UtilsLibrary_Ringtone.dpToPixels(context, bottom);
            return this;
        }

        @UiThread
        public BottomDialog_WallPaper build() {
            return new BottomDialog_WallPaper(this);
        }

        @UiThread
        public BottomDialog_WallPaper show() {
            BottomDialog_WallPaper bottomDialog = build();
            bottomDialog.show();
            return bottomDialog;
        }

    }

    public interface ButtonCallback {

        void onClick(@NonNull BottomDialog_WallPaper dialog);
    }

}
