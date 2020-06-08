/*
 * Created by Lee Oh Hyoung on 2020/06/08 .. 
 */
package kr.pnu.ga2019.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kr.pnu.ga2019.databinding.DialogUserPreferenceBinding
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.util.setOnProgressChanged

private typealias PreferenceClickListener = (Preference) -> Unit

class PreferenceSettingDialog(
    context: Context,
    private val clickListener: PreferenceClickListener = {}
) : Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen) {

    private lateinit var binding: DialogUserPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setWindowAttr()
        setContentView(binding.root)
        setPreferenceTextChanged()
        setListener()
    }

    private fun setBinding() {
        binding = DialogUserPreferenceBinding.inflate(layoutInflater)
    }

    private fun setListener() {
        binding.enterButton.setOnClickListener {
            clickListener.invoke(getUserPreference())
            dismiss()
        }
    }

    private fun setWindowAttr() {
        setCancelable(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setPreferenceTextChanged() {
        binding.seekBarAge.setOnProgressChanged(binding.textAge)
        binding.seekBarAncient.setOnProgressChanged(binding.textAncient)
        binding.seekBarMedieval.setOnProgressChanged(binding.textMedieval)
        binding.seekBarModern.setOnProgressChanged(binding.textModern)
        binding.seekBarDonation.setOnProgressChanged(binding.textDonation)
        binding.seekBarPainting.setOnProgressChanged(binding.textPainting)
        binding.seekBarWorld.setOnProgressChanged(binding.textWorld)
        binding.seekBarCraft.setOnProgressChanged(binding.textCraft)
    }

    private fun getUserPreference(): Preference =
        Preference(
            age = binding.seekBarAge.progress,
            ancient = binding.seekBarAncient.progress,
            medieval = binding.seekBarMedieval.progress,
            modern = binding.seekBarModern.progress,
            donation = binding.seekBarDonation.progress,
            painting = binding.seekBarPainting.progress,
            world = binding.seekBarWorld.progress,
            craft = binding.seekBarCraft.progress
        )
}
