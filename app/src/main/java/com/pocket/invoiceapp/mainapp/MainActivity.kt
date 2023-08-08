package com.pocket.invoiceapp.mainapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.authenticator.AuthViewModel
import com.pocket.invoiceapp.base.BaseActivity
import com.pocket.invoiceapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val LOG_TAG = MainActivity::class.java.name

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpNavigation()


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    private fun setUpNavigation() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        binding.navHostFragmentContentMain.visibility = View.VISIBLE

        binding.toolbar.setNavigationOnClickListener(navigationClickListener)
    }

    private val navigationClickListener = View.OnClickListener {
        when (navController.currentDestination?.id) {
            R.id.loginFragment, R.id.homeFragment -> {
                if (onBackPressedDispatcher.hasEnabledCallbacks())
                    onBackPressedDispatcher.onBackPressed()
                else
                    navController.navigateUp()
            }

            else -> navController.navigateUp()
        }
    }

    override fun registerObservers() {

    }
}

