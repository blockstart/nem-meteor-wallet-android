package com.blockstart.android.wallet.viewModel

import android.arch.lifecycle.ViewModel

class CreateWalletViewModel : ViewModel() {
    var walletName: String? = null
    var password: String? = null
    var backupEmail: String? = null
    var privateKey: String? = null
}