package com.shop.data.repository

import com.shop.data.model.UserList
import com.shop.utils.AuthResult
import com.shop.utils.DeleteResult
import retrofit2.Response

interface UserRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun login(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
    suspend fun getUser(): Response<UserList>
    suspend fun delete(id: Int): DeleteResult<Unit>
}