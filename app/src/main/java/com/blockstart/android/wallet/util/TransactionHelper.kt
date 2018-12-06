package com.blockstart.android.wallet.util

import android.content.Context
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.ApiManager

class TransactionHelper(val context: Context) {

    fun sentOrReceived(recipient: String) : String {
        val userAddress = ApiManager.account?.address
        return if (userAddress == recipient) context.getString(R.string.received) else context.getString(R.string.sent)
    }

    companion object {
        @JvmStatic
        fun getSentOrReceivedImgName(sendOrReceive: String) : String {
            return when (sendOrReceive) {
                "Sent" -> "sent_icon_red"
                "Received" -> "received_icon_green"
                else -> ""
            }
        }
    }
}
