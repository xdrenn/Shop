package com.shop.data.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String
)

data class UserList(
    @SerializedName("user")
    val user: User
)
data class AuthRequest (
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)

data class DeleteRequest (
    @SerializedName("id")
    val id: Int
)

data class TokenResponse (
    @SerializedName("token")
    val token: String
)
