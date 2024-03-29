package com.ahmedtikiwa.insight.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmedtikiwa.insight.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

/**
 * Binding adapter for poster sized images using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    try {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.poster_placeholder)
            .error(R.drawable.poster_placeholder)
            .fallback(R.drawable.poster_placeholder)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    } catch (e: Exception) {
        Timber.d(e.message.toString())
    }
}

/**
 * Binding adapter for wide-sized images using Glide
 */
@BindingAdapter("wideImageUrl")
fun setWideImageUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    try {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.backdrop_background)
            .error(R.drawable.backdrop_background)
            .fallback(R.drawable.backdrop_background)
            .into(imageView)
    } catch (e: Exception) {
        Timber.d(e.message.toString())
    }
}