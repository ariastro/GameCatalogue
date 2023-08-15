package io.astronout.core.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import io.astronout.core.R

/**
 * @param callback -> the callback to execute on back pressed
 * @param predicate -> condition that needs to be met in order to execute back press
 *
 * If predicate not met, back press is propagated to activity
 */
fun Fragment.onFragmentBackCallback(
    callback: () -> Unit,
    predicate: () -> Boolean = { true }
) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when {
                predicate() -> callback()
                else -> {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
    })
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.changeStatusBarColor(@ColorRes color: Int?) {
    val window = requireActivity().window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColorCompat(color ?: R.color.white)
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun FragmentActivity.initGraph(
    @IdRes hostId: Int,
    @NavigationRes navId: Int,
    bundle: Bundle? = null,
    block: ((NavGraph) -> Unit)? = null
) {
    val navController = supportFragmentManager.findFragmentById(hostId)?.findNavController()
    val graph = navController?.navInflater?.inflate(navId)
    if (graph != null) {
        block?.invoke(graph)
        navController.setGraph(graph, bundle)
    }
}