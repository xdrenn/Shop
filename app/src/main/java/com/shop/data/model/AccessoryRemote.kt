package com.shop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "accessories_table")
data class Accessory(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("subcategory")
    val subcategory: String
)

data class AccessoryRequestCategory(
    @SerializedName("category")
    val category: String
)

data class AccessoryRequestSubcategory(
    @SerializedName("subcategory")
    val subcategory: String
)

data class AccessoryRequestId(
    @SerializedName("id")
    val id: Int
)

data class AccessoriesList(
    @SerializedName("accessory")
    val accessory: List<Accessory> = listOf()
)