package com.uas.agenda.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.uas.agenda.R
import com.uas.agenda.databinding.ActivityMenuBinding
import com.uas.agenda.databinding.NavHeaderMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)

        // Navigation Drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        navView.setupWithNavController(navController)

        // AppBar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dates, R.id.nav_status, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Bottom Navigation
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        val info: Bundle? = intent.extras

        val headerView = navView.getHeaderView(0)
        val headerBinding = NavHeaderMenuBinding.bind(headerView)

        headerBinding.textViewUser.text = getString(R.string.navDrawerTitle)

        if (info != null) {
            headerBinding.textViewUserEmail.text = info.getString("correo")

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}