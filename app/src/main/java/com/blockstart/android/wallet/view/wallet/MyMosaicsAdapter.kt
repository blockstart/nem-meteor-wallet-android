package com.blockstart.android.wallet.view.wallet

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import java.util.*

class MyMosaicsAdapter(val context: Context, private val mosaics: ArrayList<Mosaic>, private val itemClick: (Mosaic) -> Unit) : RecyclerView.Adapter<MyMosaicsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMosaic(context, mosaics[position])
    }

    override fun getItemCount(): Int {
        return mosaics.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mosaic_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    inner class ViewHolder(itemView: View?, val itemClick: (Mosaic) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mosaicImage = itemView?.findViewById<ImageView>(R.id.mosaic_image)
        private val mosaicName = itemView?.findViewById<TextView>(R.id.mosaic_name)
        private val mosaicAmount = itemView?.findViewById<TextView>(R.id.mosaic_amount)

        fun bindMosaic(context: Context, mosaic: Mosaic) {
            val resourceId = context.resources.getIdentifier(mosaic.mosaicId.name.toLowerCase(), "drawable", context.packageName)
            mosaicImage?.setImageResource(resourceId)
            mosaicName?.text = mosaic.mosaicId.name
            mosaicAmount?.text = context.getString(R.string.homescreen_mosaic_name_and_ticker, mosaic.quantity.toString(), mosaic.mosaicId.name)
            itemView.setOnClickListener { itemClick(mosaic) }
        }
    }
}