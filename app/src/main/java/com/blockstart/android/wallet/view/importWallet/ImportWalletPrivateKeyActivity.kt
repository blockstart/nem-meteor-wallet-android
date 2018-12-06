package com.blockstart.android.wallet.view.importWallet

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.enums.BottomActionPresenter
import com.blockstart.android.wallet.view.bottomSheets.BackupWalletSheet
import com.blockstart.android.wallet.view.bottomSheets.ConfirmActionBottomSheet
import com.blockstart.android.wallet.view.bottomSheets.ConfirmPasswordSheet
import com.blockstart.android.wallet.view.createWallet.AllSetFragment
import com.blockstart.android.wallet.view.createWallet.BackupEmailSentFragment
import com.blockstart.android.wallet.view.createWallet.BackupWalletFragment
import com.blockstart.android.wallet.view.createWallet.WalletNamePassFragment

class ImportWalletPrivateKeyActivity : AppCompatActivity(), EnterPrivateKeyFragment.OnPrivateKeyEnteredListener, WalletNamePassFragment.OnWalletNameAndPassListener, ConfirmActionBottomSheet.ConfirmBottomActionListener, BackupWalletFragment.OnBackupWalletListener, BackupEmailSentFragment.OnEmailSentConfirmationReceived {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_wallet_private_key)
        supportFragmentManager.beginTransaction()
                .replace(R.id.importFragmentContainer, EnterPrivateKeyFragment.newInstance())
                .commit()
    }

    override fun onPrivateKeyEntered() {
        openFragment(WalletNamePassFragment.newInstance())
    }

    override fun onNewWalletNameAndPassEntered() {
        val confirmDialog = ConfirmPasswordSheet.newInstance(getString(R.string.next), R.drawable.block_green_square_btn, BottomActionPresenter.CONFIRM_PASS)
        openBottomSheet(confirmDialog)
    }

    override fun onDoThisLaterSelected() {
        showAllSetFragment()
    }

    override fun backUpWalletSelected() {
        val confirmDialog = BackupWalletSheet.newInstance(BottomActionPresenter.BACKUP_TAG)
        openBottomSheet(confirmDialog)
    }

    override fun soundsGoodFromBackup() {
        showAllSetFragment()
    }

    private fun showAllSetFragment() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.
                        fade_out)
                .replace(R.id.importFragmentContainer, AllSetFragment.newInstance())
                .commit()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.importFragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
    }

    private fun openBottomSheet(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim)
                .addToBackStack(null)
                .add(R.id.importFragmentContainer, fragment, null)
                .commit()
    }

    override fun onActionClicked(tag: BottomActionPresenter) {
        when (tag) {
            BottomActionPresenter.CONFIRM_PASS -> openFragment(BackupWalletFragment.newInstance())
            BottomActionPresenter.BACKUP_TAG -> openFragment(BackupEmailSentFragment.newInstance())
        }
    }

    override fun onDismissConfirmSheet() {
        supportFragmentManager.popBackStack()
        dismissKeyboard()
    }

    override fun onDismissKeyboard() {
        dismissKeyboard()
    }

    private fun dismissKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
