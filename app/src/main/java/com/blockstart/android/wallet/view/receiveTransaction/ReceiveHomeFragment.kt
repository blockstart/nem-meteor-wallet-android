package com.blockstart.android.wallet.view.receiveTransaction

import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.ApiManager
import com.blockstart.android.wallet.util.QrEncoder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.fragment_receive_home.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.blockstart.android.wallet.interfaces.OnChangeMosaicInteractionListener
import com.blockstart.android.wallet.model.Mosaic
import android.view.ViewTreeObserver
import com.blockstart.android.wallet.viewModel.MosaicsViewModel
import kotlinx.android.synthetic.main.mosaic_selector_include.*

class ReceiveHomeFragment : Fragment() {

    private var listener: OnChangeMosaicInteractionListener? = null
    private var currentMosaicIndex = 0
    private var currentMosaicBalace : Mosaic? = null
    private lateinit var model : MosaicsViewModel

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
        return inflater.inflate(R.layout.fragment_receive_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSizeAndSetQrCode()

        copyAddressBtn.setOnClickListener {
            ApiManager.account?.let {
                copyToClipboard(it.address)
                showCopied()
            }
        }

        mosaicSelectorView.setOnClickListener {
            currentMosaicBalace?.let {
                listener?.onSelectChangeMosaic(it, currentMosaicIndex)
            }
        }
    }

    private fun showCopied() {
        copyAddressBtn.animate()
                .alpha(0f)
                .setDuration(200)
                .setStartDelay(0)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        animation.removeAllListeners()
                        hideCopied()
                    }
                })
    }

    fun hideCopied() {
        copyAddressBtn.animate().alpha(1f).setStartDelay(800).setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        animation.removeAllListeners()
                    }
                })
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.create_wallet)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnChangeMosaicInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + getString(R.string.implement_fragment_listener))
        }
    }

    private fun updateOnChangeMosaic(mosaic: Mosaic) {
        val context = context.let { it } ?: return
        currentMosaicName.text = mosaic.mosaicId.name
        mosaicBalanceInReceive.text = mosaic.quantity.toString()
        val resourceId = context.resources.getIdentifier(mosaic.mosaicId.name.toLowerCase(), "drawable", context.packageName)
        currentMosaicImage?.setImageResource(resourceId)
    }

    fun generateAndShowQrCode(width: Int, height: Int) {
        val address = ApiManager.account?.address
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter
                    .encode(address, BarcodeFormat.QR_CODE, width, height)
            val barcodeEncoder = QrEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrImageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun copyToClipboard(clip: String) {
        val clipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val copyClip = ClipData.newPlainText(CLIP_LABEL, clip)
        clipboard.primaryClip = copyClip
    }

    private fun getSizeAndSetQrCode() {
        val vto = qrImageView.viewTreeObserver
        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                qrImageView.viewTreeObserver.removeOnPreDrawListener(this)
                generateAndShowQrCode(qrImageView.measuredWidth, qrImageView.measuredHeight)
                return true
            }
        })
    }

    companion object {
        const val CLIP_LABEL = "address"
        @JvmStatic
        fun newInstance() = ReceiveHomeFragment()
    }
}
