package com.shop.state

data class ProductState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccessful: Boolean = false
)