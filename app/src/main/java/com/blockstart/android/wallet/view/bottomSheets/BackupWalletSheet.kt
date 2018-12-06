package com.blockstart.android.wallet.view.bottomSheets

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.view.View
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.enums.BottomActionPresenter
import com.blockstart.android.wallet.viewModel.CreateWalletViewModel
import kotlinx.android.synthetic.main.fragment_confirm_password_bottom.*

class BackupWalletSheet : ConfirmActionBottomSheet() {

    private lateinit var model: CreateWalletViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tag = it.get(TAG) as BottomActionPresenter
        }
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackup()

        confirmActionBtn.setOnClickListener {
            model.backupEmail = textEntryField.text.toString()
            listener?.onActionClicked(tag)
        }
    }

    private fun setupBackup() {
        textEntryField.hint = SpannableStringBuilder(getString(R.string.type_email))
        textEntryField.inputType = InputType.TYPE_CLASS_TEXT
        confirmActionBtn.text = getString(R.string.send_backup)
        confirmActionBtn.setBackgroundResource(R.drawable.block_green_square_btn)
    }

    companion object {
        @JvmStatic
        fun newInstance(tag: BottomActionPresenter?) = BackupWalletSheet().apply {
            arguments = Bundle().apply {
                putSerializable(TAG, tag)
            }
        }
    }
}