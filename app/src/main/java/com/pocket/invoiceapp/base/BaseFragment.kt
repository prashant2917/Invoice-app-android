package com.pocket.invoiceapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pocket.invoiceapp.R
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment: Fragment() {
   open lateinit var navController: NavController

   open abstract fun registerObservers()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val navHostFragment = activity?.supportFragmentManager
         ?.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
      navController = navHostFragment.navController
   }


   fun showToast(message : String) {
      Toast.makeText(activity?.baseContext, message, Toast.LENGTH_LONG).show()
   }

   fun hideSoftKeyboard(view: View) {

      // now assign the system
      // service to InputMethodManager
      val manager = ContextCompat.getSystemService(
         view.context,
         Context.INPUT_METHOD_SERVICE::class.java
      ) as InputMethodManager?
      manager?.hideSoftInputFromWindow(
         view.windowToken, 0
      )
   }

}