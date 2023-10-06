package com.shop.data.repository

import com.shop.utils.AuthResult

interface AuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun login(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}