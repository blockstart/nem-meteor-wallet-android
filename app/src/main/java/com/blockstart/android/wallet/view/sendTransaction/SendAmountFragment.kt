package com.blockstart.android.wallet.view.sendTransaction

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import kotlinx.android.synthetic.main.fragment_send_amount.*

class SendAmountFragment : Fragment() {

    private lateinit var recipientAddress: String
    private lateinit var selectedMosaic: Mosaic
    private var listener: OnAmountSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipientAddress = it.getString(SendHomeFragment.RECIPIENT_ADDRESS)
            selectedMosaic = it.getParcelable(SendHomeFragment.SELECTED_MOSAIC)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipientAddressTxt.text = recipientAddress
        selectedMosaicName.text = selectedMosaic.mosaicId.name

        nextSetAmountBtn.setOnClickListener {
            val string = sendAmountInput.text.toString()
            val amount = if (string.isNotEmpty()) string.toDouble() else {
                Toast.makeText(context, getString(R.string.enter_amount_to_send), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            listener?.onAmountSet(amount)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAmountSetListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnAmountSetListener")
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnAmountSetListener {
        fun onAmountSet(amount: Double)
    }

    companion object {
        @JvmStatic
        fun newInstance(recipientAddress: String, selectedMosaic: Mosaic) =
                SendAmountFragment().apply {
                    arguments = Bundle().apply {
                        putString(SendHomeFragment.RECIPIENT_ADDRESS, recipientAddress)
                        putParcelable(SendHomeFragment.SELECTED_MOSAIC, selectedMosaic)
                    }
                }
    }
}
