package com.blockstart.android.wallet.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.view.WindowManager
import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.interfaces.OnChangeMosaicInteractionListener
import com.blockstart.android.wallet.model.Mosaic
import com.blockstart.android.wallet.view.sendTransaction.SendHomeFragment
import com.blockstart.android.wallet.view.createWallet.CreateWalletBaseActivity
import com.blockstart.android.wallet.view.createWallet.CreateWalletBottomSheet
import com.blockstart.android.wallet.view.createWallet.ImportWalletBottomSheet
import com.blockstart.android.wallet.view.importWallet.ImportWalletPrivateKeyActivity
import com.blockstart.android.wallet.view.importWallet.ImportWalletQrBaseActivity
import com.blockstart.android.wallet.view.mosaicSelector.MosaicSelectorFragment
import com.blockstart.android.wallet.view.receiveTransaction.ReceiveHomeFragment
import com.blockstart.android.wallet.view.wallet.WalletHomeFragment
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity(), CreateWalletBottomSheet.OnCreateWalletSheetListener, OnChangeMosaicInteractionListener, SendHomeFragment.OnScanPressedListener, ImportWalletBottomSheet.OnImportWalletSheetListener, QrScannerFragment.OnScanListener {

    companion object {
        const val TAG = "Main Activity"
        const val SHIFT_MODE = "mShiftingMode"
        const val NO_FIELD = "Unable to get shift mode field"
        const val NO_FIELD_CHANGE = "Unable to change value of shift mode"
        const val CREATE_WALLET_DIALOG_TAG = "wallet dialog"
        const val IMPORT_WALLET_DIALOG_TAG = "import dialog"
        const val MOSAIC_SELECTOR_FRAG_TAG = "MosaicSelector"
        const val WALLET_TAG = "wallet"
        const val SEND_TAG = "send"
        const val RECEIVE_TAG = "receive"
        const val SETTINGS_TAG = "settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.disableShiftMode()
        setupWalletScreen()
    }

    override fun onResume() {
        super.onResume()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun setupWalletScreen() {
        openFragment(WalletHomeFragment.newInstance(), WALLET_TAG)
        supportActionBar?.title = getString(R.string.title_my_crypto)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (supportFragmentManager.findFragmentByTag(MOSAIC_SELECTOR_FRAG_TAG) != null) {
            supportFragmentManager.popBackStack()
        }

        when (item.itemId) {
            R.id.navigation_wallet -> {
                setupWalletScreen()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_send -> {
                openFragment(SendHomeFragment.newInstance(), SEND_TAG)
                supportActionBar?.title = getString(R.string.title_send)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_receive -> {
                openFragment(ReceiveHomeFragment.newInstance(), RECEIVE_TAG)
                supportActionBar?.title = getString(R.string.title_receive)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                openFragment(SettingsHomeFragment.newInstance(), SETTINGS_TAG)
                supportActionBar?.title = getString(R.string.title_settings)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.create_wallet -> launchCreateWallet()
        }
        return true
    }

    private fun launchCreateWallet() {
        val dialog = CreateWalletBottomSheet()
        dialog.show(supportFragmentManager, CREATE_WALLET_DIALOG_TAG)
    }

    override fun importWalletSelected() {
        val dialog = ImportWalletBottomSheet()
        dialog.show(supportFragmentManager, IMPORT_WALLET_DIALOG_TAG)
    }

    override fun newWalletSelected() {
        startActivity(intentFor<CreateWalletBaseActivity>())
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    override fun importWithQrCode() {
        startActivity(intentFor<ImportWalletQrBaseActivity>())
    }

    override fun importWithPrivateKey() {
        startActivity(intentFor<ImportWalletPrivateKeyActivity>())
    }

    override fun onSelectChangeMosaic(mosaic: Mosaic, currentIndex: Int) {
        val mosaicSelectorFrag = supportFragmentManager.findFragmentByTag(MOSAIC_SELECTOR_FRAG_TAG)
        if (mosaicSelectorFrag == null) {
            openMosaicSelectorFragment(MosaicSelectorFragment.newInstance(mosaic, currentIndex))
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun beginQrScanPressed() {
        startScannerFragmentWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun startScannerFragment() {
        supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .add(R.id.mainActivity, QrScannerFragment(), "Scanner")
                .commit()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        toast(getString(R.string.permission_not_granted))
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog(R.string.camera_permission_request, request)
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton(getString(R.string.allow)) { _, _ -> request.proceed() }
                .setNegativeButton(getString(R.string.deny)) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }

    override fun onScanComplete(result: String) {
        val sendHome = supportFragmentManager.findFragmentByTag(SEND_TAG) as SendHomeFragment
        sendHome.scannerReturnedAddress(SpannableStringBuilder(result))
        supportFragmentManager.popBackStack()
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.container, fragment, tag)
                .commit()
    }

    private fun openMosaicSelectorFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim, R.anim.constant_anim)
                .addToBackStack(null)
                .add(R.id.mosaicSelectorContainer, fragment, MOSAIC_SELECTOR_FRAG_TAG)
                .commit()
    }

    @SuppressLint("RestrictedApi")
    private fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView::class.java.getDeclaredField(SHIFT_MODE)
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, NO_FIELD, e)
        } catch (e: IllegalStateException) {
            Log.e(TAG, NO_FIELD_CHANGE, e)
        }
    }
}
