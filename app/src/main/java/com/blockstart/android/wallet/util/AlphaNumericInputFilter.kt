package com.blockstart.android.wallet.util

import android.text.InputFilter
import android.text.Spanned

class AlphaNumericInputFilter : InputFilter {
    override fun filter(source: CharSequence, start: Int, end: Int,
                        dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        return EditableCharacterDivider.filterForAlphaNumeric(source, start, end)
    }
}