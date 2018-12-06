package com.blockstart.android.wallet.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.view.sendTransaction.SendHomeFragment

class DividerEditText : AppCompatEditText {

    private lateinit var mClearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        setupEditText()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupEditText()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setupEditText()
    }

    private fun setupEditText() {
        filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(SendHomeFragment.FULL_ADDRESS_LENGTH), AlphaNumericInputFilter())
        inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

        var isUpdating = false
        var priorLength = 0
        var isDeleting = false
        mClearButtonImage = ResourcesCompat.getDrawable(resources,
                R.drawable.ic_cancel_gray_24dp, null) ?: return

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if(s.isEmpty()){
                        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
                    }else{
                        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null)
                    }

                    if (isUpdating) return else isUpdating = true
                    if (priorLength > s.count()) isDeleting = true
                    if (s.isNotEmpty()) s.replace(0, s.length,
                                EditableCharacterDivider.buildDividedString(s, isDeleting = isDeleting))
                    isUpdating = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                priorLength = s?.count() ?: 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText("")
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }
}