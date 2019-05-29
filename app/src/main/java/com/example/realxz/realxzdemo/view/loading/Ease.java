package com.example.realxz.realxzdemo.view.loading;

import android.view.animation.Interpolator;

/**
 * Created by ybq.
 */
class Ease {
    public static Interpolator inOut() {
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f);
    }
}
