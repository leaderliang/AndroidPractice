package com.android.ballot;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

/**
 * 卡片出现动画 dialog
 * 关于动画：
 * 从小到大，同时旋转出现
 * 缩放
 *
 * @author devliang
 * @date 2020年11月12日 下午2:12
 */
public class CourseCardDialog extends Dialog {
    private Activity mContext;
    private Dialog mDialog;
    private FrameLayout mFrameLayout, mLottieLayout;
    private LottieAnimationView mLottieAnimationView;
    private LottieAnimationView mGuideLottieView;
    private RelativeLayout mChildLayout;
    private ClickDialogLister clickDialogLister;


    public CourseCardDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        mDialog = new Dialog(mContext, R.style.Room_ActionSheetDialogStyle);
        mDialog.setContentView(R.layout.room_view_opening_branch);
        mDialog.setCanceledOnTouchOutside(false);
        mFrameLayout = mDialog.findViewById(R.id.fl_root_view);
        mLottieLayout = mDialog.findViewById(R.id.fl_child_view);

        Window window = mDialog.getWindow();
        window.setDimAmount(0f);
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)));

        mLottieAnimationView = new LottieAnimationView(mContext);
        mLottieAnimationView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLottieLayout.addView(mLottieAnimationView);
        try {
            LottieAnimatorUtil.showLottieByRaw(mLottieAnimationView, R.raw.circle, true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLottieAnimationView.setVisibility(View.GONE);
        mDialog.setOnCancelListener(dialog -> mContext.finish());
        initChildView();
    }

    private void initChildView() {
        mChildLayout = new RelativeLayout(mContext);
        mChildLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mChildLayout.setVisibility(View.GONE);
        mChildLayout.setGravity(Gravity.CENTER);

//        mChildLayout.setRotationY(180f);
        ImageView childImageView = new ImageView(mContext);
        childImageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        childImageView.setAdjustViewBounds(true);
        mChildLayout.addView(childImageView);
        childImageView.setBackgroundResource(R.mipmap.bgg1);
        childImageView.setOnClickListener(v -> {
            if (DoubleUtils.isFastDoubleClick()) {
                return;
            }
            mLottieAnimationView.cancelAnimation();
            // 执行回调
            closeDialog();
            mFrameLayout.setVisibility(View.GONE);
            if (clickDialogLister != null) {
                clickDialogLister.dialogClick();
            }
        });

        mLottieLayout.addView(mChildLayout);
        branchVewStartAnimation();
    }

    private void branchVewStartAnimation() {
        AnimationUtils.courseCardAnimation(mContext,mChildLayout, mLottieAnimationView, animation -> {
            /*镜头视角:改变视角, 涉及到旋转卡片的Y轴, 即rotationY, 需要修改视角距离; 如果不修改, 则会超出屏幕高度, 影响视觉体验.*/
            float distance = 10000f;
            float scale = mContext.getResources().getDisplayMetrics().density;
            mChildLayout.setCameraDistance(distance * scale);
            float value = (float) animation.getAnimatedValue();

        }, new MyAnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {
                mChildLayout.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mChildLayout.setEnabled(true);
            }
        });
    }

    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception exception) {
                Log.e("Dialog", exception.toString());
            }
        }
    }


    public void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setClickDialogLister(ClickDialogLister clickDialogLister) {
        this.clickDialogLister = clickDialogLister;
    }

    public interface ClickDialogLister {
        void dialogClick();
    }


}
