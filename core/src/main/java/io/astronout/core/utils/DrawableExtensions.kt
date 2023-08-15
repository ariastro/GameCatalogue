package io.astronout.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableId)
}

fun Fragment.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), drawableId)
}