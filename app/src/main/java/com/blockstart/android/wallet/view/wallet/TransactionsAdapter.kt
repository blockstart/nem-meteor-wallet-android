package com.blockstart.android.wallet.view.wallet

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Transaction
import com.blockstart.android.wallet.util.TransactionHelper
import java.util.ArrayList

class TransactionsAdapter (val context: Context, private val transactions: ArrayList<Transaction>, private val itemClick: (Transaction) -> Unit) :  RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMosaic(context, transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.transaction_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    inner class ViewHolder(itemView: View?, private val itemClick: (Transaction) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val transactionImage = itemView?.findViewById<ImageView>(R.id.transactionImage)
        private val transactionSendReceive = itemView?.findViewById<TextView>(R.id.transactionSendReceiveTxt)
        private val transactionAmount = itemView?.findViewById<TextView>(R.id.transactionAmount)
        private val transactionTimestamp = itemView?.findViewById<TextView>(R.id.transactionTimestamp)

        fun bindMosaic(context: Context, transaction: Transaction) {
            val txHelper = TransactionHelper(context)
            val sendOrReceive = txHelper.sentOrReceived(transaction.recipientAddress)
            val resourceId = context.resources.getIdentifier(TransactionHelper.getSentOrReceivedImgName(sendOrReceive), "drawable", context.packageName)
            transactionImage?.setImageResource(resourceId)
            transactionSendReceive?.text = sendOrReceive
            transactionAmount?.text = transaction.amount.toString()
            transactionTimestamp?.text = "3 hours ago"
            itemView.setOnClickListener { itemClick(transaction) }
        }
    }
}