package com.blockstart.android.wallet.view.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.MosaicMarketValue
import com.blockstart.android.wallet.viewModel.MosaicMarketViewModel
import kotlinx.android.synthetic.main.fragment_mosaic_ticker.*

class MosaicTickerFragment : Fragment() {

    private lateinit var model : MosaicMarketViewModel

    private val mosaicTickerObserver = Observer<MosaicMarketValue> { value ->
        value?.let {
           currentMosaicPriceTxt.text = it.data.quotes.stats.price.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(MosaicMarketViewModel::class.java)
            model.getCurrentValue().observe(this, mosaicTickerObserver)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mosaic_ticker, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MosaicTickerFragment()
    }
}
