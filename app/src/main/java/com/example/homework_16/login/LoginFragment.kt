package com.example.homework_16.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_16.BaseFragment
import com.example.homework_16.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {
    }

    override fun onClickListeners() {
        binding.loginBtn.setOnClickListener {
            onLoginButtonClicked()
        }
    }

    //observing changes
    override fun bindObservers() {
        observeLoginResult()
        observeLoadingState()
    }
    //login button functionality
    private fun onLoginButtonClicked() {
        viewModel.login(
            binding.loginUsername.text.toString(),
            binding.loginPassword.text.toString()
        )
    }
    //observing login results
    private fun observeLoginResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect { result ->
                    if (result.isNotEmpty()) {
                        showToast(result)
                    }
                }
            }
        }
    }
    //observing login progress bar
    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    updateProgressBarVisibility(isLoading)
                }
            }
        }
    }
    //showing toast
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    //progress bar visibility function
    private fun updateProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}