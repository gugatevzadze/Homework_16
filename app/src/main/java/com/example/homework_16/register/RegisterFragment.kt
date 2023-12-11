package com.example.homework_16.register

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_16.BaseFragment
import com.example.homework_16.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun setUp() {
    }

    override fun onClickListeners() {
        binding.registerBtn.setOnClickListener {
            registerButtonClicked()
        }
    }

    //observing changes
    override fun bindObservers() {
        observeRegisterResult()
        observeLoadingState()
    }

    //even though several lines of code were added, i decided to put everything in seperate functions to make the code more readable
    //register button click functionality
    private fun registerButtonClicked() {
        viewModel.register(
            binding.registerEmail.text.toString(),
            binding.registerPassword.text.toString()
        )
    }
    //observing changes on the result
    private fun observeRegisterResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect { result ->
                    if (result.isNotEmpty()) {
                        showToast(result)
                    }
                }
            }
        }
    }
    //observing changes on the progress bar
    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    progressBarVisibility(isLoading)
                }
            }
        }
    }
    //showing toast
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    //progress bar visibility function
    private fun progressBarVisibility(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}

