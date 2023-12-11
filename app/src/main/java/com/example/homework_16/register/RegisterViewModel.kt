package com.example.homework_16.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_16.ApiService
import com.example.homework_16.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    //result of the registration process
    private val _registerResult = MutableStateFlow("")
    val registerResult: StateFlow<String> get() = _registerResult.asStateFlow()

    //registration process loader
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    //instance of ApiService using the Retrofit client
    private val apiService = Network.create(ApiService::class.java)

    fun register(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _registerResult.value = "Please fill in all fields"
            return
        }
        //allowing only "eve.holt@reqres.in" as email
        if (email != "eve.holt@reqres.in") {
            _registerResult.value = "Registration is allowed with eve.holt@reqres.in"
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                //register api call
                val response = apiService.register(RegisterRequest(email, password))
                //registration result with a success message
                _registerResult.value = "Registration successful: ${response.token}"
            } catch (e: Exception) {
                //registration result with a error message
                _registerResult.value = "Registration failed: ${e.message}"
            }
            _isLoading.value = false
        }
    }
}
