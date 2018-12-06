package com.blockstart.android.wallet.util

import android.text.Editable

class EditableCharacterDivider {
    companion object {
        const val DEFAULT_MODULO = 6
        const val DEFAULT_DIVIDER = '-'
        @JvmField val DEFAULT_REGEX = "-".toRegex()

        @JvmStatic
         fun buildDividedString(s: Editable, dividerModulo: Int = DEFAULT_MODULO, dividerChar: Char = DEFAULT_DIVIDER,
                                isDeleting: Boolean = false, regex: Regex = DEFAULT_REGEX) : String {
            val stripped = s.toString().replace(regex, EMPTY_STRING)
            val numDividers = if (stripped.length >= dividerModulo) stripped.length / dividerModulo else 0
            val chars = ArrayList<Char>()

            stripped.forEachIndexed { index, c ->
                chars.add(index, c)
            }

            for (x in 0 until numDividers) {
                val offset = dividerModulo + x
                chars.add((x * dividerModulo) + offset, dividerChar)
            }

            if (isDeleting && chars.last() == dividerChar) chars.removeAt(chars.lastIndex)
            return StringBuilder().append(chars.toCharArray()).toString()
        }

        @JvmStatic
        fun filterForAlphaNumeric(source: CharSequence, start: Int, end: Int) : CharSequence? {
            val chars = source.filter { Character.isLetterOrDigit(it) || it == DASH_CHAR }
            val allCharactersValid = chars.length == end - start && start != end
            return if (allCharactersValid) null else chars.toString()
        }
    }
}