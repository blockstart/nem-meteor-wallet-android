package com.blockstart.android.wallet.view.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.ApiManager
import com.blockstart.android.wallet.model.Mosaic
import com.blockstart.android.wallet.model.Transaction
import com.blockstart.android.wallet.viewModel.MosaicsViewModel
import kotlinx.android.synthetic.main.fragment_wallet_home.*
import java.util.ArrayList

class WalletHomeFragment : Fragment() {

    private lateinit var model : MosaicsViewModel
    private lateinit var adapter: MyMosaicsAdapter

    private val mosaicBalancesObserver = Observer<ArrayList<Mosaic>> { value ->
        value?.let {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(MosaicsViewModel::class.java)
            model.mosaicBalances.observe(this, mosaicBalancesObserver)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context.let { it } ?: return
        adapter = MyMosaicsAdapter(context, model.mosaicBalances.value as ArrayList<Mosaic>){ mosaicBalance ->
            val intent = Intent(context, TransactionActivity::class.java)
            intent.putExtra(SELECTED_MOSAIC_KEY, mosaicBalance)
            intent.putExtra(SELECTED_MOSAIC_TRANSACTIONS, filterMosaic(mosaicBalance.mosaicId.name))
            startActivity(intent)
        }

        childFragmentManager.beginTransaction()
                .replace(R.id.currentCurrencyFragContainer, MosaicTickerFragment.newInstance())
                .commit()

        my_currencies_recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        my_currencies_recycler.layoutManager = layoutManager
        my_currencies_recycler.setHasFixedSize(true)
    }

    private fun filterMosaic(mosaic: String) : ArrayList<Transaction> {
        return ApiManager.transactions.filter { tx ->
            tx.mosaicId.endsWith(mosaic.toLowerCase())
        } as ArrayList<Transaction>
    }

    companion object {
        const val SELECTED_MOSAIC_KEY = "Selected Mosaic Balance"
        const val SELECTED_MOSAIC_TRANSACTIONS = "Selected Mosaic Transactions"

        @JvmStatic
        fun newInstance() = WalletHomeFragment()
    }
}
