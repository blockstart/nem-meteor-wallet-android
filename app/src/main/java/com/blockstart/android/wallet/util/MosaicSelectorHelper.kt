package com.blockstart.android.wallet.util

import com.blockstart.android.wallet.model.ApiManager
import com.blockstart.android.wallet.model.Mosaic

class MosaicSelectorHelper {
    companion object {
        @JvmStatic
        fun returnMosaics() : ArrayList<Mosaic>? {
             ApiManager.account?.mosaics?.let {
                 return it
             }
            return null
        }
    }
}