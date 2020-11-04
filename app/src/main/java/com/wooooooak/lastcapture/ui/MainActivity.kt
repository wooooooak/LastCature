package com.wooooooak.lastcapture.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import wooooooak.com.library.CoroutinesPermissionManager
import wooooooak.com.library.PermissionResult

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initMobileAds()
        initNavController()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(
            this,
            R.id.navigation_host_fragment
        )
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun initMobileAds() {
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun checkPermission() {
        lifecycleScope.launch {
            val result = CoroutinesPermissionManager.requestPermission(this@MainActivity) {
                permissionList = listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Rationale {
                    title = "[설정] > [권한] 에서 권한을 허용하시면 앱을 사용 하실 수 있습니다."
                }
            }
            if (result is PermissionResult.Denied) {
                Snackbar.make(window.decorView.rootView, "권한이 거부되어 종료됩니다.\n[설정] > [권한] 에서 권한을 허용하시면 앱을 사용 하실 수 있습니다.", Snackbar.LENGTH_LONG).show()
                delay(1_000)
                finishAffinity()
            }
        }
    }

}
