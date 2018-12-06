package com.blockstart.android.wallet.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blockstart.android.wallet.model.Mosaic
import com.blockstart.android.wallet.repository.MosaicRepo

class MosaicsViewModel : ViewModel() {
    private val mosaicsRepo = MosaicRepo()
    private val selectedMosaicIndex = MutableLiveData<Int>()
    private val selectedMosaicBalance = MutableLiveData<Mosaic>()
    val mosaicBalances = MutableLiveData<ArrayList<Mosaic>>()

    init {
        mosaicBalances.value = mosaicsRepo.getMosaics().value
    }

    fun setSelectedMosaic(index: Int) {
        selectedMosaicIndex.value = index
        selectedMosaicBalance.value = mosaicBalances.value?.get(index)
    }

    fun getSelectedMosaicIndex() : LiveData<Int>  {
        if (selectedMosaicIndex.value == null) {
            selectedMosaicIndex.value = 0
        }
        return selectedMosaicIndex
    }

    fun getSelectedMosaicBalance() : LiveData<Mosaic> {
        if (selectedMosaicBalance.value == null) {
            mosaicBalances.value?.takeIf { it.count() > 0 }?.apply {
                selectedMosaicBalance.value = this[0]
            }
        }
        return selectedMosaicBalance
    }
}