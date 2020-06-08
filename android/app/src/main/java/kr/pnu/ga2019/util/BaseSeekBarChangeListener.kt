/*
 * Created by Lee Oh Hyoung on 2020/06/08 .. 
 */
package kr.pnu.ga2019.util

import android.widget.SeekBar
import android.widget.TextView

abstract class BaseSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) { /* explicitly empty */ }

    override fun onStartTrackingTouch(seekBar: SeekBar?) { /* explicitly empty */ }

    override fun onStopTrackingTouch(seekBar: SeekBar?) { /* explicitly empty */ }
}

fun SeekBar.setOnProgressChanged(textView: TextView) {
    setOnSeekBarChangeListener(object: BaseSeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            textView.text = progress.toString()
        }
    })
}
