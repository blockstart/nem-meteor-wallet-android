package com.blockstart.android.wallet.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mosaic(
        val mosaicId: MosaicId,
        val properties: MosaicProperties,
        val levy: MosaicLevy?,
        val quantity: String
): Parcelable

@Parcelize
data class MosaicId(
        val namespaceId: String,
        val name: String
): Parcelable

@Parcelize
data class MosaicProperties(
        val initialSupply: String,
        val supplyMutable: Boolean,
        val transferable: Boolean,
        val divisibility: Int
): Parcelable

@Parcelize
data class MosaicLevy(
        val type: Int,
        val recipient: Address,
        val mosaicId: MosaicId,
        val fee: Int
): Parcelable