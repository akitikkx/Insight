package com.ahmedtikiwa.insight.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("showHideStringContent")
fun showHideStringContent(view: View, content: String?) {
    view.visibility = if (!content.isNullOrEmpty()) View.VISIBLE else View.GONE
}