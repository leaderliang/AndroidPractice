package com.android.ballot;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

/**
 * 分支欢迎动画 dialog
 * 关于手势动画：
 * 手势提示：语音题干播放完毕后开始计时5s，手势动画（同游戏）提示任意选项，
 * 动画播放完毕后手势消失，从动画开始播放的节点算起，再过5s再出现手势
 *
 * @author devliang
 * @date 2020年11月12日 下午2:12
 */
public class CourseOpeningBranchDialog extends Dialog {
    private StringBuilder mStringBuilder;
    private Activity mContext;
    private Dialog mDialog;
    private FrameLayout mFrameLayout, mLottieLayout;
    private LottieAnimationView mLottieAnimationView;
    private LottieAnimationView mGuideLottieView;
    private LinearLayout mChildLayout;
    private String[] mStrArray;


    public CourseOpeningBranchDialog(@NonNull Activity context, StringBuilder stringBuilder) {
        super(context);
        this.mContext = context;
        this.mStringBuilder = stringBuilder;
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
                LottieAnimatorUtil.showLottieByRaw(mLottieAnimationView, R.raw.star, true, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        mDialog.setOnCancelListener(dialog -> {
            mContext.finish();
        });
        initChildView();
    }

    private void initChildView() {
        mChildLayout = new LinearLayout(mContext);
        mChildLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mChildLayout.setVisibility(View.GONE);
        mChildLayout.setGravity(Gravity.CENTER);
        mChildLayout.setOrientation(LinearLayout.VERTICAL);
        // 提前设置，保证翻转后图片文字方向正确
        mChildLayout.setRotationY(180f);
        if (mStringBuilder == null) {
            return;
        }
        mStrArray = mStringBuilder.toString().split(";",2);
        for (int i = 0; i < mStrArray.length; i++) {
            FrameLayout childView = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.room_activity_branch, null);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            childParams.setMargins(0, 0, 0, Dimensions.dip2px(25f));
            childView.setLayoutParams(childParams);

            mChildLayout.addView(childView);
            int position = i;

            childView.setOnClickListener(v -> {
                if (DoubleUtils.isFastDoubleClick()) {
                    return;
                }
                mLottieAnimationView.cancelAnimation();
                // 执行回调
                closeDialog();
                mFrameLayout.setVisibility(View.GONE);
                if (clickBranchDialogLister != null) {
                    clickBranchDialogLister.branchDialogClick();
                }

                /*if (mChildLayout.getChildCount() > 1) {
                    Rect fromR = new Rect();
                    v.getGlobalVisibleRect(fromR);
                    int viewTargetY = (Dimensions.getScreenHeight(mContext) - fromR.height()) / 2;
                    int moveDistance = fromR.height() / 2;//Dimensions.dip2px(25f)
                    PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", viewTargetY - fromR.top > 0 ? moveDistance + Dimensions.dip2px(25f) : -moveDistance);
                    ObjectAnimator translationAnimator = ObjectAnimator.ofPropertyValuesHolder(v, translationY);
                    translationAnimator.setDuration(600);
                    translationAnimator.addListener(new MyAnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            for (int j = 0; j < mChildLayout.getChildCount(); j++) {
                                if (v != mChildLayout.getChildAt(j)) {
                                    mChildLayout.getChildAt(j).setVisibility(View.INVISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFrameLayout.postDelayed(() -> {
                                mLottieAnimationView.cancelAnimation();
                                mFrameLayout.setVisibility(View.GONE);
                                // 执行回调
                                closeDialog();
                                if (clickBranchDialogLister != null) {
                                    clickBranchDialogLister.branchDialogClick();
                                }
                            }, 600);
                        }
                    });
                    translationAnimator.start();
                } else {
                    mLottieAnimationView.cancelAnimation();
                    mFrameLayout.setVisibility(View.GONE);
                    closeDialog();
                    if (clickBranchDialogLister != null) {
                        clickBranchDialogLister.branchDialogClick();
                    }
                }*/
            });
        }
        mLottieLayout.addView(mChildLayout);
        branchVewStartAnimation();
    }

    private void branchVewStartAnimation() {
        AnimationUtils.branchVewStartAnimation(mContext, mChildLayout, animation -> {
            /*镜头视角:改变视角, 涉及到旋转卡片的Y轴, 即rotationY, 需要修改视角距离; 如果不修改, 则会超出屏幕高度, 影响视觉体验.*/
            float distance = 10000f;
            float scale = mContext.getResources().getDisplayMetrics().density;
            mChildLayout.setCameraDistance(distance * scale);
            float value = (float) animation.getAnimatedValue();
            // 改变图片
            int childCount = mChildLayout.getChildCount();
            if (childCount > 1) {
                for (int i = 0; i < childCount; i++) {
                    FrameLayout childView = (FrameLayout) mChildLayout.getChildAt(i);
                    ImageView ivBranchCourse = childView.findViewById(R.id.iv_branch_course);
                    ImageView ivBear = childView.findViewById(R.id.iv_bear);
                    TextView tvBranchCourseName = childView.findViewById(R.id.tv_branch_course_name);


                    if (value <= 90.0f) {
                        tvBranchCourseName.setBackgroundResource(R.mipmap.room_branch_text_bg_front);
//                        ivBranchCourse.setImageResource(R.mipmap.tam);
                        Glide.with(mContext).load(R.mipmap.bgg2)
                                .transform(new MultiTransformation(new CenterCrop(), new RoundedCorners(20)))
                                .into(ivBranchCourse);
                        tvBranchCourseName.setText(mStrArray[i]);
                        ivBear.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }, new MyAnimatorListener() {
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

    private ClickBranchDialogLister clickBranchDialogLister;

    public void setClickBranchDialogLister(ClickBranchDialogLister clickBranchDialogLister) {
        this.clickBranchDialogLister = clickBranchDialogLister;
    }

    public interface ClickBranchDialogLister {
        void branchDialogClick();
    }


}
