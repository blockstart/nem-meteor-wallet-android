package com.blockstart.android.wallet.view.bottomSheets

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.fragment_confirm_password_bottom.*

class DeleteWalletSheet : ConfirmActionBottomSheet() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDeleteWallet()

        confirmActionBtn.setOnClickListener {
            listener?.onActionClicked(tag)
        }
    }

    private fun setupDeleteWallet() {
        textEntryField.hint = SpannableStringBuilder(getString(R.string.confirm_password))
        confirmActionBtn.text = getString(R.string.delete_wallet)
        confirmActionBtn.setBackgroundResource(R.drawable.block_red_square_btn)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DeleteWalletSheet()
    }
}