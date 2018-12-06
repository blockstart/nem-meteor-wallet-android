package com.blockstart.android.wallet.view.mosaicSelector

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import java.util.ArrayList

class MosaicSelectorAdapter (val context: Context, var selectedMosaic: Int, private val mosaics: ArrayList<Mosaic>, private val itemClick: (Mosaic, Int) -> Unit) :  RecyclerView.Adapter<MosaicSelectorAdapter.ViewHolder>() {

    fun updateSelectedMosaic(index: Int) {
        this.selectedMosaic = index
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mosaics.count() != 0) {
            holder.bindMosaic(context, mosaics[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mosaics.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.select_mosaic_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    inner class ViewHolder(itemView: View?, val itemClick: (Mosaic, Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mosaicImage = itemView?.findViewById<ImageView>(R.id.selectMosaicImage)
        private val mosaicName = itemView?.findViewById<TextView>(R.id.selectMosaicName)
        private val currentMosaicCheck = itemView?.findViewById<ImageView>(R.id.selectMosaicSelectedCheck)

        fun bindMosaic(context: Context, mosaic: Mosaic, selectedIndex: Int) {
            val resourceId = context.resources.getIdentifier(mosaic.mosaicId.name.toLowerCase(), "drawable", context.packageName)
            mosaicImage?.setImageResource(resourceId)
            currentMosaicCheck?.visibility = if (selectedIndex == selectedMosaic) View.VISIBLE else View.INVISIBLE
            mosaicName?.text = mosaic.mosaicId.name

            itemView.setOnClickListener { itemClick(mosaic, selectedIndex) }
        }
    }
}