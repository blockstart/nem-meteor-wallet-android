package com.blockstart.android.wallet.view.sendTransaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.*
import android.view.*
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.interfaces.OnChangeMosaicInteractionListener
import com.blockstart.android.wallet.model.Mosaic
import kotlinx.android.synthetic.main.fragment_send_home.*
import com.blockstart.android.wallet.viewModel.MosaicsViewModel
import kotlinx.android.synthetic.main.mosaic_selector_include.*

class SendHomeFragment : Fragment() {

    private var mosaicListener: OnChangeMosaicInteractionListener? = null
    private var scannerListener: OnScanPressedListener? = null
    private var currentMosaicIndex = 0
    private lateinit var model : MosaicsViewModel
    var currentMosaicBalace : Mosaic? = null

    private val mosaicIndexObserver = Observer<Int> { value ->
        value?.let {
            currentMosaicIndex = value
        }
    }

    private val mosaicBalanceObserver = Observer<Mosaic> { value ->
        value?.let {
            updateOnChangeMosaic(it)
            currentMosaicBalace = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(MosaicsViewModel::class.java)
            model.getSelectedMosaicIndex().observe(this, mosaicIndexObserver)
            model.getSelectedMosaicBalance().observe(this, mosaicBalanceObserver)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_send_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddressTextListener()
        sendNextBtn.isEnabled = false

        mosaicSelectorView.setOnClickListener { _ ->
            currentMosaicBalace?.let { mosaic ->
                mosaicListener?.onSelectChangeMosaic(mosaic, currentMosaicIndex)
            }
        }

        sendUsingQrBtn.setOnClickListener {
           scannerListener?.beginQrScanPressed()
        }

        sendNextBtn.setOnClickListener { _ ->
            val intent = Intent(context, SendHomeActivity::class.java)
            intent.putExtra(RECIPIENT_ADDRESS, sendEnterWalletTxt.text.toString())
            model.getSelectedMosaicBalance().value.let {
                intent.putExtra(SELECTED_MOSAIC, it)
                startActivity(intent)
            }
        }
    }

    fun scannerReturnedAddress(address: Editable) {
        sendEnterWalletTxt.text = address
    }

    private fun setupAddressTextListener() {
        val addressTextListener = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { checkValidAddress() }
        }
        sendEnterWalletTxt.addTextChangedListener(addressTextListener)
    }

    fun checkValidAddress() {
        // TODO: Once SDK is in, replace with SDK valid address check.
        val isValid = sendEnterWalletTxt.text.length == FULL_ADDRESS_LENGTH
        val resId = if (isValid) R.drawable.block_blue_btn else R.drawable.block_gray_btn
        sendNextBtn.isEnabled = isValid
        sendNextBtn.setBackgroundResource(resId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnScanPressedListener && context is OnChangeMosaicInteractionListener) {
            mosaicListener = context
            scannerListener = context
        } else {
            throw RuntimeException(context.toString() + getString(R.string.implement_fragment_listener))
        }
    }

    interface OnScanPressedListener {
        fun beginQrScanPressed()
    }

    private fun updateOnChangeMosaic(mosaic: Mosaic) {
        val context = context.let { it } ?: return
        currentMosaicName.text = getString(R.string.mosaic_name_and_amount, mosaic.mosaicId.name, mosaic.quantity.toString())
        val resourceId = context.resources.getIdentifier(mosaic.mosaicId.name.toLowerCase(), "drawable", context.packageName)
        currentMosaicImage?.setImageResource(resourceId)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.create_wallet)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    companion object {
        const val FULL_ADDRESS_LENGTH = 46
        const val RECIPIENT_ADDRESS = "recipientAddress"
        const val SELECTED_MOSAIC = "selectedMosaic"
        @JvmStatic
        fun newInstance() = SendHomeFragment()
    }
}

