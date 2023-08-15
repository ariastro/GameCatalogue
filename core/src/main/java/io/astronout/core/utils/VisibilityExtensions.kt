package io.astronout.core.utils

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}






