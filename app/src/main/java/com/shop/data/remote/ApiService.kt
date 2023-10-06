package com.shop.data.remote

import com.shop.data.model.AuthRequest
import com.shop.data.model.Guitar
import com.shop.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/signup")
    suspend fun signUp(@Body request: AuthRequest)

    @Headers("Content-Type: application/json")
    @POST("api/login")
    suspend fun login(@Body request: AuthRequest): Response<TokenResponse>

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
    @GET("api/guitar")
    suspend fun getGuitars(
        @Header("Authorization") token: String
    ): Response<Guitar>

}