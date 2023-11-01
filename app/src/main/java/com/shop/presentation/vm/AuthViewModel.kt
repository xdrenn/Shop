package com.shop.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.data.model.User
import com.shop.data.repository.UserRepository
import com.shop.state.AuthState
import com.shop.utils.AuthResult
import com.shop.utils.DeleteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState(isLoading = false))
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val resultChannel = Channel<AuthResult<Unit>>()
    var authResult = resultChannel.receiveAsFlow()

    private val deleteChannel = Channel<DeleteResult<Unit>>()
    var deleteResult = deleteChannel.receiveAsFlow()

    private val userChannel = Channel<User>()
    var userResult = userChannel.receiveAsFlow()


    init {
        authenticate()
    }

    fun signUp(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.signUp(
                username = username,
                password = password
            )
            resultChannel.send(result)
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.login(username = username, password = password)
            resultChannel.send(result)
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.delete(id)
            deleteChannel.send(result)
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.getUser().body()?.user
            if (result != null) {
                userChannel.send(result)
            }
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    private fun authenticate() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.authenticate()
        resultChannel.send(result)
    }
}