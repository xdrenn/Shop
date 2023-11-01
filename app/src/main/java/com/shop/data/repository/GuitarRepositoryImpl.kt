package com.shop.data.repository

import android.content.SharedPreferences
import com.shop.data.model.GuitarList
import com.shop.data.model.GuitarRequestBrand
import com.shop.data.model.GuitarRequestCategory
import com.shop.data.model.GuitarRequestId
import com.shop.data.remote.ApiService
import retrofit2.Response


class GuitarRepositoryImpl(
    private val apiService: ApiService,
    private val preferences: SharedPreferences
) : GuitarRepository {
    override suspend fun getGuitarByCategory(category: String): Response<GuitarList> {
        val token = preferences.getString("jwt", null)
        return apiService.getGuitarByCategory(
            token = "Bearer $token",
            request = GuitarRequestCategory(
                category = category
            )
        )
    }

    override suspend fun getGuitarByBrand(brand: String): Response<GuitarList> {
        val token = preferences.getString("jwt", null)
        return apiService.getGuitarByBrand(
            token = "Bearer $token",
            request = GuitarRequestBrand(
                brand = brand
            )
        )
    }

    override suspend fun getGuitarById(id: Int): Response<GuitarList> {
        val token = preferences.getString("jwt", null)
        return apiService.getGuitarById(
            token = "Bearer $token",
            request = GuitarRequestId(
                id = id
            )
        )
    }
}
