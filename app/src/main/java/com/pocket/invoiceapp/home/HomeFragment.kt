package com.pocket.invoiceapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        showToolbar()
        addBackPressCallback()
        observeLiveData()

    }

    override fun observeLiveData() {

    }


    override fun setUpListeners() {
        binding.btnSignOut.setOnClickListener {
            //viewModel.signOut()
        }
    }

    override fun setUpUI() {
        TODO("Not yet implemented")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}