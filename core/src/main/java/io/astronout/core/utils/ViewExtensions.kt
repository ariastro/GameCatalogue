@file:Suppress("DEPRECATION", "MagicNumber")

package io.astronout.core.utils

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

fun sendIntent(text: String) = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

fun urlIntent(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url))

internal fun CircularProgressDrawable.style() {
    strokeWidth = 8f
    centerRadius = 45f
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(Color.GRAY, BlendMode.SRC_ATOP)
    } else {
        this.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
    }
    start()
}

fun SwipeRefreshLayout.enable() {
    isEnabled = true
    setColorSchemeColors(Color.GRAY)
}

fun View.fadeAnimation(alpha: Float, setUpdateListener: (View) -> Unit) {
    animate()
        .alpha(alpha)
        .setDuration(300L)
        .setUpdateListener {
            setUpdateListener(this)
        }
}

fun View.showFadeAnimation() {
    alpha = 0F
    animate()
        .alpha(1F)
        .setDuration(300L)
        .setUpdateListener {
            visible()
        }
}

fun View.hideFadeAnimation() {
    alpha = 1F
    animate()
        .alpha(0F)
        .setDuration(300L)
        .setUpdateListener {
            gone()
        }
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    val snackBar = Snackbar.make(this, message, duration)
    val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    snackBar.view.layoutParams = params
    snackBar.show()
}