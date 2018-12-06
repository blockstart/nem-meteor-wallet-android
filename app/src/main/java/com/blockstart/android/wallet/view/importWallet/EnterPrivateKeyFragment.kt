package com.blockstart.android.wallet.view.importWallet

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.viewModel.CreateWalletViewModel
import kotlinx.android.synthetic.main.fragment_enter_private_key.*

class EnterPrivateKeyFragment : Fragment() {

    private var listener: OnPrivateKeyEnteredListener? = null
    private lateinit var model: CreateWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(CreateWalletViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_enter_private_key, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        privateKeyNextBtn.setOnClickListener {
            val key = privateKeyEditTxt.text.toString()
            if (key.isNotEmpty()) {
                model.privateKey = privateKeyEditTxt.text.toString()
                listener?.onPrivateKeyEntered()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPrivateKeyEnteredListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnPrivateKeyEnteredListener {
        fun onPrivateKeyEntered()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EnterPrivateKeyFragment()
    }
}
