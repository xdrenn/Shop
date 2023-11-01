package com.shop.data.repository

import com.shop.data.model.GuitarList
import retrofit2.Response

interface GuitarRepository {
    suspend fun getGuitarByCategory(category: String): Response<GuitarList>
    suspend fun getGuitarByBrand(brand: String): Response<GuitarList>
    suspend fun getGuitarById(id: Int): Response<GuitarList>
}