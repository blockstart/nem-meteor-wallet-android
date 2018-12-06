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
import kotlinx.android.synthetic.main.fragment_backup_wallet.*


class BackupWalletFragment : Fragment() {

    private var listener: OnBackupWalletListener? = null
    private lateinit var model: CreateWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_backup_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context ?: return
        val bold = ResourcesCompat.getFont(context, R.font.source_sans_pro_bold) ?: return
        val titleText = getText(R.string.wallet_backup_intro) as SpannedString
        backUpWalletIntroTxt.text = StringAnnotationProcessor.styleNewFonts(titleText, arrayListOf(SOURCE_SANS_PRO_BOLD), arrayListOf(bold))

        backUpWalletBtn.setOnClickListener {
            listener?.backUpWalletSelected()
        }

        doThisLaterBtn.setOnClickListener {
            listener?.onDoThisLaterSelected()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackupWalletListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnBackupWalletListener {
        fun backUpWalletSelected()
        fun onDoThisLaterSelected()
    }

    companion object {
        @JvmStatic
        fun newInstance() = BackupWalletFragment()
    }
}
