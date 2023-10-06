package com.shop.data.model

import com.google.gson.annotations.SerializedName

data class TokenResponse (
    @SerializedName("token")
    val token: String
)