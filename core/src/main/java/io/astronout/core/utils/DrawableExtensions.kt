package io.astronout.core.utils

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), drawableId)
}