package com.blockstart.android.wallet.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
        val networkType: Int,
        val value: String
): Parcelable