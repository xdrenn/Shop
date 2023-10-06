package com.shop.data.model

import com.google.gson.annotations.SerializedName

data class Guitar(
    @SerializedName("category")
    val category: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String
)