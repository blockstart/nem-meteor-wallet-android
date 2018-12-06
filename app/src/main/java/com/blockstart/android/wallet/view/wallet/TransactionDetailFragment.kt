package com.blockstart.android.wallet.view.wallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Transaction
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import android.content.Intent
import android.net.Uri


class TransactionDetailFragment : Fragment() {

    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            transaction = it.getParcelable(TRANSACTION_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAmount.text = transaction.amount.toString()
        transactionTicker.text = transaction.mosaicId
        transactionBalanceUsd.text = "$42.09"
        transactionRecipient.text = getString(R.string.transaction_recipient, transaction.recipientAddress)
        transactionDate.text = "June 4, 2019"
        transactionMessage.text = getString(R.string.transaction_message, transaction.message)
        transactionId.text = transaction.privateKey

        viewOnExplorerBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://getcache.io/tx-lookup"))
            startActivity(browserIntent)
        }
    }

    companion object {
        const val TRANSACTION_KEY = "Selected Transaction"

        @JvmStatic
        fun newInstance(transaction: Transaction) = TransactionDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TRANSACTION_KEY, transaction)
            }
        }
    }
}
