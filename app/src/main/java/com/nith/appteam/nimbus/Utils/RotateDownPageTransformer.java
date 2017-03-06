package com.nith.appteam.nimbus.Utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by sahil on 4/3/17.
 */

public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private static final float ROT_MOD = -15f;
    @Override
    public void transformPage(View page, float position) {
        onPreTransform(page,position);
        onTransform(page,position);

    }

    private void onPreTransform(View view, float position) {
        final float width = view.getWidth();
        view.setRotationX(0);
        view.setRotationY(0);
        view.setRotation(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setPivotX(0);
        view.setPivotY(0);
        view.setTranslationY(0);
        view.setTranslationX(0f);
        view.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
        }



    private void onTransform(View view, float position) {
        final float width = view.getWidth();
        final float height = view.getHeight();
        final float rotation = ROT_MOD * position * -1.25f;

        view.setPivotX(width * 0.5f);
        view.setPivotY(height);
        view.setRotation(rotation);
    }
}
