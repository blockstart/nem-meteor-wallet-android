package com.blockstart.android.wallet.view.sendTransaction

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import kotlinx.android.synthetic.main.fragment_send_confirm.*

class SendConfirmFragment : Fragment() {

    private lateinit var recipientAddress: String
    private lateinit var selectedMosaic: Mosaic
    private var mosaicAmountToSend = 0.0
    private var listener: OnConfirmSendListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipientAddress = it.getString(SendHomeFragment.RECIPIENT_ADDRESS)
            selectedMosaic = it.getParcelable(SendHomeFragment.SELECTED_MOSAIC)
            mosaicAmountToSend = it.getDouble(AMOUNT_KEY)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeaderText()
        sendConfirmAmountBtn.setOnClickListener {
            listener?.onConfirmSendClicked()
        }
    }

    private fun setupHeaderText() {
        val html = (getString(R.string.you_are_sending_to, mosaicAmountToSend.toString(), selectedMosaic.mosaicId.name))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            youAreSendingTxt.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            youAreSendingTxt.text = Html.fromHtml(html)
        }
        recipientAddressTxt.text = recipientAddress
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnConfirmSendListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnConfirmSendListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnConfirmSendListener {
        fun onConfirmSendClicked()
    }

    companion object {
        const val AMOUNT_KEY = "sendAmount"
        @JvmStatic
        fun newInstance(recipientAddress: String, selectedMosaic: Mosaic, sendAmount: Double) =
                SendConfirmFragment().apply {
                    arguments = Bundle().apply {
                        putString(SendHomeFragment.RECIPIENT_ADDRESS, recipientAddress)
                        putParcelable(SendHomeFragment.SELECTED_MOSAIC, selectedMosaic)
                        putDouble(AMOUNT_KEY, sendAmount)
                    }
                }
    }
}
