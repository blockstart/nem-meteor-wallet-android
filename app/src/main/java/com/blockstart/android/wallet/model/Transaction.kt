package com.blockstart.android.wallet.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(val mosaicId: String,
                       val amount: Double,
                       val recipientAddress: String,
                       val privateKey: String,
                       val message: String?) : Parcelable