package com.blockstart.android.wallet.util

import android.graphics.Typeface
import android.text.Annotation
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString

class StringAnnotationProcessor() {

    companion object {
        @JvmStatic
        fun styleNewFonts(string: SpannedString, fontKeys: ArrayList<String>, typefaces: ArrayList<Typeface>): SpannableString {
            val annotations = string.getSpans(0, string.length, Annotation::class.java)
            val spannableString = SpannableString(string)
            val hasMultipleFonts = fontKeys.size > 1
            annotations.forEachIndexed { index, annotation ->
                val fontKey = if (hasMultipleFonts) fontKeys[index] else fontKeys[0]
                val typeface = if (hasMultipleFonts) typefaces[index] else typefaces[0]
                if (annotation.key == "font") {
                    if (annotation.value == fontKey) {
                        spannableString.setSpan(CustomTypefaceSpan(typeface),
                                string.getSpanStart(annotation),
                                string.getSpanEnd(annotation),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
            }
            return spannableString
        }
    }
}