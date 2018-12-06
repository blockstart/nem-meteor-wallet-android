package com.blockstart.android.wallet.view.createWallet

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.sheet_create_wallet.*

class CreateWalletBottomSheet : BottomSheetDialogFragment() {

    interface OnCreateWalletSheetListener {
        fun importWalletSelected()
        fun newWalletSelected()
    }

    private var listener : OnCreateWalletSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sheet_create_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelBtn.setOnClickListener {
            dismiss()
        }

        importWalletBtn.setOnClickListener {
            listener?.importWalletSelected()
            dismiss()
        }

        createNewWalletBtn.setOnClickListener {
            listener?.newWalletSelected()
            dismiss()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCreateWalletSheetListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }
}