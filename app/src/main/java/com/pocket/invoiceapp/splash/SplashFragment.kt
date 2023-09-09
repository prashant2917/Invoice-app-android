package com.pocket.invoiceapp.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.blogger.Blogger
import com.pocket.invoiceapp.databinding.FragmentSplashBinding
import com.pocket.invoiceapp.mainapp.MainActivity
import com.pocket.invoiceapp.utils.Constants
import java.util.Timer
import java.util.TimerTask

class SplashFragment : BaseFragment() {
    private val tag = SplashFragment::class.java.name
    private var _binding: FragmentSplashBinding? = null

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

        setUpUI()
        hideToolbar()
        observeLiveData()


    }

    override fun observeLiveData() {
        Blogger.debug(message = "observe live data")
        Timer().schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {

                    binding.progressBar.visibility = View.GONE
                    navController.navigate(R.id.action_splash_fragment_to_login_fragment)
                }
            }

        }, Constants.SPLASH_SCREEN_TIMER)
    }

    override fun setUpListeners() {

    }

    override fun setUpUI() {
        binding.progressBar.visibility = View.VISIBLE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}