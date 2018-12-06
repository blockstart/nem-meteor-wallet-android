package com.blockstart.android.wallet.view.bottomSheets

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.enums.BottomActionPresenter
import com.blockstart.android.wallet.viewModel.CreateWalletViewModel
import kotlinx.android.synthetic.main.fragment_confirm_password_bottom.*

class ConfirmPasswordSheet : ConfirmActionBottomSheet() {

    private lateinit var model: CreateWalletViewModel
    private var buttonColor: Int = 0
    private lateinit var buttonTitle : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            buttonColor = it.getInt(BUTTON_COLOR)
            buttonTitle = it.getString(BUTTON_TITLE)
            tag = it.get(TAG) as BottomActionPresenter
        }
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupConfirmSheet()

        confirmActionBtn.setOnClickListener {
            listener?.onActionClicked(tag)
        }
    }

    private fun setupConfirmSheet() {
        textEntryField.hint = SpannableStringBuilder(getString(R.string.confirm_password))
        textEntryField.transformationMethod = PasswordTransformationMethod.getInstance()
        confirmActionBtn.text = buttonTitle
        confirmActionBtn.setBackgroundResource(buttonColor)
    }

    companion object {
        const val BUTTON_COLOR = "buttonColor"
        const val BUTTON_TITLE = "buttonTitle"
        @JvmStatic
        fun newInstance(buttonTitle: String, buttonColor: Int, tag: BottomActionPresenter) = ConfirmPasswordSheet().apply {
            arguments = Bundle().apply {
                putString(BUTTON_TITLE, buttonTitle)
                putInt(BUTTON_COLOR, buttonColor)
                putSerializable(TAG, tag)
            }
        }
    }
}