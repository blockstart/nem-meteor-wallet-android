package com.blockstart.android.wallet.view.sendTransaction

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.model.Mosaic
import android.view.inputmethod.InputMethodManager
import com.blockstart.android.wallet.enums.BottomActionPresenter
import com.blockstart.android.wallet.view.bottomSheets.ConfirmActionBottomSheet
import com.blockstart.android.wallet.view.bottomSheets.ConfirmPasswordSheet

class SendHomeActivity : AppCompatActivity(), SendAmountFragment.OnAmountSetListener, SendConfirmFragment.OnConfirmSendListener, ConfirmActionBottomSheet.ConfirmBottomActionListener {

    private lateinit var selectedMosaic: Mosaic
    private lateinit var recipientAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_home)
        selectedMosaic = intent.getParcelableExtra(SendHomeFragment.SELECTED_MOSAIC)
        recipientAddress = intent.getStringExtra(SendHomeFragment.RECIPIENT_ADDRESS)
        setInitialFragment()
    }

    private fun setInitialFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, SendAmountFragment.newInstance(recipientAddress, selectedMosaic))
                .commit()
        title = getString(R.string.title_send)
    }

    override fun onAmountSet(amount: Double) {
        openFragment(SendConfirmFragment.newInstance(recipientAddress, selectedMosaic, amount))
    }

    override fun onConfirmSendClicked() {
        val dialog = ConfirmPasswordSheet.newInstance(getString(R.string.title_send),R.drawable.block_orange_btn, BottomActionPresenter.CONFIRM_PASS)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim)
                .addToBackStack(null)
                .add(R.id.container, dialog, CONFIRM_PASS_DIALOG_TAG)
                .commit()
    }

    override fun onActionClicked(tag: BottomActionPresenter) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, TransactionSentFragment.newInstance())
                .commit()
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

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(SEND_BACKSTACK)
                .replace(R.id.container, fragment)
                .commit()
    }

    companion object {
        const val CONFIRM_PASS_DIALOG_TAG = "confirmPasswordDialog"
        const val SEND_BACKSTACK = "sendBackstack"
    }
}

