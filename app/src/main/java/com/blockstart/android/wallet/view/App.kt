package com.blockstart.android.wallet.view

import android.app.Application
import com.blockstart.android.wallet.NIS
import com.blockstart.android.wallet.model.ApiManager

class App : Application() {

    var _startedNodeAlready = false


    override fun onCreate() {
        super.onCreate()
        if (!_startedNodeAlready) {
            _startedNodeAlready = true
            NIS(applicationContext)
        }

        ApiManager.getTransactionList(assets.open("transactionHistory.json"))
        ApiManager.getAccountInfo(assets.open("account.json"))
    }
}