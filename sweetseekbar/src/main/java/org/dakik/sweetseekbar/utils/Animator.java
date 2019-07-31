package org.dakik.sweetseekbar.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

import org.dakik.sweetseekbar.R;

public class Animator {
    public static void bounceToBig(final View v) {
        Animation a= AnimationUtils.loadAnimation(v.getContext(),R.anim.bounce_anim_make_big);
        v.startAnimation(a);
    }
    public static void bounceToSmall(final View v) {
        Animation a= AnimationUtils.loadAnimation(v.getContext(),R.anim.bounce_anim_make_small);
        v.startAnimation(a);
    }
}
