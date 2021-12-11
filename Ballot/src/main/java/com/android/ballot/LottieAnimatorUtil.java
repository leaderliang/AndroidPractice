package com.android.ballot;

import android.animation.Animator;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;

import java.io.File;
import java.io.FileInputStream;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2020/12/03 22:51
 */
public class LottieAnimatorUtil {

    public static void showLottieEffectsBySD(LottieAnimationView lottieView,
                                             String jsonFilePath, boolean isRepeat, OnLottieAnimatorListener
                                                     listener) throws Exception {
        File jsonFile = new File(jsonFilePath);
        FileInputStream fileInputStream = null;
        if (jsonFile.exists()) {
            fileInputStream = new FileInputStream(jsonFile);
        }
        if (fileInputStream == null) {
            if (listener != null) {
                listener.onAnimationEnd();
            }
            if (lottieView != null) {
                lottieView.removeAllAnimatorListeners();
            }
            return;
        }
        //cacheKey一定要用null,否则会有缓存，每次都是第一次的动画
        LottieCompositionFactory.fromJsonInputStream(fileInputStream, null).addListener(new MyLottieListener(lottieView, listener));
        lottieView.removeAllAnimatorListeners();
        if (isRepeat) {
            lottieView.setRepeatCount(-1);
        }
        lottieView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    /**
     * 播放raw目录下面lottie文件
     *
     * @param lottieAnimationView
     * @param jsonRawPath
     * @param listener
     */
    public static void showLottieByRaw(LottieAnimationView lottieAnimationView, int jsonRawPath, boolean isRepeat, OnLottieAnimatorListener
            listener) {
        if (lottieAnimationView != null) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.removeAllAnimatorListeners();
            lottieAnimationView.setAnimation(jsonRawPath);
            lottieAnimationView.setRepeatMode(LottieDrawable.RESTART);
            if (isRepeat) {
                lottieAnimationView.setRepeatCount(-1);
            }
            lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (listener != null) {
                        listener.onAnimationStart();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (listener != null) {
                        listener.onAnimationEnd();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if (listener != null) {
                        listener.onAnimationEnd();
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            lottieAnimationView.playAnimation();
        } else {
            if (listener != null) {
                listener.onAnimationEnd();
            }
        }

    }

    private static class MyLottieListener implements LottieListener<LottieComposition> {

        private LottieAnimationView mView;
        private OnLottieAnimatorListener mListener;

        private MyLottieListener(LottieAnimationView view, OnLottieAnimatorListener listener) {
            mView = view;
            mListener = listener;
        }

        @Override
        public void onResult(LottieComposition result) {
            if (mView != null) {
                mView.setVisibility(View.VISIBLE);
                mView.setComposition(result);
                mView.playAnimation();
            }
            if (mListener != null) {
                mListener.onAnimationStart();
            }
        }
    }


    public interface OnLottieAnimatorListener {
        void onAnimationStart();

        void onAnimationEnd();
    }

}
