package com.tisser.puneet.tisserartisan.UI.Custom;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;

public final class TransitionUtils {

    /**
     * Returns a modified enter transition that excludes the navigation bar and status
     * bar as targets during the animation. This ensures that the navigation bar and
     * status bar won't appear to "blink" as they fade in/out during the transition.
     */
    public static Transition makeFadeEnterTransition() {
        Transition t = new Fade();
        t.excludeTarget(android.R.id.navigationBarBackground, true);
        t.excludeTarget(android.R.id.statusBarBackground, true);
        return t;
    }

    public static Transition makeEnterExplodeTransition() {
        Transition t = new Explode();
        t.excludeTarget(android.R.id.navigationBarBackground, true);
        t.excludeTarget(android.R.id.statusBarBackground, true);
        return t;
    }

    public static Transition makeEnterSlideDownTransition() {
        Transition t = new Slide();
        t.excludeTarget(android.R.id.navigationBarBackground, true);
        t.excludeTarget(android.R.id.statusBarBackground, true);
        return t;
    }

    private TransitionUtils() {
    }
}
