package com.blockstart.android.wallet.view.bottomSheets

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.animation.AnimationUtils
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.fragment_confirm_password_bottom.*
import android.view.MotionEvent
import android.view.GestureDetector
import com.blockstart.android.wallet.enums.BottomActionPresenter
import com.blockstart.android.wallet.view.sendTransaction.SendHomeActivity

open class ConfirmActionBottomSheet : Fragment(), GestureDetector.OnGestureListener {

    interface ConfirmBottomActionListener {
        fun onActionClicked(tag: BottomActionPresenter)
        fun onDismissConfirmSheet()
        fun onDismissKeyboard()
    }

    companion object {
        const val TAG = "tag"
    }

    protected var listener: ConfirmBottomActionListener? = null
    protected lateinit var tag: BottomActionPresenter
    private lateinit var gestureScanner: GestureDetector
    private val flingThreshold = 50

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_confirm_password_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        bgView.setOnClickListener {
            listener?.onDismissConfirmSheet()
        }

        sheetHolder.setOnTouchListener { v, event ->
            gestureScanner.onTouchEvent(event)
            true
        }

        gestureScanner = GestureDetector(context, this)
        val enterBottom = AnimationUtils.loadAnimation(context, R.anim.enter_bottom)
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        sheetHolder.startAnimation(enterBottom)
        bgView.startAnimation(fadeIn)
        textEntryField.requestFocus()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ConfirmBottomActionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + "must implement SheetListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        listener = null
    }

    override fun onStop() {
        super.onStop()
        val exitBottom = AnimationUtils.loadAnimation(context, R.anim.exit_bottom)
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        sheetHolder.startAnimation(exitBottom)
        bgView.startAnimation(fadeOut)
    }

    override fun onShowPress(e: MotionEvent?) {}
    override fun onSingleTapUp(e: MotionEvent?): Boolean { return false }
    override fun onDown(e: MotionEvent?): Boolean { return false }
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean { return false }
    override fun onLongPress(e: MotionEvent?) {}
    override fun onFling(motionEvent1: MotionEvent, motionEvent2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        if (motionEvent2.y - motionEvent1.y > flingThreshold) {
            listener?.onDismissKeyboard()
            activity?.supportFragmentManager?.popBackStack(SendHomeActivity.SEND_BACKSTACK, 0)
            return true
        }
        return false
    }
}