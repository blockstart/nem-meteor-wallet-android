package com.blockstart.android.wallet.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.blockstart.android.wallet.interfaces.MosaicTickerApi
import com.blockstart.android.wallet.model.MosaicMarketValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MosaicMarketRepo {
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/v2/ticker/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(MosaicTickerApi::class.java)

    fun getCurrentMosaicValue() : LiveData<MosaicMarketValue> {
        val liveData = MutableLiveData<MosaicMarketValue>()
        service.getXemValue().enqueue(object : Callback<MosaicMarketValue> {

            override fun onResponse(call: Call<MosaicMarketValue>?, response: Response<MosaicMarketValue>?) {
                if (response != null && response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<MosaicMarketValue>?, t: Throwable?) {
                Log.e("Request Failure", "Failure to retrieve current mosaic value", t)
            }
        })

        return liveData
    }
}