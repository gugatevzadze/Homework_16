package com.example.homework_16.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_16.ApiService
import com.example.homework_16.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    //result of the login process
    private val _loginResult = MutableStateFlow("")
    val loginResult: StateFlow<String> get() = _loginResult.asStateFlow()

    //login process loader
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    //instance of ApiService using the Retrofit client
    private val apiService = Network.create(ApiService::class.java)

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginResult.value = "Please fill in all fields"
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                //login api call
                val response = apiService.login(LoginRequest(email, password))

                //login result with a success message
                _loginResult.value = "Login successful: ${response.token}"
            } catch (e: Exception) {
                //login result with an error message
                _loginResult.value = "Login failed: ${e.message}"
            }
            _isLoading.value = false
        }
    }
}
