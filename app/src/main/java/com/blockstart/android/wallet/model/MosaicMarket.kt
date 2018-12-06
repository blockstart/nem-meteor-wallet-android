package com.blockstart.android.wallet.model

import com.google.gson.annotations.SerializedName

data class MosaicMarketValue(val data: MosaicData)

data class MosaicData(val id: Int,
                      val name: String,
                      val symbol: String,
                      val rank: Int,
                      val quotes: Quotes,
                      @SerializedName("circulating_supply") val circulatingSupply: Double,
                      @SerializedName("total_supply") val totalSupply: Double,
                      @SerializedName("max_supply") val maxSupply: Double)

data class Quotes(@SerializedName("USD") val stats: MosaicUsd)

data class MosaicUsd(val price: Double,
                     @SerializedName("volume_24h") val volume24h: Double,
                     @SerializedName("market_cap") val marketCap: Double,
                     @SerializedName("percent_change_1h") val percentChange1h: Double,
                     @SerializedName("percent_change_24h") val percentChange24h: Double,
                     @SerializedName("percent_change_7d") val percentChange7d: Double)