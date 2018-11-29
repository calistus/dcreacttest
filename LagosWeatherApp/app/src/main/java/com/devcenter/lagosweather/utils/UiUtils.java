package com.devcenter.lagosweather.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.devcenter.lagosweather.LagosWeatherApp;


/**
 * @author Ilo Calistus
 */

public class UiUtils {


    // Suppress default constructor for non-instantiability
    private UiUtils() {
        throw new AssertionError();
    }

    private static String TAG = UiUtils.class.getSimpleName();

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showSafeToast(final String toastMessage) {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LagosWeatherApp.getInstance(), toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnMain(final @NonNull Runnable runnable) {
        if (isMainThread()) runnable.run();
        else handler.post(runnable);
    }


    private static Animation getAnimation(Context context, int animationId) {
        return AnimationUtils.loadAnimation(context, animationId);
    }

    private static synchronized void animateView(View view, Animation animation) {
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    private static ProgressDialog operationsProgressDialog;

    public static void showProgressDialog(final Context context, final String message) {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                operationsProgressDialog = new ProgressDialog(context);
                operationsProgressDialog.setMessage(message);
                operationsProgressDialog.show();
            }
        });
    }

    public static void dismissAllProgressDialogs() {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                try {
                    if (operationsProgressDialog != null) {
                        if (operationsProgressDialog.isShowing()) {
                            operationsProgressDialog.dismiss();
                        }
                    }
                } catch (WindowManager.BadTokenException e) {
                    LogUtils.log(TAG, "Dialog has leaked state" + e.getMessage());
                }
            }
        });
    }




}
