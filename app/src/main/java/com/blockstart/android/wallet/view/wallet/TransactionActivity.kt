package com.blockstart.android.wallet.view.wallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import com.blockstart.android.wallet.model.Transaction
import com.blockstart.android.wallet.util.TransactionHelper

class TransactionActivity : AppCompatActivity(), TransactionsFragment.OnSelectTransactionInteractionListener {

    private lateinit var mosaicBalance : Mosaic
    private lateinit var transactions: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        mosaicBalance = intent.getParcelableExtra(WalletHomeFragment.SELECTED_MOSAIC_KEY)
        transactions = intent.getParcelableArrayListExtra(WalletHomeFragment.SELECTED_MOSAIC_TRANSACTIONS)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, TransactionsFragment.newInstance(mosaicBalance, transactions))
                .commit()
    }

    override fun onSelectTransaction(transaction: Transaction) {
        val txHelper = TransactionHelper(this)
        title = txHelper.sentOrReceived(transaction.recipientAddress)
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.container, TransactionDetailFragment.newInstance(transaction))
                .addToBackStack(null)
                .commit()
    }
}
