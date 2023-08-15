package io.astronout.core.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.astronout.core.R

fun ImageView.loadImage(url: String) =
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder))
        .into(this)

fun ImageView.loadImageRound(url: String, placeholder: CircularProgressDrawable) =
    Glide.with(this).load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)

fun ImageView.loadImageRounded(drawable: Drawable) =
    Glide.with(this).load(drawable)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder))
        .transform(
            CenterCrop(),
            RoundedCorners(resources.getDimensionPixelOffset(R.dimen.dimen_20))
        )
        .into(this)

fun ImageView.loadImageRounded(url: String) =
    Glide.with(this).load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder))
        .transform(
            CenterCrop(),
            RoundedCorners(resources.getDimensionPixelOffset(R.dimen.dimen_20))
        )
        .into(this)

fun ImageView.loadImageRounded(uri: Uri) =
    Glide.with(this).load(uri)
        .transform(
            CenterCrop(),
            RoundedCorners(resources.getDimensionPixelOffset(R.dimen.dimen_20))
        )
        .into(this)
