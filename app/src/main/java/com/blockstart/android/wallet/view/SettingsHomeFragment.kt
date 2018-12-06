package com.blockstart.android.wallet.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.blockstart.android.wallet.R

class SettingsHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_settings_home, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.create_wallet)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsHomeFragment()
    }
}
