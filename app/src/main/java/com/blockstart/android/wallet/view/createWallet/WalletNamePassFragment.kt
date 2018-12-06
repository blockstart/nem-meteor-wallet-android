package com.blockstart.android.wallet.view.createWallet

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannedString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.util.SOURCE_SANS_PRO_BOLD
import com.blockstart.android.wallet.util.StringAnnotationProcessor
import com.blockstart.android.wallet.viewModel.CreateWalletViewModel
import kotlinx.android.synthetic.main.fragment_wallet_name_pass.*

class WalletNamePassFragment : Fragment() {

    private var listener: OnWalletNameAndPassListener? = null
    private lateinit var model: CreateWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet_name_pass, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWalletNameAndPassListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnWalletNameAndPassListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context ?: return
        val typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_bold) ?: return
        val titleText = getText(R.string.name_and_password_title) as SpannedString
        namePasswordTitleTxt.text = StringAnnotationProcessor.styleNewFonts(titleText, arrayListOf(SOURCE_SANS_PRO_BOLD), arrayListOf(typeface))

        nameAndPassNextBtn.setOnClickListener {
            model.password = password_edit_text.text.toString()
            model.walletName = privateKeyEditTxt.text.toString()
            listener?.onNewWalletNameAndPassEntered()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnWalletNameAndPassListener {
        fun onNewWalletNameAndPassEntered()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WalletNamePassFragment()
    }
}
