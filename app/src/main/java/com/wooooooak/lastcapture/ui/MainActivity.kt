package com.wooooooak.lastcapture.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.ActivityMainBinding
import com.wooooooak.lastcapture.utilities.PermissionUtil

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionUtil(this).checkExtStoragePermission({

        }, {
            Snackbar.make(window.decorView.rootView, "권한이 거부되어 종료됩니다.", Snackbar.LENGTH_LONG).show()
        })

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        navController = Navigation.findNavController(this,
            R.id.navigation_host_fragment
        )

//        setSupportActionBar(binding.toolbar)
//        setupActionBarWithNavController(navController)

        binding.bottomNavigationView.setupWithNavController(navController)

    }
}
