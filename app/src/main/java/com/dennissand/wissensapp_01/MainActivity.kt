package com.dennissand.wissensapp_01

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dennissand.wissensapp_01.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startDownload()

        /** Statusbar wird hier auf transparent gestellt */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        AppBarConfiguration(
            setOf(
                R.id.navigation_home_home,
                R.id.nested_box,
                R.id.nested_card,
                R.id.nested_learn
            )

        )

        /** Navigation über Navbottemitems - wenn ein Navigationsfad verlassen wird,
         * daß man wieder auf dem Startfragment startet */

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->

            NavigationUI.onNavDestinationSelected(item, navController)

            val graph = navController.graph.findNode(item.itemId) as? NavGraph?

            graph?.startDestinationId?.let {
                navController.popBackStack(it, false)
            }

            return@setOnItemSelectedListener true
        }
    }
}
