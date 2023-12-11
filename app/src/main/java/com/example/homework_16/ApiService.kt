package com.example.homework_16

import com.example.homework_16.login.LoginRequest
import com.example.homework_16.register.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //suspend function to get post login
    @POST("login")
    suspend fun login(@Body request: LoginRequest): ApiResponse
    //suspend function to get post register
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse
}