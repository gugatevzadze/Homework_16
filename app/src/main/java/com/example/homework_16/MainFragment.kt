package com.example.homework_16

import androidx.navigation.fragment.findNavController
import com.example.homework_16.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    override fun setUp() {
    }

    override fun onClickListeners() {
        registerPageNav()
        loginPageNav()
    }

    override fun bindObservers() {
    }

    private fun registerPageNav() {
        binding.registerPageBtn.setOnClickListener() {
            findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }
    }

    private fun loginPageNav() {
        binding.loginPageBtn.setOnClickListener() {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }
}