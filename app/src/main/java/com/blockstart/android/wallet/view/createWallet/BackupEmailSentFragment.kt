package com.blockstart.android.wallet.view.createWallet

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.viewModel.CreateWalletViewModel
import kotlinx.android.synthetic.main.fragment_backup_email_sent.*

class BackupEmailSentFragment : Fragment() {

    private var listener: OnEmailSentConfirmationReceived? = null
    private lateinit var model: CreateWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_backup_email_sent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundsGoodBtn.setOnClickListener {
            listener?.soundsGoodFromBackup()
        }
        sentEmailTxt.text = model.backupEmail
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEmailSentConfirmationReceived) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnEmailSentConfirmationReceived {
        fun soundsGoodFromBackup()
    }

    companion object {
        @JvmStatic
        fun newInstance() = BackupEmailSentFragment()
    }
}
