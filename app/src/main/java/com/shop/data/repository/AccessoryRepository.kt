package com.shop.data.repository

import com.shop.data.model.AccessoriesList
import retrofit2.Response

interface AccessoryRepository {

    suspend fun getAccessoryByCategory(category: String): Response<AccessoriesList>
    suspend fun getAccessoryBySubcategory(subcategory: String): Response<AccessoriesList>
    suspend fun getAccessoryById(id: Int): Response<AccessoriesList>
}