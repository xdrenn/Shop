package com.shop.data.repository

import android.content.SharedPreferences
import com.shop.data.model.AuthRequest
import com.shop.data.remote.ApiService
import com.shop.utils.AuthResult
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val preferences: SharedPreferences
) : AuthRepository {
    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            apiService.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            login(username, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.Error()
            }
        } catch (e: Exception) {
            AuthResult.Error()
        }
    }

    override suspend fun login(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = apiService.login(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            preferences.edit()
                .putString("jwt", response.body()!!.token)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.Error()
            }
        } catch (e: Exception) {
            AuthResult.Error()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = preferences.getString("jwt", null) ?: return AuthResult.Unauthorized()
            apiService.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.Error()
            }
        } catch (e: Exception) {
            AuthResult.Error()
        }
    }
}