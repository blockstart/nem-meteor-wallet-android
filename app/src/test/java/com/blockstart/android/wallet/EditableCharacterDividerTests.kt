package com.blockstart.android.wallet

import android.text.SpannableStringBuilder
import com.blockstart.android.wallet.util.EMPTY_STRING
import com.blockstart.android.wallet.util.EditableCharacterDivider
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.Assert.*

class EditableCharacterDividerTests {

    private val rawAddress: SpannableStringBuilder = mock(name = "SBK6UHWEH6XOTZSK2O7MB5RLVZTJEL44H3N4QXPO")

    @Test
    fun inputOfSpecialCharactersNotReturned() {
        val source = "ASDF!@#$%^&*()_+=-ASDF|~`<>,.?/;:'-ASDF-asdf-1234"
        val result = EditableCharacterDivider.filterForAlphaNumeric(source, 0, source.count())
        assertEquals("ASDF-ASDF-ASDF-asdf-1234", result)
    }

    @Test
    fun inputOfFullAddressReturnsProperlyDivided() {
        val result = EditableCharacterDivider.buildDividedString(rawAddress)
        val expected = "SBK6UH-WEH6XO-TZSK2O-7MB5RL-VZTJEL-44H3N4-QXPO"
        assertEquals(result, expected)
    }

    @Test
    fun testWithOtherDividerAndModulo() {
        val divider = '_'
        val dividerModulo = 4
        val regex = "_".toRegex()
        val result = EditableCharacterDivider.buildDividedString(rawAddress, dividerModulo, divider, false, regex)
        val expected = "SBK6_UHWE_H6XO_TZSK_2O7M_B5RL_VZTJ_EL44_H3N4_QXPO_"
        assertEquals(result, expected)
    }

    @Test
    fun testProperBehaviorWhenDeletingFromEnd() {
        val initialInput : SpannableStringBuilder = mock(name = "ASDFAS-")
        val deletedDash = EditableCharacterDivider.buildDividedString(initialInput, isDeleting = true)
        assertEquals(deletedDash, "ASDFAS")
    }

    @Test
    fun testProperBehaviorWhenDeletingFromMiddle() {
        val initialInput = "ASDFAS-ASDF"
        val deletedInput = initialInput.removeRange(2,3)
        val deletedSpannable : SpannableStringBuilder = mock(name = deletedInput)
        val newDashed = EditableCharacterDivider.buildDividedString(deletedSpannable)
        assertEquals(newDashed, "ASFASA-SDF")
    }

    @Test
    fun testOnlyCharacterIsInvalidNoFail() {
        val singleInvalidCharacter : SpannableStringBuilder = mock(name = "$")
        val filtered = EditableCharacterDivider.filterForAlphaNumeric(singleInvalidCharacter, 0, singleInvalidCharacter.count())
        val editableResult : SpannableStringBuilder = mock(name = filtered.toString())
        val result = EditableCharacterDivider.buildDividedString(editableResult)
        assertEquals(result, EMPTY_STRING)
    }

    @Test
    fun testEmptyStringNoFail() {
        val emptyString : SpannableStringBuilder = mock(name = "")
        val filtered = EditableCharacterDivider.filterForAlphaNumeric(emptyString, 0, emptyString.count())
        val editableResult : SpannableStringBuilder = mock(name = filtered.toString())
        val result = EditableCharacterDivider.buildDividedString(editableResult)
        assertEquals(result, EMPTY_STRING)
    }
}