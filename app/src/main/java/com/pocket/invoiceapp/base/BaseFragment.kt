package com.pocket.invoiceapp.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pocket.invoiceapp.R
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment: Fragment() {
   open lateinit var navController: NavController

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val navHostFragment = activity?.supportFragmentManager
         ?.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
      navController = navHostFragment.navController
   }


}