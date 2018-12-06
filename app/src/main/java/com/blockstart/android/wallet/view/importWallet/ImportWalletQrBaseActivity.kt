package com.blockstart.android.wallet.view.importWallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.view.QrScannerFragment
import com.blockstart.android.wallet.view.createWallet.AllSetFragment
import com.blockstart.android.wallet.view.createWallet.WalletNamePassFragment

class ImportWalletQrBaseActivity : AppCompatActivity() , QrScannerFragment.OnScanListener, WalletNamePassFragment.OnWalletNameAndPassListener   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_wallet_qr_base)
        setScanner()
    }

    override fun onScanComplete(result: String) {
        openFragment(WalletNamePassFragment.newInstance())
    }

    private fun setScanner() {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .add(R.id.import_qr_container, QrScannerFragment(), "Scanner")
                .commit()
    }

    override fun onNewWalletNameAndPassEntered() {
        showAllSetFragment()
    }

    private fun showAllSetFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.
                        fade_out)
                .replace(R.id.import_qr_container, AllSetFragment.newInstance())
                .commit()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.import_qr_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
