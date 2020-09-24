package com.bilingoal.locationtracker.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.ui.base.Host
import dagger.android.support.DaggerAppCompatActivity

class HostActivity : DaggerAppCompatActivity(), Host {
    private  lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.mainFragment,
            R.id.loginFragment
            ))

        setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun test() {
        Log.e("Activity", "Test")
    }
}