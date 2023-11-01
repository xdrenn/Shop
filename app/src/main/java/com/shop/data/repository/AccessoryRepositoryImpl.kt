package com.shop.data.repository

import android.content.SharedPreferences
import com.shop.data.model.AccessoriesList
import com.shop.data.model.AccessoryRequestCategory
import com.shop.data.model.AccessoryRequestId
import com.shop.data.model.AccessoryRequestSubcategory
import com.shop.data.remote.ApiService
import retrofit2.Response

class AccessoryRepositoryImpl(
    private val apiService: ApiService,
    private val preferences: SharedPreferences
) : AccessoryRepository {
    override suspend fun getAccessoryByCategory(category: String): Response<AccessoriesList> {
        val token = preferences.getString("jwt", null)
        return apiService.getAccessoryByCategory(
            token = "Bearer $token",
            request = AccessoryRequestCategory(
                category = category
            )
        )
    }

    override suspend fun getAccessoryBySubcategory(subcategory: String): Response<AccessoriesList> {
        val token = preferences.getString("jwt", null)
        return apiService.getAccessoryBySubcategory(
            token = "Bearer $token",
            request = AccessoryRequestSubcategory(
                subcategory = subcategory
            )
        )
    }

    override suspend fun getAccessoryById(id: Int): Response<AccessoriesList> {
        val token = preferences.getString("jwt", null)
        return apiService.getAccessoryById(
            token = "Bearer $token",
            request = AccessoryRequestId(
                id = id
            )
        )
    }
}