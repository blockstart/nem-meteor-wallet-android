package com.blockstart.android.wallet.view.createWallet

import android.animation.Animator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.fragment_all_set.*

class AllSetFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
    }

    private fun setupAnimation() {
        checkmarkAnimation.setAnimation(R.raw.checkmark_edited_green)
        checkmarkAnimation.speed = 0.6f
        checkmarkAnimation.playAnimation()
        checkmarkAnimation.addAnimatorListener(object : Animator.AnimatorListener {
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
        fun newInstance() = AllSetFragment()
    }
}
