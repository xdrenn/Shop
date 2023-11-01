package com.shop.data.remote

import com.shop.data.model.AccessoriesList
import com.shop.data.model.AccessoryRequestCategory
import com.shop.data.model.AccessoryRequestId
import com.shop.data.model.AccessoryRequestSubcategory
import com.shop.data.model.AuthRequest
import com.shop.data.model.DeleteRequest
import com.shop.data.model.GuitarList
import com.shop.data.model.GuitarRequestBrand
import com.shop.data.model.GuitarRequestCategory
import com.shop.data.model.GuitarRequestId
import com.shop.data.model.TokenResponse
import com.shop.data.model.UserList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    //user requests
    @Headers("Content-Type: application/json")
    @POST("api/signup")
    suspend fun signUp(@Body request: AuthRequest)

    @Headers("Content-Type: application/json")
    @POST("api/login")
    suspend fun login(@Body request: AuthRequest): Response<TokenResponse>

    @Headers("Content-Type: application/json")
    @POST("api/delete")
    suspend fun delete(@Header("Authorization") token: String, @Body request: DeleteRequest)

    @Headers("Content-Type: application/json")
    @POST("api/user")
    suspend fun getUser(@Body request: AuthRequest): Response<UserList>

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

    //guitar requests
    @POST("api/guitar/category")
    suspend fun getGuitarByCategory(
        @Header("Authorization") token: String,
        @Body request: GuitarRequestCategory
    ): Response<GuitarList>

    @POST("api/guitar/brand")
    suspend fun getGuitarByBrand(
        @Header("Authorization") token: String,
        @Body request: GuitarRequestBrand
    ): Response<GuitarList>

    @POST("api/guitar/id")
    suspend fun getGuitarById(
        @Header("Authorization") token: String,
        @Body request: GuitarRequestId
    ): Response<GuitarList>

    //accessories request
    @POST("api/accessory/category")
    suspend fun getAccessoryByCategory(
        @Header("Authorization") token: String,
        @Body request: AccessoryRequestCategory
    ): Response<AccessoriesList>

    @POST("api/accessory/id")
    suspend fun getAccessoryById(
        @Header("Authorization") token: String,
        @Body request: AccessoryRequestId
    ): Response<AccessoriesList>

    @POST("api/accessory/subcategory")
    suspend fun getAccessoryBySubcategory(
        @Header("Authorization") token: String,
        @Body request: AccessoryRequestSubcategory
    ): Response<AccessoriesList>


}