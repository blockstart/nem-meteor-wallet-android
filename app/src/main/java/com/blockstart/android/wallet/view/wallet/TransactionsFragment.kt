package com.blockstart.android.wallet.view.wallet

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import com.blockstart.android.wallet.model.Transaction
import kotlinx.android.synthetic.main.fragment_transactions.*

class TransactionsFragment : Fragment() {

    private lateinit var mosaicBalance: Mosaic
    private lateinit var transactions: ArrayList<Transaction>
    private var listener: OnSelectTransactionInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mosaicBalance = it.getParcelable(WalletHomeFragment.SELECTED_MOSAIC_KEY)
            transactions = it.getParcelableArrayList(WalletHomeFragment.SELECTED_MOSAIC_TRANSACTIONS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAmount.text = mosaicBalance.quantity.toString()
        transactionTicker.text = mosaicBalance.mosaicId.name
        mosaicBalanceUsd.text = "$12,000 (USD)"

        val context = context.let { it } ?: return
        val adapter = TransactionsAdapter(context, transactions) { transaction ->
            listener?.onSelectTransaction(transaction)
        }

        transactionRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        transactionRecyclerView.layoutManager = layoutManager
        transactionRecyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.transactions_title)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSelectTransactionInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnSelectTransactionInteractionListener {
        fun onSelectTransaction(transaction: Transaction)
    }

    companion object {
        @JvmStatic
        fun newInstance(mosaicBalance: Mosaic, transactions: ArrayList<Transaction>) = TransactionsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(WalletHomeFragment.SELECTED_MOSAIC_KEY, mosaicBalance)
                putParcelableArrayList(WalletHomeFragment.SELECTED_MOSAIC_TRANSACTIONS, transactions)
            }
        }
    }
}
