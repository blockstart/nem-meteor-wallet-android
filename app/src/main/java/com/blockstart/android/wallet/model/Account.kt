package com.blockstart.android.wallet.model

data class Account(val address: String,
                   val mosaics: ArrayList<Mosaic>?,
                   val wallet: Wallet)
