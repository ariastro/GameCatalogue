package io.astronout.gamescatalogue.presentation.splashscreen

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.astronout.core.base.BaseFragment
import io.astronout.core.utils.showToast
import io.astronout.gamescatalogue.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    override fun initObserver() {
        super.initObserver()
        showNotificationPermission()
        lifecycleScope.launch {
            delay(3000)
            navigateToHomeDashboard()
        }
    }

    private fun navigateToHomeDashboard() {
        findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
    }

    private fun showNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showToast("Permission denied!")
        }
    }

}