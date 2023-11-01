package com.shop.data.repository

import android.content.SharedPreferences
import com.shop.data.model.AuthRequest
import com.shop.data.model.DeleteRequest
import com.shop.data.model.UserList
import com.shop.data.remote.ApiService
import com.shop.utils.AuthResult
import com.shop.utils.DeleteResult
import retrofit2.HttpException
import retrofit2.Response

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val preferences: SharedPreferences
) : UserRepository {
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

            preferences.edit()
                .putString("username", username)
                .apply()
            preferences.edit()
                .putString("password", password)
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

    override suspend fun getUser(): Response<UserList> {
        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)
        return apiService.getUser(
            AuthRequest(
                username, password
            )
        )
    }

    override suspend fun delete(id: Int): DeleteResult<Unit> {
        return try {
            val token = preferences.getString("jwt", null) ?: return DeleteResult.Failure()
            apiService.delete(
                token, request = DeleteRequest(
                    id = id
                )
            )
            DeleteResult.Success()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                DeleteResult.Failure()
            } else {
                DeleteResult.Error()
            }
        } catch (e: Exception) {
            DeleteResult.Error()
        }
    }
}