package com.shop.state

data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthSuccessful: Boolean = false
)