package com.blockstart.android.wallet.view.sendTransaction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.fragment_transaction_sent.*
import android.animation.Animator

class TransactionSentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction_sent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
    }

    fun setupAnimation() {
        rocketAnimation.setAnimation(R.raw.nem_to_the_moon_closer)
        rocketAnimation.scaleType = ImageView.ScaleType.CENTER_CROP
        rocketAnimation.speed = 1.2f
        rocketAnimation.setMaxProgress(.994f)
        rocketAnimation.playAnimation()
        rocketAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                activity?.finish()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionSentFragment()
    }
}
