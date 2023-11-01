package com.shop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "guitar_table")
data class Guitar(
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String
)

data class GuitarRequestCategory(
    @SerializedName("category")
    val category: String,
    )

data class GuitarRequestBrand(
    @SerializedName("brand")
    val brand: String
)

data class GuitarRequestId(
    @SerializedName("id")
    val id: Int
)

data class GuitarList(
    @SerializedName("guitar")
    val guitar: List<Guitar> = listOf()
)