package io.astronout.core.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.getColorCompat(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Fragment.getColorCompat(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(requireContext(), colorId)
}

fun Fragment.getColorStateList(@ColorRes colorId: Int): ColorStateList {
    return ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorId))
}