/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kr.pnu.ga2019.databinding.DialogExitBinding

class ExitDialog(
    context: Context,
    private val onExitClick: () -> Unit
) : Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen) {

    private lateinit var binding: DialogExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setWindowAttr()
        setListener()
        setContentView(binding.root)
    }

    private fun setBinding() {
        binding = DialogExitBinding.inflate(layoutInflater)
    }

    private fun setWindowAttr() {
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setListener() {
        binding.exitButton.setOnClickListener {
            onExitClick.invoke()
            dismiss()
        }
    }
}
