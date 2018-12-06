package com.blockstart.android.wallet.view.createWallet

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannedString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blockstart.android.wallet.R
import com.blockstart.android.wallet.util.SOURCE_SANS_PRO_BOLD
import com.blockstart.android.wallet.util.SOURCE_SANS_PRO_LIGHT
import com.blockstart.android.wallet.util.StringAnnotationProcessor
import kotlinx.android.synthetic.main.fragment_create_wallet_intro.*


class CreateWalletIntroFragment : Fragment() {

    private var listener: OnCreateNewWalletListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_wallet_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context ?: return
        val bold = ResourcesCompat.getFont(context, R.font.source_sans_pro_bold) ?: return
        val light = ResourcesCompat.getFont(context, R.font.source_sans_pro_light) ?: return
        val titleText = getText(R.string.create_new_wallet_intro) as SpannedString
        createWalletIntroTxt.text = StringAnnotationProcessor.styleNewFonts(titleText, arrayListOf(SOURCE_SANS_PRO_BOLD, SOURCE_SANS_PRO_LIGHT), arrayListOf(bold, light))
        createNewWalletBtn.setOnClickListener {
            listener?.onCreateNewWalletPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCreateNewWalletListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnCreateNewWalletListener {
        fun onCreateNewWalletPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateWalletIntroFragment()
    }
}
