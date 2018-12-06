package com.blockstart.android.wallet.view

import android.content.Context
import android.os.Bundle
import com.blockstart.android.wallet.R
import android.graphics.PointF
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.lottie.LottieDrawable
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import kotlinx.android.synthetic.main.fragment_qr_scanner.*

class QrScannerFragment : Fragment() , QRCodeReaderView.OnQRCodeReadListener {

    private var hasReadQrCode = false
    private var listener: OnScanListener? = null

    interface OnScanListener {
        fun onScanComplete(result: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qr_scanner, container, false)
    }

    private fun setScanner() {
        scannerView.setQRDecodingEnabled(true)
        scannerView.setAutofocusInterval(2000L)
        scannerView.setBackCamera()
        scannerView.setOnQRCodeReadListener(this)
        scannerView.setOnClickListener { scannerView.forceAutoFocus() }
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        qrScanAnimation.setAnimation(R.raw.scan)
        qrScanAnimation.scaleType = ImageView.ScaleType.CENTER_CROP
        qrScanAnimation.repeatCount = LottieDrawable.INFINITE
        qrScanAnimation.playAnimation()
        setScanner()
        hasReadQrCode = false
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnScanListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + getString(R.string.implement_fragment_listener))
        }
    }

    override fun onQRCodeRead(text: String, pointFS: Array<PointF>) {
        if (hasReadQrCode) return
        hasReadQrCode = true
        listener?.onScanComplete(text)
    }
}