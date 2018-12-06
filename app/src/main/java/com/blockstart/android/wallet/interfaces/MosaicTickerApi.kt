package com.blockstart.android.wallet.interfaces

import com.blockstart.android.wallet.model.MosaicMarketValue
import retrofit2.Call
import retrofit2.http.GET

interface MosaicTickerApi {
    @GET("873/")
    fun getXemValue(): Call<MosaicMarketValue>
}