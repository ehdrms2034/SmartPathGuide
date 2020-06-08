/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.utility

import android.animation.Animator

abstract class PointAnimator : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {
        /* explicitly empty */
    }

    override fun onAnimationCancel(animation: Animator?) {
        /* explicitly empty */
    }

    override fun onAnimationStart(animation: Animator?) {
        /* explicitly empty */
    }
}
