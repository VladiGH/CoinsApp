package com.naldana.ejemplo10.utilities

import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

class ViewAnimator {

    fun getRotationAnimation(): AnimationSet {
        val animRotate = RotateAnimation(
            0.0f, 360.0f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 2500
            fillAfter = true
            repeatCount = 3
        }
        return AnimationSet(true).apply {
            interpolator = LinearInterpolator()
            fillAfter = true
            isFillEnabled = true
            addAnimation(animRotate)
        }
    }

}