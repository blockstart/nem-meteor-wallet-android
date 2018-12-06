package com.blockstart.android.wallet.view.mosaicSelector

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import kotlinx.android.synthetic.main.fragment_mosaic_selector.*
import android.view.animation.AnimationUtils
import com.blockstart.android.wallet.model.ApiManager
import com.blockstart.android.wallet.viewModel.MosaicsViewModel

class MosaicSelectorFragment : Fragment(), LifecycleObserver {

    private lateinit var currentlySelectedMosaic: Mosaic
    private lateinit var adapter : MosaicSelectorAdapter
    private lateinit var model : MosaicsViewModel
    private var currentIndex = 0

    private val mosaicChangeObserver = Observer<Int> { value ->
        value?.let {
            currentIndex = value
            adapter.updateSelectedMosaic(value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentlySelectedMosaic = it.getParcelable(CURRENT_MOSAIC)
        }

        activity?.let {
            model = ViewModelProviders.of(it).get(MosaicsViewModel::class.java)
            model.getSelectedMosaicIndex().observe(this, mosaicChangeObserver)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mosaic_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context.let { it } ?: return
        adapter = MosaicSelectorAdapter(context, currentIndex, ApiManager.account?.mosaics as ArrayList<Mosaic>){ mosaicBalance, index ->
            model.setSelectedMosaic(index)
        }

        mosaicSelectorRecycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        mosaicSelectorRecycler.layoutManager = layoutManager
        mosaicSelectorRecycler.setHasFixedSize(true)

        bg_view.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val enterTop = AnimationUtils.loadAnimation(context, R.anim.enter_top)
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        recycler_holder.startAnimation(enterTop)
        bg_view.startAnimation(fadeIn)
    }

    override fun onStop() {
        super.onStop()
        val exitTop = AnimationUtils.loadAnimation(context, R.anim.exit_top)
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        recycler_holder.startAnimation(exitTop)
        bg_view.startAnimation(fadeOut)
    }

    companion object {
        const val CURRENT_MOSAIC = "Selected Mosaic"
        const val CURRENT_INDEX = "Selected Index"

        @JvmStatic
        fun newInstance(mosaic: Mosaic, currentIndex: Int) = MosaicSelectorFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(CURRENT_MOSAIC, mosaic)
                        putInt(CURRENT_INDEX, currentIndex)
                    }
                }
    }
}
