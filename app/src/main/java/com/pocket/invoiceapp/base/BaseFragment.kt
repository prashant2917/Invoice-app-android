package com.pocket.invoiceapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.intefaces.DialogClickListener
import com.pocket.invoiceapp.utils.Utils


abstract class BaseFragment : Fragment() {
    open lateinit var navController: NavController


    abstract fun observeLiveData()
    abstract fun setUpListeners()

    abstract fun setUpUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
    }


    fun showToast(message: String) {
        val toast = Toast.makeText(activity?.baseContext, message, Toast.LENGTH_LONG)
        toast.show()

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

    fun hideToolbar() {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.hide()

    }

    fun showToolbar() {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
    }

    open fun addBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showExitDialog()
        }
    }

    fun showExitDialog() {
        Utils.createAlertDialog(
            requireContext(),
            requireContext().getString(R.string.exit_dialog_title),
            requireContext().getString(R.string.yes),
            requireContext().getString(R.string.no),
            dialogClickListener
        )
    }

    private val dialogClickListener = object : DialogClickListener {
        override fun onPositiveClick() {
            activity?.finish()
        }

        override fun onNegativeClick() {

        }

    }

}