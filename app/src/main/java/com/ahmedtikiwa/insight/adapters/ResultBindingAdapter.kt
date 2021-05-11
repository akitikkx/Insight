package com.ahmedtikiwa.insight.adapters

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Show or hide a view based on whether the string value is null or not
 */
@BindingAdapter("showHideStringContent")
fun showHideStringContent(view: View, content: String?) {
    view.visibility = if (!content.isNullOrEmpty()) View.VISIBLE else View.GONE
}