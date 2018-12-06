package com.blockstart.android.wallet.viewModel

import android.arch.lifecycle.ViewModel
import com.blockstart.android.wallet.repository.MosaicMarketRepo

class MosaicMarketViewModel : ViewModel() {
    private val mosaicRepo = MosaicMarketRepo()
    private val currentValue = mosaicRepo.getCurrentMosaicValue()

    fun getCurrentValue() = currentValue
}