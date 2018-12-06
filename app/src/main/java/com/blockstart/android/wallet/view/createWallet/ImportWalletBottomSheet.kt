package com.blockstart.android.wallet.view.createWallet

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import kotlinx.android.synthetic.main.sheet_import_wallet.*

class ImportWalletBottomSheet : BottomSheetDialogFragment() {

        interface OnImportWalletSheetListener {
            fun importWithQrCode()
            fun importWithPrivateKey()
        }

        private var listener : OnImportWalletSheetListener? = null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.sheet_import_wallet, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            qr_code.setOnClickListener {
                listener?.importWithQrCode()
                dismiss()
            }

            private_key.setOnClickListener {
                listener?.importWithPrivateKey()
                dismiss()
            }
        }

        override fun onAttach(context: Context?) {
            super.onAttach(context)
            if (context is OnImportWalletSheetListener) {
                listener = context
            } else {
                throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
            }
        }
}