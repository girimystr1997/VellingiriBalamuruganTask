package com.vb.vellingiribalamurugantask

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import java.text.NumberFormat
import java.util.Locale


fun Double.formatAmount(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(this)
}


fun ViewGroup.showHideAnimation(duration: Long = 1000,callback:(bool:Boolean)->Unit) {
    if (isVisible) {
        animate()
            .alpha(0f)
            .setDuration(duration / 2)
            .withEndAction {
                visibility = View.GONE
                translationY = 0f
            }
    } else {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
    }
    callback(isVisible)
}