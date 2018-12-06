package com.blockstart.android.wallet.repository

import android.arch.lifecycle.LiveData
import com.blockstart.android.wallet.util.MosaicSelectorHelper
import android.arch.lifecycle.MutableLiveData
import com.blockstart.android.wallet.model.Mosaic

class MosaicRepo {
    fun getMosaics() : LiveData<ArrayList<Mosaic>> {
        val mosaics = MutableLiveData<ArrayList<Mosaic>>()
        mosaics.value = MosaicSelectorHelper.returnMosaics()
        return mosaics
    }
}