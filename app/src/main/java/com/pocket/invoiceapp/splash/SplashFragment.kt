package com.pocket.invoiceapp.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.authenticator.AuthViewModel
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.databinding.FragmentSplashBinding
import com.pocket.invoiceapp.mainapp.MainActivity
import com.pocket.invoiceapp.utils.SPLASH_SCREEN_TIMER
import java.util.Timer
import java.util.TimerTask

class SplashFragment : BaseFragment() {
    private val LOG_TAG = MainActivity::class.java.name
    private var _binding: FragmentSplashBinding? = null
    private val viewModel: AuthViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCurrentUser()


        hideToolbar()
        registerObservers()


    }


    override fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        if (firebaseUser == null) {
                            Log.d(LOG_TAG, "User is not login")
                            binding.progressBar.visibility = View.GONE
                            navController.navigate(R.id.action_splash_fragment_to_login_fragment)


                        } else {
                            Log.d(LOG_TAG, "User is login")
                            binding.progressBar.visibility = View.GONE
                            navController.navigate(R.id.action_splash_fragment_to_home_fragment)

                        }
                    }
                }
            }, SPLASH_SCREEN_TIMER)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}