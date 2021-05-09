package com.ahmedtikiwa.insight.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("showHideView")
fun showHideView(view: View, show: Boolean) {
    if (show) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}