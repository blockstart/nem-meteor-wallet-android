package com.blockstart.android.wallet.interfaces

import com.blockstart.android.wallet.model.Mosaic

interface OnChangeMosaicInteractionListener {
    fun onSelectChangeMosaic(mosaic: Mosaic, currentIndex: Int)
}